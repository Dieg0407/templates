package az.demo.bridge.elasticsearch.repo.elastic;

import az.demo.bridge.elasticsearch.model.Tweet;
import az.demo.bridge.elasticsearch.repo.Creator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Slf4j
@Repository
public class ESTweetRepo implements Creator<Tweet> {
    private static final String INDEX = "tweets";

    private final RestHighLevelClient elasticClient;
    private final ObjectMapper mapper;

    public ESTweetRepo(RestHighLevelClient elasticClient, ObjectMapper mapper) {
        this.elasticClient = elasticClient;
        this.mapper = mapper;
    }

    @Override
    public Tweet create(Tweet data) {
        final var request = new IndexRequest( INDEX );

        try {
            request.source( mapper.writeValueAsString(data), XContentType.JSON );
            elasticClient.index( request, RequestOptions.DEFAULT);

        } catch (IOException e) {
            log.error("Failed to send the request to elasticsearch", e);
        }

        return data;
    }
}
