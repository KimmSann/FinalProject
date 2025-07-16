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
}
