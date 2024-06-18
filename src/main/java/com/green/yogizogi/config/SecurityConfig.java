package com.green.yogizogi.config;

import com.green.yogizogi.service.PrincipalOauth2UserService;
import com.nimbusds.oauth2.sdk.auth.ClientAuthenticationMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
    public CsrfTokenRepository csrfTokenRepository() {
        return new CsrfTokenRepository() {
            private final HttpSessionCsrfTokenRepository delegate = new HttpSessionCsrfTokenRepository();

            @Override
            public CsrfToken generateToken(HttpServletRequest request) {
                return delegate.generateToken(request);
            }

            @Override
            public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
                delegate.saveToken(token, request, response);
            }

            @Override
            public CsrfToken loadToken(HttpServletRequest request) {
                CsrfToken csrfToken = delegate.loadToken(request);
                if (csrfToken != null) {
                    String decodedHeader = decodeCookieValue(request.getHeader("Cookie"));
                    String csrfCookieName = "XSRF-TOKEN"; // CSRF 토큰의 이름에 따라 변경
                    String csrfCookieValue = extractCsrfToken(decodedHeader, csrfCookieName);
                    if (csrfCookieValue != null) {
                        return new DefaultCsrfToken(csrfCookieName, "_csrf", csrfCookieValue);
                    }
                }
                return csrfToken;
            }

            private String extractCsrfToken(String decodedHeader, String csrfCookieName) {
                String[] cookies = decodedHeader.split("; ");
                for (String cookie : cookies) {
                    String[] parts = cookie.split("=");
                    if (parts.length == 2 && parts[0].trim().equals(csrfCookieName)) {
                        return parts[1].trim();
                    }
                }
                return null;
            }

            private String decodeCookieValue(String cookieValue) {
                try {
                    return URLDecoder.decode(cookieValue, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .anyRequest().permitAll()
        );

        http.formLogin(login -> login
                .loginPage("/member/login")
                .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/", true)
                .failureUrl("/member/login/error")
        );

        //api로그인
        http.oauth2Login(oauth2Login -> oauth2Login
                .loginPage("/member/login")
                .defaultSuccessUrl("/", true)
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
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/css/**"),
                new AntPathRequestMatcher("/fonts/**"),
                new AntPathRequestMatcher("/icons/**"),
                new AntPathRequestMatcher("/img/**"),
                new AntPathRequestMatcher("/images/**"),
                new AntPathRequestMatcher("/js/**"),
                new AntPathRequestMatcher("/layout/**")
        );
    }

}