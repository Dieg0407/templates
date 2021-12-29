package az.demo.sourcing.twitter.service;

import az.demo.sourcing.twitter.conf.props.AppProperties;
import az.demo.sourcing.twitter.model.UserTweetEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.concurrent.ListenableFuture;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTweetPublisherServiceTest {
    UserTweetPublisherService service;

    @Mock
    ListenableFuture<String> future;

    @Mock
    PubSubTemplate template;

    @Mock
    AppProperties properties;

    @Mock
    ObjectMapper mapper;

    @BeforeEach
    public void init() {
        service = new UserTweetPublisherService( template, properties, mapper);
    }

    @Test
    public void testPublish() throws Exception {
        final var entity = new UserTweetEntity();
        final var json = "{}";
        final var topic = "topic";

        when (mapper.writeValueAsString( entity )).thenReturn( json );
        when( future.get() ).thenReturn("");
        when( template.publish(topic, json) ).thenReturn( future );
        when( properties.getTopicName() ).thenReturn( topic );

        assertThat( service.publish( entity ) ).isEqualTo( "" );
    }

}
