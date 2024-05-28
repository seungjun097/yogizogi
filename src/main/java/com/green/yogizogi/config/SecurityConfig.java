package com.green.yogizogi.config;

import com.green.yogizogi.service.PrincipalOauth2UserService;
import com.nimbusds.oauth2.sdk.auth.ClientAuthenticationMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService oAuth2MemberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .anyRequest().permitAll()
        );

        http.formLogin(login -> login
                .loginPage("/member/login")
                .defaultSuccessUrl("/", true)
                .loginProcessingUrl("/loginProc")
                .failureUrl("/member/login/error")
        );

        //api로그인
        http.oauth2Login(oauth2Login -> oauth2Login
                .loginPage("/member/login")
                .defaultSuccessUrl("/",true)
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(oAuth2MemberService)
                )
        );

        http.logout(auth -> auth.logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );

        http.csrf(cs -> cs.disable());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/css/**"),
                new AntPathRequestMatcher("/fonts/**"),
                new AntPathRequestMatcher("/icons/**"),
                new AntPathRequestMatcher("/img/**"),
                new AntPathRequestMatcher("/js/**")
        );
    }
}