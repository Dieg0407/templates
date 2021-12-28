package az.demo.sourcing.twitter.service;

import az.demo.sourcing.twitter.model.UserTweetEntity;

public interface IUserTweetPublisherService {
    boolean publish(UserTweetEntity userTweetEntity);
}
