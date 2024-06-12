package com.green.yogizogi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public CookieSanitizerInterceptor cookieSanitizerInterceptor() {
        return new CookieSanitizerInterceptor();
    }
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new CookieSanitizerInterceptor()).addPathPatterns("/**");
        }

}
