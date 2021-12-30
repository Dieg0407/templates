package az.demo.bridge.elasticsearch.service;

import az.demo.bridge.elasticsearch.conf.props.AppProperties;
import az.demo.bridge.elasticsearch.model.Tweet;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ElasticsearchBridgeService {
    private final PubSubTemplate pubSubTemplate;
    private final RestHighLevelClient elasticClient;
    private final AppProperties properties;

    public ElasticsearchBridgeService(PubSubTemplate pubSubTemplate,
                                      RestHighLevelClient elasticClient,
                                      AppProperties properties) {
        this.pubSubTemplate = pubSubTemplate;
        this.elasticClient = elasticClient;
        this.properties = properties;
    }

    public void startSubscription() {
        /*pubSubTemplate.subscribe( properties.getSubscriptionName(), (message) -> {
            var json = message.getPubsubMessage().getData().toString();
            log.info( json );

            message.ack();
        });*/
        pubSubTemplate.subscribeAndConvert( properties.getSubscriptionName(), (message) -> {
            var payload = message.getPayload();
            log.info( payload.toString() );

            message.ack();
        }, Tweet.class);
    }
}
