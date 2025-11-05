package com.aetheriadm;

import com.aetheriadm.config.properties.JWTProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JWTProperties.class)
@SpringBootApplication
public class AetheriaDmApplication {
    // 애플리케이션 추가
    public static void main(String[] args) {
        SpringApplication.run(AetheriaDmApplication.class, args);
    }

}
