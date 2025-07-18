package com.example.demo.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {
    private String name;
    private String email;
    private String password;
    private String nickname;
    private String role; 
    private String profileimg;  // 따로 s3fileutil에 String형태로 aws 에 저장했으니 현재걸로 병합해주세요*****
}