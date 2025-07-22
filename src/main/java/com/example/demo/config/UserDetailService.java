package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private UserService service;

//    private final UserRepository userRepository;

	// 필요한 코드
	// 로그인 성공 => 이메일 주소를 통해 사용자 정보 조회 => 시큐리티 내부에서 사용하는 user데이터 변환
	// 로그인 실패 => 에러 발생
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	
  		UserDto user = service.readByEmail(email);

		if(user == null) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다"); 
		} else {
			// 왜? 로그인 성공 => 페이지 이동시 이전에 로그인한 사용자의 정보 보관
			// 반환된 사용자 정보를 시큐리티가 보관하다가 필요할때 꺼내줌!
			return new CustomUser(user); 
		}
    	
//        return (UserDetails) userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException((email)));
    }
}