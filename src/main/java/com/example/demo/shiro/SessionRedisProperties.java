package com.example.demo.shiro;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "fone.sso")
public class SessionRedisProperties {

    public static final String SESSION_NAME = "f1sid";

    private String cookieDomain = "localhost";

    private Boolean redisEnable = true;

    private String redisSessionKeyPrefix;

    private Duration sessionTimeout;

    private String redisShiroKeyPrefix;

    private Duration redisShiroTimeout;
}
