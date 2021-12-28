package az.demo.sourcing.twitter.service;

import az.demo.sourcing.twitter.model.UserTweetEntity;
import org.springframework.stereotype.Service;

@Service
public class UserTweetPublisherService implements IUserTweetPublisherService {

    @Override
    public boolean publish(UserTweetEntity userTweetEntity) {
        return true;
    }
}
