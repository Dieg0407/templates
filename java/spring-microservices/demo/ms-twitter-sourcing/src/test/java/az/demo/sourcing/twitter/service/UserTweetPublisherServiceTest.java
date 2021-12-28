package az.demo.sourcing.twitter.service;

import az.demo.sourcing.twitter.model.UserTweetEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserTweetPublisherServiceTest {
    UserTweetPublisherService service;

    @BeforeEach
    public void init() {
        service = new UserTweetPublisherService();
    }

    @Test
    public void testPublish() {
        assertThat( service.publish( new UserTweetEntity()) ).isEqualTo( true );
    }

}
