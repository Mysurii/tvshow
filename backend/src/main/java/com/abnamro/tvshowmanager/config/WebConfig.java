package com.abnamro.tvshowmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // TODO: Change to url on production!.
                .allowedMethods("GET", "POST", "Put", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
