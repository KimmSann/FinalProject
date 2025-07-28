package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer.UserInfoEndpointConfig;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.user.service.OAuth2UserService;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	 private final OAuth2UserService oAuth2UserService;

	    public SecurityConfig(OAuth2UserService oAuth2UserService) {
	        this.oAuth2UserService = oAuth2UserService;
	    }
	

	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	// 댓글 달기, 좋아요, 싫어요, post.register modify, 마이페이지 이렇게 로그인 안하면 막아두기 (끝)
    	
    	http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/signin","/signup", "/register", "/css/**", "/js/**", "/images/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 전용 페이지
            .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
            .requestMatchers("/post/**").hasAnyRole("ADMIN", "USER") // 게시물 삭제는 관리자와 작성자만
            .requestMatchers("/comment/**").hasAnyRole("ADMIN", "USER") // 댓글도 마찬가지
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
                )
                .oauth2Login(form -> {
                    form
                        .loginPage("/signin")
                        .userInfoEndpoint(userInfo -> {
                            userInfo.userService(oAuth2UserService);
                        });
                });
              
            
//      
//	    http.authorizeHttpRequests()
//        // 로그인해야만 접근 가능한 경로
//        .requestMatchers("/cart/**", "/mypage/main").authenticated()
//        // 등록은 권한이 판매자인 사람만 들어오게 하기
//        
//        // 권환을 확인하는 목록 : 물건 등록, 수정, 삭제, 주문목록수정
//        .requestMatchers("/products/register")
//        .hasAnyRole("SELLER")
//        .requestMatchers("/products/modify")
//        .hasAnyRole("SELLER")
//        .requestMatchers("/products/remove")
//        .hasAnyRole("SELLER")
//        .requestMatchers("/mypage/orderlist")
//        .hasAnyRole("SELLER")
//        .anyRequest().permitAll();


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}