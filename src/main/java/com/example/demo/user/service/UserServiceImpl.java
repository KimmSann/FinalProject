package com.example.demo.user.service;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean register(UserDto dto) {
        // 비밀번호 암호화 후 저장
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
    public UserDto login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return entityToDto(user); // 로그인 성공
            }
        }
        return null; // 로그인 실패
    }
}
