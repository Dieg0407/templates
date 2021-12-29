package az.demo.sourcing.twitter.service;

import az.demo.sourcing.twitter.conf.props.AppProperties;
import az.demo.sourcing.twitter.model.UserTweetEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class UserTweetPublisherService implements IUserTweetPublisherService {

    private final PubSubTemplate template;
    private final AppProperties properties;
    private final ObjectMapper mapper;

    public UserTweetPublisherService(PubSubTemplate template, AppProperties properties, ObjectMapper mapper) {
        this.template = template;
        this.properties = properties;
        this.mapper = mapper;
    }

    @Override
    public String publish(UserTweetEntity userTweetEntity) {
        try {
            final var json = mapper.writeValueAsString( userTweetEntity );
            return template.publish( properties.getTopicName(), json )
                    .get();
        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
            throw new RuntimeException( e );
        }
    }
}
