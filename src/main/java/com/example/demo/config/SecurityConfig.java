package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	// 댓글 달기, 좋아요, 싫어요, post.register modify, 마이페이지 이렇게 로그인 안하면 막기
    	
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/signin", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/", "/signin").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                // 여기에 로그인 안하면 못들어오는 경로 추가
                .requestMatchers("/post/register", "/post/modify", "/post/like", "/post/unlike",
                		"/post/remove", "/comment/register", "/comment/delete", "/home/mypage", 
                		"/home/modify").authenticated()
                
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/signin")
                .permitAll()
                .defaultSuccessUrl("/", true)
            )
            .logout(logout -> logout 	
                .logoutUrl("/logout")
                .logoutSuccessUrl("/signin")
            );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}