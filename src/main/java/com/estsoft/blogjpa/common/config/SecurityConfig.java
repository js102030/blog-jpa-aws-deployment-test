package com.estsoft.blogjpa.common.config;


import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    //    @Bean
//    public WebSecurityCustomizer configure() {      // 스프링 시큐리티 기능 비활성화
//        return web -> web.ignoring().requestMatchers(toH2Console())
//                .requestMatchers("/static/**");
//    }
//
    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests(
//                        auth -> auth          // 인증, 인가 설정
//                                .requestMatchers("/login", "/signup", "/user")
//                                .permitAll()
//                                .anyRequest()
//                                .authenticated())
//                .formLogin(
//                        auth -> auth
//                                .loginPage("/login")     // 폼 기반 로그인 설정
//                                .defaultSuccessUrl("/articles"))
//                .logout(
//                        auth -> auth
//                                .logoutSuccessUrl("/login") // 로그아웃 설정
//                                .invalidateHttpSession(true))
//                .csrf(AbstractHttpConfigurer::disable);                  // csrf 비활성화
//        return httpSecurity.build();
//    }
//
//    // 패스워드 인코더로 사용할 빈 등록
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public WebSecurityCustomizer configure() {      // 스프링 시큐리티 기능 비활성화
        return web -> web.ignoring().requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.
                authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers("/login", "/signup", "/users")
                                .permitAll()
                                .requestMatchers("/new-article").hasRole("USER")
//                        .requestMatchers("/admin").hasRole("ADMIN")
//                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                                .anyRequest()
                                .authenticated()
                );

        httpSecurity
                .formLogin(auth -> auth
                                .loginPage("/login")
//                                .loginProcessingUrl("/articles")
//                        .permitAll()
                );

        // 이건 주석처리 하면 에러남 어떻게 할지 모르겠음.
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable);

        httpSecurity
                .sessionManagement(auth -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                );

        httpSecurity
                .sessionManagement(auth -> auth
                        .sessionFixation()
                        .changeSessionId()
                );

        httpSecurity
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                );

        return httpSecurity.build();

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}