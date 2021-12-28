package az.demo.sourcing.twitter.conf.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("service")
public class AppProperties {
    private List<String> twitterKeywords;
    private String welcomeMessage;
}