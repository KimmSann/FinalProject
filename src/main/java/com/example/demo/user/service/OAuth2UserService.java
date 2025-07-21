package com.example.demo.user.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId(); // "kakao"
        String providerId = oauth2User.getName(); // 고유 ID
        Map<String, Object> attributes = oauth2User.getAttributes();

        // 카카오 계정 이메일 추출
        String email = null;
        if (provider.equals("kakao")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            email = (String) kakaoAccount.get("email");
        }

        // DB에 해당 이메일 유저가 있는지 확인
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            // 회원가입 처리
            user = User.builder()
                    .email(email)
                    .password(new BCryptPasswordEncoder().encode("1111"))
                    .name("카카오사용자")
                    .nickname("카카오닉네임")
                    .profileimg("default.png")
                    .role("user")
                    .loginType("kakao")
                    .createdate(LocalDateTime.now())
                    .build();
            userRepository.save(user);
        }

        return new PrincipalDetails(user, attributes); // 사용자 정보와 OAuth2 속성 전달
    }
}