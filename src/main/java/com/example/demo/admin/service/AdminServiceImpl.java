package com.example.demo.admin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.admin.dto.AdminDto;
import com.example.demo.admin.entity.Admin;
import com.example.demo.admin.repository.AdminRepository;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.post.repository.PostRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public boolean register(AdminDto dto) {
        try {
            Optional<Admin> findAdmin = repository.findById(dto.getAdminid());
            if (findAdmin.isPresent()) {
                System.out.println("이미 사용중인 관리자입니다.");
                return false;
            }
            Admin entity = dtoToEntity(dto);
            repository.save(entity);
            System.out.println("저장완료");
            return true;
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            return false;
        }
    }

    // 유저 권한 변경 (예: ROLE_USER → ROLE_ADMIN or ROLE_ADMIN → ROLE_USER)
    public boolean changeUserRole(int userId, String newRole) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            System.out.println("유저를 찾을 수 없습니다.");
            return false;
        }
        User user = userOpt.get();
        user.setRole(newRole);
        userRepository.save(user);
        return true;
    }

    // 유저 강제 탈퇴
    public boolean forceDeleteUser(int userId) {
        if (!userRepository.existsById(userId)) {
            System.out.println("유저를 찾을 수 없습니다.");
            return false;
        }
        userRepository.deleteById(userId);
        return true;
    }

    // 게시글 삭제
    public boolean deletePost(int postId) {
        if (!postRepository.existsById(postId)) {
            System.out.println("게시글을 찾을 수 없습니다.");
            return false;
        }
        postRepository.deleteById(postId);
        return true;
    }

    // 댓글 삭제
    public boolean deleteComment(int commentId) {
        if (!commentRepository.existsById(commentId)) {
            System.out.println("댓글을 찾을 수 없습니다.");
            return false;
        }
        commentRepository.deleteById(commentId);
        return true;
    }

    @Override
    public AdminDto read(int id) {
        return null;
    }

    
    public Admin dtoToEntity(AdminDto dto) {
        return Admin.builder()
                .adminid(dto.getAdminid())
                .managername(dto.getManagername())
                .build();
    }

    @Override
    public boolean grantAdmin(int userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            System.out.println("유저를 찾을 수 없습니다.");
            return false;
        }

        User user = userOpt.get();

        // 1. admin 테이블에 추가
        Admin admin = Admin.builder()
            .managername(user.getName())
            .build();

        repository.save(admin);

        // 2. 사용자 권한을 ROLE_ADMIN으로 변경
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);

        System.out.println("관리자 권한 부여 완료");
        return true;
    }
	@Override
	public void deleteById(int userId) {
		// TODO Auto-generated method stub
		
	}
}
