package az.demo.sourcing.twitter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTweetEntity {
    private long id;
    private String user;
    private String email;
    private String text;
    private boolean retweet;
    private int favoriteCount;
}
