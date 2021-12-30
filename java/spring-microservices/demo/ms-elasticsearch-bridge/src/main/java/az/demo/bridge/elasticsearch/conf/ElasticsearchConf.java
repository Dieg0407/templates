package az.demo.bridge.elasticsearch.conf;

import az.demo.bridge.elasticsearch.conf.props.AppProperties;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticsearchConf extends AbstractElasticsearchConfiguration {
    AppProperties properties;

    public ElasticsearchConf(AppProperties properties) {
        this.properties = properties;
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        final var elastic = properties.getElasticsearch();
        final var conf = ClientConfiguration.builder()
                .connectedTo("%s:%d".formatted( elastic.getHost(), elastic.getPort() ))
                .build();

        return RestClients.create( conf ).rest();
    }
}
