package com.example.demo.kakao.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Service
@RequiredArgsConstructor
public class KakaoService {

	@Autowired
	private final UserRepository userRepository;
	
	@Value("${kakao.client.id")
	private String KAKAO_CLIENT_ID;
	
	@Value("${kakao.redirect.url")
	private String KAKAO_REDIRECT_URL;
	
	@Value("${kakao.redirect.url")
	private String KAKAO_REDIRECT_URL;
	
	
	private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
	
	private final static String KAKAO_API_URI = "https://kapi.kakao.com";
	
	
	
	
	
	
}
