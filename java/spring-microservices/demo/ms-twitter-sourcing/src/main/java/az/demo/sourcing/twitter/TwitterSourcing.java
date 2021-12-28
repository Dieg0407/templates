package az.demo.sourcing.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("az.demo")
@SpringBootApplication
public class TwitterSourcing {

    public static void main(String[] args) {
        SpringApplication.run(TwitterSourcing.class, args);
    }
}
