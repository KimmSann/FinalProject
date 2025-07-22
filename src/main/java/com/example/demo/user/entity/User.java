package com.example.demo.user.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;

    private String name;
    private String email;
    private String password;
    private String nickname;

    private String profileimg;

    private String role;
    
    private String loginType; // "local", "kakao"

    private LocalDateTime createdate;
    
   
    @PrePersist
    public void prePersist() {
        this.createdate = LocalDateTime.now();
        this.role = this.role == null ? "USER" : this.role;
        this.profileimg = this.profileimg == null ? "default.png" : this.profileimg;
    }
}
