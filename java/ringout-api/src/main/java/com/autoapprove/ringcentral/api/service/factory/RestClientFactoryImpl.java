package com.autoapprove.ringcentral.api.service.factory;

import com.autoapprove.ringcentral.api.config.props.RingCentralProps;
import com.autoapprove.ringcentral.api.repo.CredentialsRepositoryImpl;
import com.ringcentral.RestClient;
import com.ringcentral.RestException;
import com.ringcentral.definitions.TokenInfo;
import java.io.IOException;
import okhttp3.OkHttpClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RestClientFactoryImpl implements RestClientFactory {
  final RingCentralProps props;
  final CredentialsRepositoryImpl repository;

  public RestClientFactoryImpl(RingCentralProps props,
      CredentialsRepositoryImpl repository) {
    this.props = props;
    this.repository = repository;
  }

  public RestClient createClient(String extension) {
    final var client = new RestClient(props.getId(), props.getSecret(), props.getUrl());
    final var jwt = repository.getJwt(extension);

    try {
      client.authorize(jwt);

      return client;
    } catch (IOException | RestException e) {
      throw new ResponseStatusException(
          HttpStatus.UNAUTHORIZED,
          "Login failed to RingCentral with stored credentials for extension: " + extension,
          e
      );
    }
  }

  @Override
  public RestClient createClient(String extension, String authToken) {
    final var client = new RestClient(props.getId(), props.getSecret(), props.getUrl());
    client.token = new TokenInfo().access_token(authToken);

    return client;
  }
}
