package az.demo.bridge.elasticsearch;

import az.demo.bridge.elasticsearch.service.ElasticsearchBridgeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("az.demo")
@SpringBootApplication
public class ElasticsearchBridge implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchBridge.class, args);
    }
    final ElasticsearchBridgeService service;

    public ElasticsearchBridge(ElasticsearchBridgeService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        service.startSubscription();
    }
}
