package az.demo.bridge.elasticsearch.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;

@Configuration
public class PubSubConf {
    final PubSubTemplate template;
    final ObjectMapper mapper;

    public PubSubConf(PubSubTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        template.setMessageConverter( new JacksonPubSubMessageConverter( mapper ));
    }

    @Bean(name = "pubsubSubscriberThreadPool")
    public ThreadPoolTaskScheduler scheduler() {
        return new ThreadPoolTaskScheduler();
    }

}
