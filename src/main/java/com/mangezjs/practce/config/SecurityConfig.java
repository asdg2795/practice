package com.mangezjs.practce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf->csrf
                        .disable()
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/","/member/","/member/login","/member/logout","/member/join","/member/login/fail","/member/findPw", "/login/mailConfirm").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login->login
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/", true)
                        .usernameParameter("email")
                        .failureUrl("/member/login/fail")
                )
                .logout(logout->logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/"));
        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
