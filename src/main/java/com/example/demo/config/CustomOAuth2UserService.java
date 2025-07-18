package com.example.demo.config;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

@Service
	public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	    private final UserRepository userRepository;
	    private final PasswordEncoder passwordEncoder;

	    public CustomOAuth2UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
	        this.userRepository = userRepository;
	        this.passwordEncoder = passwordEncoder;
	    }

	    @Override
	    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
	        OAuth2User oAuth2User = super.loadUser(userRequest);
	        Map<String, Object> attributes = oAuth2User.getAttributes();

	        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // ex: "kakao", "google"
	        String email = extractEmail(attributes, registrationId);

	        Optional<User> userOpt = userRepository.findByEmail(email);
	        User user;

	        if (userOpt.isEmpty()) {
	            user = User.builder()
	                    .email(email)
	                    .password(passwordEncoder.encode("1111")) // 기본 비밀번호
	                    .role("USER")
	                    .name("김아무개")
	                    .nickname("둘리")
	                    .loginType(registrationId)
	                    .createdate(LocalDateTime.now())
	                    .build();

	            userRepository.save(user);
	        } else {
	            user = userOpt.get();
	        }

	        return new PrincipalDetails(user, attributes);
	    }

	    private String extractEmail(Map<String, Object> attributes, String provider) {
	        if ("kakao".equals(provider)) {
	            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
	            return (String) kakaoAccount.get("email");
	        } else if ("google".equals(provider)) {
	            return (String) attributes.get("email");
	        }
	        throw new OAuth2AuthenticationException("알 수 없는 소셜 로그인 제공자: " + provider);
	    }
	}