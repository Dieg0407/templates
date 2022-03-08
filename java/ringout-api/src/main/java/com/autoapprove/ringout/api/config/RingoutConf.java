package com.autoapprove.ringout.api.config;

import java.io.IOException;

import com.autoapprove.ringout.api.config.props.RingoutProps;
import com.ringcentral.RestClient;
import com.ringcentral.RestException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Configuration
public class RingoutConf {

    @Bean
    public RestClient ringCentralRestClient(RingoutProps props) throws IOException, RestException {
        final var client = new RestClient(props.getId(), props.getSecret(), props.getUrl());
        if (props.getToken() != null && !props.getToken().isBlank()) 
            client.authorize(props.getToken());
        else
            client.authorize(props.getUser(), props.getExt(), props.getPass());

        client.autoRefresh();

        return client;
    }

    @Bean(destroyMethod = "exec")
    public Shutdown shutdown(RingoutProps props) throws IOException, RestException {
        return new Shutdown(ringCentralRestClient(props));
    }

    @Slf4j
    @Value
    public static class Shutdown {
        private final RestClient restClient;

        public void exec() throws IOException, RestException {
            log.info("Calling revoke on ring central rest client");
            restClient.revoke();
        }
    }
}
