package com.abnamro.tvshowmanager.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class RateLimiterConfiguration {

    @Autowired
    private RateLimiterRegistry rateLimiterRegistry;

    @Value("${rate-limiter.limit-for-period}")
    private int limitForPeriod;

    @Value("${rate-limiter.limit-refresh-period}")
    private int limitRefreshPeriod;

    @Value("${rate-limiter.timeout-duration}")
    private int timeoutDuration;

    @Bean
    public RateLimiter rateLimitWithGlobalConfig() {
        RateLimiterConfig customConfig = RateLimiterConfig.custom()
                .limitForPeriod(limitForPeriod)
                .limitRefreshPeriod(Duration.ofMinutes(limitRefreshPeriod))
                .timeoutDuration(Duration.of(timeoutDuration, ChronoUnit.SECONDS))
                .build();

        return rateLimiterRegistry.rateLimiter("GlobalRateLimiterConfig", customConfig);
    }
}
