package az.demo.bridge.postgresql;

import az.demo.bridge.postgresql.service.TweetBridgeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("az.demo")
@SpringBootApplication
public class PostgresqlBridge implements CommandLineRunner  {
    public static void main(String[] args) {
        SpringApplication.run( PostgresqlBridge.class, args );
    }

    final TweetBridgeService bridgeService;

    public PostgresqlBridge(TweetBridgeService bridgeService) {
        this.bridgeService = bridgeService;
    }

    @Override
    public void run(String... args) throws Exception {
        bridgeService.startSubscription();
    }
}
