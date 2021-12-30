package az.demo.bridge.elasticsearch.conf.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "service")
public class AppProperties {
    private String subscriptionName;
    private ElasticsearchProperties elasticsearch;

    @Data
    public static class ElasticsearchProperties {
        private String host;
        private int port;
    }
}
