package com.green.yogizogi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(login->login
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/loginProc")
                .failureUrl("/member/login/error")
        );

        http.logout(auth-> auth.logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );

        http.authorizeHttpRequests(auth-> auth
                .requestMatchers("/").permitAll()
                .anyRequest().permitAll()
        );
        http.csrf(cs->cs.disable());
        return http.build();
    }
}