package az.demo.bridge.postgresql.service;

import az.demo.bridge.postgresql.conf.props.AppProperties;
import az.demo.bridge.postgresql.model.TweetEntity;
import az.demo.bridge.postgresql.repo.TweetRepo;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TweetBridgeService {
    private final PubSubTemplate pubSubTemplate;
    private final TweetRepo repo;
    private final AppProperties properties;

    public TweetBridgeService(PubSubTemplate pubSubTemplate, TweetRepo repo, AppProperties properties) {
        this.pubSubTemplate = pubSubTemplate;
        this.repo = repo;
        this.properties = properties;
    }

    public void startSubscription() {
        pubSubTemplate.subscribeAndConvert( properties.getSubscriptionName(), (message) -> {
            var payload = message.getPayload();
            repo.save( payload );

            log.info( "Tweet saved" );

            message.ack();
        }, TweetEntity.class);
    }
}
