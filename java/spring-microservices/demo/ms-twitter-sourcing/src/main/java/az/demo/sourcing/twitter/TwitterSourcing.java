package az.demo.sourcing.twitter;

import az.demo.sourcing.twitter.conf.props.AppProperties;
import az.demo.sourcing.twitter.service.UserTweetListenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import twitter4j.TwitterStream;

import javax.annotation.PreDestroy;

@Slf4j
@ComponentScan("az.demo")
@SpringBootApplication
public class TwitterSourcing implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TwitterSourcing.class, args);
    }

    private final UserTweetListenerService listenerService;
    private final TwitterStream stream;
    private final AppProperties appProperties;

    public TwitterSourcing(UserTweetListenerService listenerService, TwitterStream stream, AppProperties appProperties) {
        this.listenerService = listenerService;
        this.stream = stream;
        this.appProperties = appProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        stream.addListener( listenerService );
        stream.filter( appProperties.getTwitterKeywords().toArray(String[]::new) );

        stream.sample();
    }

    @PreDestroy
    public void destroy() {
        try {
            stream.shutdown();
        }
        catch (Exception e) {
            log.error( "Error during shut down", e);
        }
    }
}
