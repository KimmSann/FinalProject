package com.example.demo.kakao.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.kakao.Service.*;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping
public class KakaoController {

    @Value("${kakao.client_id}")
    private String client_id;

    @Autowired
    private KakaoService kakaoService;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code) throws IOException {
        String accessToken = kakaoService.getAccessTokenFromKakao(client_id, code);
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(accessToken);
        log.info("id : " + userInfo.get("id"));
        // User 로그인, 또는 회원가입 로직 추가
        return userInfo.toString();
    }
}