package az.demo.sourcing.twitter.service;

import az.demo.sourcing.twitter.model.UserTweetEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twitter4j.Status;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTweetListenerServiceTest {
    @Mock
    Status status;

    @Mock
    UserTweetExtractorService extractorService;

    @Mock
    IUserTweetPublisherService publisherService;

    UserTweetListenerService service;

    @BeforeEach
    public void init() {
        service = new UserTweetListenerService( 5, extractorService, publisherService);
    }

    @Test
    public void testArriveNewMessage() {
        service.onStatus(status);
        assertThat( service.getNumberOfMessages() )
                .as( "Validating calculation of message number" )
                .isEqualTo( 1 );

        service.onStatus( status );
        assertThat( service.getNumberOfMessages() )
                .as( "Validating calculation of message number after second add" )
                .isEqualTo( 2 );
    }

    @Test
    public void testLimitOfProcessedMessages() {
        when( extractorService.fromTwitterStatus( any() ) ).thenReturn( new UserTweetEntity() );
        service.onStatus(status);
        service.onStatus(status);
        service.onStatus(status);
        service.onStatus(status);
        service.onStatus(status);

        verify( extractorService, times( 5 )).fromTwitterStatus( any() );

        // limited to process 5
        service.onStatus( status );
        verify( extractorService, times( 5 )).fromTwitterStatus( any() );

    }

    @Test
    public void testPublishOnProcessing() {
        when( extractorService.fromTwitterStatus( any() ) ).thenReturn( new UserTweetEntity() );
        when( publisherService.publish( any() ) ).thenReturn( "" );

        service.onStatus( status );
        verify( extractorService, times( 1 ) ).fromTwitterStatus( any() );
        verify( publisherService, times( 1 ) ).publish( any() );
    }

    @Test
    public void testHandlePublishError() {
        when( extractorService.fromTwitterStatus( any() ) ).thenReturn( new UserTweetEntity() );
        when( publisherService.publish( any() ) ).thenThrow( new RuntimeException( "Failed to publish message" ) );

        service.onStatus( status );
        verify( extractorService, times( 1 ) ).fromTwitterStatus( any() );
        verify( publisherService, times( 1 ) ).publish( any() );    }
}
