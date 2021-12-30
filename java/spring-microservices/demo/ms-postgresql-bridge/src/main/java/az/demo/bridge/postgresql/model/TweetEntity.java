package az.demo.bridge.postgresql.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.ZonedDateTime;

@ToString
@Getter
@Setter
@Entity
@Table(name = "tweets")
public class TweetEntity {
    @Id
    private long id;

    @Column(name = "user_alias")
    private String user;
    private String email;
    private String text;
    private boolean  retweet;
    private int favoriteCount;
    private ZonedDateTime registrationDate = ZonedDateTime.now();

}
