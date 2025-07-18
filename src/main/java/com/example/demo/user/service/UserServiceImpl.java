package com.example.demo.user.service;

import java.util.Optional;

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

    // 따로 s3fileutil에 String형태로 aws 에 저장했으니 현재걸로 병합해주세요*****
    @Override
    public boolean signup(SignupDto dto, MultipartFile file) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("이미 등록된 이메일입니다.");
        }
        
        String testImg = fileUtil.fileUpload(file);
        
        System.out.println(testImg);

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getName())
                .profileimg(fileUtil.fileUpload(file))
                .role("USER")
                .build();

        userRepository.save(user);
        return true;  // 가입 성공 시 true 반환
    }

    
	@Override
	public boolean modify(UserDto dto) {
		
		// 어짜피 수정까지 가면 이미 인증은 되어있는 상태니깐 굳이같은지 확인 안하기
		Optional<User> optional = userRepository.findById(dto.getUserid());
		
		if(optional.isPresent()) {
			User entity = optional.get();
			entity.setNickname(dto.getNickname());
			entity.setProfileimg(dto.getProfileimg());
			return true;
			
		}		
		return false;
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


}