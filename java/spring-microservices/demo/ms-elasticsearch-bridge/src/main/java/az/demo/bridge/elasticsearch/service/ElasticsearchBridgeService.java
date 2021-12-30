package az.demo.bridge.elasticsearch.service;

import az.demo.bridge.elasticsearch.conf.props.AppProperties;
import az.demo.bridge.elasticsearch.model.Tweet;
import az.demo.bridge.elasticsearch.repo.Creator;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ElasticsearchBridgeService {
    private final PubSubTemplate pubSubTemplate;
    private final Creator<Tweet> tweetCreator;
    private final AppProperties properties;

    public ElasticsearchBridgeService(PubSubTemplate pubSubTemplate, Creator<Tweet> tweetCreator, AppProperties properties) {
        this.pubSubTemplate = pubSubTemplate;
        this.tweetCreator = tweetCreator;
        this.properties = properties;
    }

    public void startSubscription() {
        pubSubTemplate.subscribeAndConvert( properties.getSubscriptionName(), (message) -> {
            var payload = message.getPayload();
            tweetCreator.create( payload );

            log.info( "Tweet sent!" );

            message.ack();
        }, Tweet.class);
    }
}
