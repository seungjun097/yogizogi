package com.green.yogizogi.config;

import com.nimbusds.oauth2.sdk.auth.ClientAuthenticationMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
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
        http.formLogin(login -> login
                .loginPage("/member/login") //로그인 페이지 사용자인증 안됐을시 이페이지로 이동
                .defaultSuccessUrl("/")  // 로그인 성공시 페이지
                .loginProcessingUrl("/loginProc") // 로그인 처리 Url
                .failureUrl("/member/login/error") //실패시 페이지
        );

        http.logout(auth -> auth.logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );

        http.authorizeHttpRequests(auth -> auth  //권한 메서드
                .requestMatchers("/").permitAll() //해당 경로 요청에대해 인증없이 허용
                .anyRequest().permitAll() //나머지 모든 요청에 대해서도 인증없이 허용
        );
        http.csrf(cs -> cs.disable()); //csrf보호 비활성 기본적으로는 활성화 돼있음
        return http.build();
    }


}