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
        
        // 권한에 따라서 접근권한 주기
        // .requestMatchers("/admin/**").hasRole("ADMIN")  // ADMIN만 접근
//        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")  // USER 또는 ADMIN만 접근
        
        // 접근 거부시 
//        .exceptionHandling(exception -> 
//        exception.accessDeniedPage("/access-denied")
//    )


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}