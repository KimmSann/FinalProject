package com.example.demo.admin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.admin.dto.AdminDto;
import com.example.demo.admin.entity.Admin;
import com.example.demo.admin.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminRepository repository;
	
	@Override
	public boolean register(AdminDto dto) {
		
		try {
			Optional<Admin> findAdmin = repository.findById(dto.getAdminid());
			if(findAdmin.isPresent()) {
				System.out.println("이미 사용중인 관리자입니다.");
				return false;
			}
			
			// 관리자는 비밀번호 그런거 필요없으니 그냥 등록
			// 게시판에 관리자 막주면 곤란하니 소수만 정해놓고 수정불가
			Admin entity = dtoToEntity(dto);
			
			repository.save(entity);
			System.out.println("저장완료");
			return true;
		}
		catch (Exception e) {
			System.out.println("ERROR : " + e);
			return false;
		}
		
	}

	// 관리자 읽기가 필요할까?
	// 일단 보류
	@Override
	public AdminDto read(int id) {
		
		return null;
	}

}
