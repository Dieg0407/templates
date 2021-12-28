package az.demo.sourcing.twitter.conf;

import az.demo.sourcing.twitter.conf.props.AppProperties;
import az.demo.sourcing.twitter.conf.props.Twitter4JProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class TwitterConf {
    private final Twitter4JProperties twitter4JProperties;
    private final AppProperties properties;

    public TwitterConf(Twitter4JProperties twitter4JProperties, AppProperties properties) {
        this.twitter4JProperties = twitter4JProperties;
        this.properties = properties;
    }

    @Bean
    public TwitterStreamFactory twitterFactory() {
        final var confBuilder = new ConfigurationBuilder();
        confBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey( twitter4JProperties.getOauth().getConsumerKey() )
                .setOAuthConsumerSecret( twitter4JProperties.getOauth().getConsumerSecret() )
                .setOAuthAccessToken( twitter4JProperties.getOauth().getAccessToken() )
                .setOAuthAccessTokenSecret( twitter4JProperties.getOauth().getAccessTokenSecret() );

        return new TwitterStreamFactory(confBuilder.build());
    }

    @Bean
    @Scope("prototype")
    public TwitterStream twitterStream(TwitterStreamFactory factory) {
        return factory.getInstance();
    }
}
