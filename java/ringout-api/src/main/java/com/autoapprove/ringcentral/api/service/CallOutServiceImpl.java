package com.autoapprove.ringcentral.api.service;

import com.autoapprove.ringcentral.api.model.CallRequest;
import com.autoapprove.ringcentral.api.service.factory.RestClientFactory;
import com.ringcentral.RestClient;
import com.ringcentral.RestException;
import com.ringcentral.definitions.CallSession;
import com.ringcentral.definitions.ExtensionDeviceResponse;
import com.ringcentral.definitions.MakeCallOutCallerInfoRequestFrom;
import com.ringcentral.definitions.MakeCallOutCallerInfoRequestTo;
import com.ringcentral.definitions.MakeCallOutRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class CallOutServiceImpl implements CallOutService {
  final RestClientFactory factory;

  public CallOutServiceImpl(RestClientFactory factory) {
    this.factory = factory;
  }

  @Override
  public CallSession call(CallRequest data) {
    final var client = factory.createClient(data.getExtension());
    final var device = getDevice(client, data.getExtension());

    final var from = new MakeCallOutCallerInfoRequestFrom().deviceId(device.id);
    final var to = new MakeCallOutCallerInfoRequestTo().phoneNumber(data.getTo());
    final var request = new MakeCallOutRequest()
        .from(from)
        .to(to);

    try {
      final var res = client.restapi()
          .account()
          .telephony()
          .callOut()
          .post(request);
      client.revoke();

      return res;
    } catch (RestException | IOException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to generate a new call", e);
    }
  }

  ExtensionDeviceResponse getDevice(RestClient client, String extension) {
    try {
      final var devices = client.restapi()
          .account()
          .extension()
          .device()
          .get()
          .records;

      if (devices.length == 0)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no devices for the extension: " + extension);

      for(final var device : devices) {
        if ("Online".equals(device.status)) {
          return device;
        }
      }

      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no active devices for the extension: " + extension);
    } catch (RestException | IOException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to fetch devices for extension: " + extension, e);
    }
  }
}
