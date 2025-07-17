package com.example.demo.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {
    String name;
    String email;
    String password;
    String nickname;
    String profileName;
    
}
