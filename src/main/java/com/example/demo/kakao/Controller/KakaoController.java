package com.example.demo.kakao.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class KakaoController {

//	private final KakaoService kakaoService;
	
	@GetMapping("/callback")
	public ResponseEntity<T> callback(HttpServletRequest request) throws Exception{
		KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));
		
		return ResponseEntity.ok()
				.body(new MsgEntity("Success", kakaoInfo)));
	}
}

