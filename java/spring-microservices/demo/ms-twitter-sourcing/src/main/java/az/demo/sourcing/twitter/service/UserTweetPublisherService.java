package az.demo.sourcing.twitter.service;

import az.demo.sourcing.twitter.model.UserTweetEntity;

public class UserTweetPublisherService implements IUserTweetPublisherService {

    @Override
    public boolean publish(UserTweetEntity userTweetEntity) {
        return true;
    }
}
