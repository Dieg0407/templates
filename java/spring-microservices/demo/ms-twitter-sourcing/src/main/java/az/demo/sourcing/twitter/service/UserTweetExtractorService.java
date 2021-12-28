package az.demo.sourcing.twitter.service;

import az.demo.sourcing.twitter.model.UserTweetEntity;
import twitter4j.Status;

public class UserTweetExtractorService {

    public UserTweetEntity fromTwitterStatus(Status status) {
        if ( status.getUser() != null )
            return new UserTweetEntity(
                    status.getId(),
                    status.getUser().getName(),
                    status.getUser().getEmail(),
                    status.getText(),
                    status.isRetweet(),
                    status.getFavoriteCount()
            );
        else
            return new UserTweetEntity(
                    status.getId(),
                    "No user found",
                    "No email found",
                    status.getText(),
                    status.isRetweet(),
                    status.getFavoriteCount()
            );

    }
}
