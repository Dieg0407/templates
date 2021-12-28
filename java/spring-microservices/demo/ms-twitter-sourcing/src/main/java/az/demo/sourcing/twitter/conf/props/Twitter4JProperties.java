package az.demo.sourcing.twitter.conf.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("twitter4j")
public class Twitter4JProperties {
    private OauthConfig oauth;

    @Data
    public static class OauthConfig {
        private String consumerKey;
        private String consumerSecret;
        private String accessToken;
        private String accessTokenSecret;
    }
}
