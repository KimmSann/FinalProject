package com.example.demo.user.service;

import java.util.Optional;

import com.example.demo.user.dto.LoginDto;
import com.example.demo.user.dto.SignupDto;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean register(UserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = dtoToEntity(dto);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDto read(int id) {
        return userRepository.findById(id)
                .map(this::entityToDto)
                .orElse(null);
    }

    @Override
    public UserDto readByUserName(String nickname) {
        Optional<User> result = userRepository.findByNickname(nickname);
        return result.map(this::entityToDto).orElse(null);
    }

    @Override
    public UserDto login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return entityToDto(user);
            }
        }
        return null;
    }

    @Override
    public boolean signup(SignupDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("이미 등록된 이메일입니다.");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname()) // dto.getNickname() 사용
                .role(dto.getRole())         // 폼에서 받은 role 반영
                .build();

        userRepository.save(user);
        return true;
    }

    private User dtoToEntity(UserDto dto) {
        return User.builder()
                .userid(dto.getUserid())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .profileimg(dto.getProfileimg())
                .role(dto.getRole())
                .createdate(dto.getCreatedate())
                .build();
    }

    private UserDto entityToDto(User entity) {
        return UserDto.builder()
                .userid(entity.getUserid())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .profileimg(entity.getProfileimg())
                .role(entity.getRole())
                .createdate(entity.getCreatedate())
                .build();
    }

	@Override
	public UserDto readByEmail(String email) {
        Optional<User> result = userRepository.findByEmail(email);
        return result.map(this::entityToDto).orElse(null);
	}
}
