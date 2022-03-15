package com.autoapprove.ringcentral.api.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.ringout")
public class RingCentralProps {
    private String id;
    private String secret;
    private String url;
    private String mainNumber;
}
