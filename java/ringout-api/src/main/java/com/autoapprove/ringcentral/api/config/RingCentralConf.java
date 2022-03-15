package com.autoapprove.ringcentral.api.config;

import com.autoapprove.ringcentral.api.config.props.RingCentralProps;
import com.autoapprove.ringcentral.api.service.factory.RestClientFactory;
import com.ringcentral.RestClient;
import com.ringcentral.RestException;
import com.ringcentral.pubnub.Subscription;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
@EnableScheduling
public class RingCentralConf {
  @Autowired
  RestClientFactory factory;

  @Bean("telephonySubClient")
  public RestClient telephonySubClient(RingCentralProps props) {
    return factory.createClient(props.getMainNumber());
  }

  @Bean("telephonySubscription")
  public Subscription subscription(@Autowired @Qualifier("telephonySubClient") RestClient client) throws RestException, IOException {
    var eventFilters = new String[] {
        "/restapi/v1.0/account/~/telephony/sessions"
    };
    final var subscription = new Subscription(client, eventFilters, log::info);

    subscription.subscribe();
    log.info("Subscription created for telephony service with id {}", subscription.getSubscription().id);

    return subscription;
  }

  @Slf4j
  @Component
  public static class TelephonyRefresher {

    final RestClient client;
    final Subscription subscription;

    public TelephonyRefresher(
        @Autowired @Qualifier("telephonySubClient") RestClient client,
        @Autowired @Qualifier("telephonySubscription") Subscription subscription
    ) {
      this.client = client;
      this.subscription = subscription;
    }

    @Scheduled(fixedDelay = 300, timeUnit = TimeUnit.SECONDS)
    public void refreshTelephonySubscription() {
      try {
        log.info("It's gonna try to refresh");

        client.refresh();
        subscription.refresh();
      } catch (RestException | IOException e) {
        log.error("Failed to refresh subscription", e);
      }
    }
  }
}
