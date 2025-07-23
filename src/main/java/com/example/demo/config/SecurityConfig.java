package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
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
    	
    	// 댓글 달기, 좋아요, 싫어요, post.register modify, 마이페이지 이렇게 로그인 안하면 막아두기 (끝)
    	
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/signin", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/", "/signin").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                // 이건 대채 뭐죠
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
            // 로그인 한거 기억하기
            .rememberMe(
            		remember -> remember.key("veryimportkeymybe..113333**")
            		// 로그인 정보 기억 체크박스 아이디
            		.rememberMeParameter("remember-me")
            		// 로그인 제한 시간 하루로 제안해둠
            		// 하루 간격으로 쿠키 삭제됨
            		.tokenValiditySeconds(60*60*24)
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