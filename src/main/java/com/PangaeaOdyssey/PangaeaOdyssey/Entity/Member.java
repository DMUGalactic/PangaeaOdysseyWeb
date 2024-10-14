package com.PangaeaOdyssey.PangaeaOdyssey.Entity;

import com.PangaeaOdyssey.PangaeaOdyssey.Enum.Role;
import com.PangaeaOdyssey.PangaeaOdyssey.Enum.SocialType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column
    private String email; // 이메일
    @Column
    private String password; // 비밀번호
    @Column
    private String nickname; // 닉네임
    @Column
    private String imageUrl; // 프로필 이미지

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    private String refreshToken; // 리프레시 토큰

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

}
