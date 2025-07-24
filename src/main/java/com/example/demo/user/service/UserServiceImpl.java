package com.example.demo.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.user.dto.SignupDto;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.util.S3FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    S3FileUtil fileUtil;

    // 일반 회원가입(UserDto 이용)
    @Override
    public boolean register(UserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setRole("ROLE_USER");        // 권한 고정 (ROLE_USER)
        dto.setLoginType("local");       // 로그인 타입 local 고정
        User user = dtoToEntity(dto);
        userRepository.save(user);
        return true;
    }

    // 회원가입(SignupDto + 파일 업로드)
    @Override
    public boolean signup(SignupDto dto, MultipartFile file) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("이미 등록된 이메일입니다.");
        }
        
        String uploadedFileName = fileUtil.fileUpload(file);
        
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .profileimg(uploadedFileName)
                .role("ROLE_USER")        // 권한 고정 (ROLE_USER)
                .loginType("local")       // 로그인 타입 local 고정
                .build();

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
    public boolean modify(UserDto dto) {
        Optional<User> optional = userRepository.findById(dto.getUserid());
        if(optional.isPresent()) {
            User entity = optional.get();
            entity.setNickname(dto.getNickname());
            entity.setProfileimg(dto.getProfileimg());
            // 필요하다면 추가 수정 가능
            return true;
        }       
        return false;
    }
    
    @Override
    public UserDto readByEmail(String email) {
        Optional<User> result = userRepository.findByEmail(email);
        return result.map(this::entityToDto).orElse(null);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::entityToDto) // User → UserDto 변환
                .collect(Collectors.toList());
    }

    // DTO → Entity 변환
    private User dtoToEntity(UserDto dto) {
        return User.builder()
                .userid(dto.getUserid())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .profileimg(dto.getProfileimg())
                .role(dto.getRole())
                .loginType(dto.getLoginType() != null ? dto.getLoginType() : "local")
                .createdate(dto.getCreatedate())
                .build();
    }

    // Entity → DTO 변환
    private UserDto entityToDto(User entity) {
        return UserDto.builder()
                .userid(entity.getUserid())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .profileimg(entity.getProfileimg())
                .role(entity.getRole())
                .loginType(entity.getLoginType())
                .createdate(entity.getCreatedate())
                .build();
    }

    
    @Override
    public boolean signup(SignupDto dto) {
        return false;
    }
    @Override
    public void deleteById(int userId) {
    	if(userRepository.existsById(userId)) {
    		userRepository.deleteById(userId);
    	}
    }
    
}
