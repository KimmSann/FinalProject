package com.example.demo.admin.service;

import com.example.demo.admin.dto.AdminDto;
import com.example.demo.admin.entity.Admin;

public interface AdminService {
	
	default AdminDto entityToDto(Admin admin) {
		
		AdminDto dto = AdminDto.builder()
				.adminid(admin.getAdminid())
				.managername(admin.getManagername())
				.build();
		
		return dto;
	}
	
	default Admin dtoToEntity(AdminDto dto) {
		
		Admin admin = Admin.builder()
				.adminid(dto.getAdminid())
				.managername(dto.getManagername())
				.build();
		
		return admin;
	}
	
}
