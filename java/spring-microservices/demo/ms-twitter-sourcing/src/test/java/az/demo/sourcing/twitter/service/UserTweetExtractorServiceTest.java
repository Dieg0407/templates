package az.demo.sourcing.twitter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twitter4j.Status;
import twitter4j.User;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTweetExtractorServiceTest {
    @Mock
    Status status;

    @Mock
    User user;


    UserTweetExtractorService service;

    @BeforeEach
    public void init() {
        service = new UserTweetExtractorService();
    }

    @Test
    public void testCreateUserTweet() {
        when( user.getName() ).thenReturn( "Diego" );
        when( user.getEmail() ).thenReturn( "dieg0407@hotmail.com" );

        when( status.getId() ).thenReturn( (long) 1 );
        when( status.getUser() ).thenReturn( user );
        when( status.getText() ).thenReturn( "this is a text demo");
        when( status.getFavoriteCount() ).thenReturn( 20 );
        when( status.isRetweet() ).thenReturn( true );

        var userTweetEntity = service.fromTwitterStatus( status );

        assertThat( userTweetEntity ).isNotNull();
        assertThat( userTweetEntity.getId() ).isEqualTo( status.getId() );
        assertThat( userTweetEntity.getUser() ).isEqualTo( status.getUser().getName() );
        assertThat( userTweetEntity.getEmail() ).isEqualTo( status.getUser().getEmail() );
        assertThat( userTweetEntity.getText() ).isEqualTo( status.getText() );
        assertThat( userTweetEntity.getFavoriteCount() ).isEqualTo( status.getFavoriteCount() );
        assertThat( userTweetEntity.isRetweet() ).isEqualTo( status.isRetweet() );
    }

    @Test
    public void testWhenUserInfoIsNotFound() {
        when( status.getId() ).thenReturn( (long) 1 );
        when( status.getUser() ).thenReturn( null );
        when( status.getText() ).thenReturn( "this is a text demo");
        when( status.getFavoriteCount() ).thenReturn( 20 );
        when( status.isRetweet() ).thenReturn( true );

       var userTweetEntity = service.fromTwitterStatus( status );
       assertThat( userTweetEntity.getUser() ).isEqualTo( "No user found" );
       assertThat( userTweetEntity.getEmail() ).isEqualTo("No email found");
    }
}
