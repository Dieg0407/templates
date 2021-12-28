package az.demo.external;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfServer {

    public static void main(String[] args) {
        SpringApplication.run(ConfServer.class, args);
    }
}
