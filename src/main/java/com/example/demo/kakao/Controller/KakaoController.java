//package com.example.demo.kakao.Controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.example.demo.kakao.Service.KakaoService;
//
//import jakarta.servlet.http.HttpSession;
//
//@Controller
//public class KakaoController {
//	
//	@GetMapping("kakaoTerms")
//	public String kakaoConnect() {
//
//		StringBuffer url = new StringBuffer();
//		url.append("https://kauth.kakao.com/oauth/authorize?");
//		url.append("client_id=" + "33672314857bf92f484135c75910f9d9");
//		url.append("&redirect_uri=http://localhost:8080/signin");
//		url.append("&response_type=code");
//
//		return "redirect:" + url.toString();
//	}
//	
//	@Autowired
//	private KakaoService kakaoService ;	
//    
//    @RequestMapping(value = "/kakao")
//	public String kakaoLogin(@RequestParam("code") String code, HttpSession session) throws Exception {
//		
//		String access_token = kakaoService.getToken(code);//code로 토큰 받음
//		System.out.println("access_token : " + access_token);
//		
//		
//		return "";
//	}
//
//
//}
//
