package com.autoapprove.ringout.api.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.ringout")
public class RingoutProps {
    private String id;
    private String secret;
    private String url;
    private String user;
    private String pass;
    private String token;
    private String ext;
}
