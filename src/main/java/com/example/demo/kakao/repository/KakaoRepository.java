package com.example.demo.kakao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.user.entity.User;

public interface KakaoRepository extends JpaRepository<User,Integer> {

}
