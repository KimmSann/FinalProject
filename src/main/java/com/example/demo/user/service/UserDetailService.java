//package com.example.demo.user.service;
//
//import org.glassfish.jaxb.core.v2.runtime.IllegalAnnotationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.user.entity.User;
//import com.example.demo.user.repository.UserRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Service
//public class UserDetailService implements UserDetailsService {
//	
//	@Autowired
//	private final UserRepository userRepository;
//	
//	@Override
//	public User loadUserByUsername(String email) throws UsernameNotFoundException{
//		return userRepository.findByEmail(email)
//				.orElseThrow(() -> new IllegalAnnotationException(email, "not found"));
//	}
//
//}
