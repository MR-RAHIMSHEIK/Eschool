package com.jts.login.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

import java.util.List;

/**
 * @author rahim.sheik
 * 
 * Holds CORS-related properties loaded from application.properties or application.yml.
 * This way we avoid hardcoding allowed origins, headers, and methods.
 */
@Component
@ConfigurationProperties(prefix = "app.cors")
@Data
public class CorsProperties {
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private Boolean allowCredentials;
    private Long maxAge;
}

