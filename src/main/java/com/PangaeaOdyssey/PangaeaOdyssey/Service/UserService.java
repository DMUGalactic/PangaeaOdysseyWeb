package com.PangaeaOdyssey.PangaeaOdyssey.Service;

import com.PangaeaOdyssey.PangaeaOdyssey.DTO.UserSignUpDto;
import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Member;
import com.PangaeaOdyssey.PangaeaOdyssey.Enum.Role;
import com.PangaeaOdyssey.PangaeaOdyssey.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
        if (memberRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (memberRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        Member user = Member.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        memberRepository.save(user);
    }
    @Transactional
    public void logout(String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken)
                .ifPresent(user -> {
                    user.updateRefreshToken(null);  // Refresh Token을 무효화
                    memberRepository.save(user);
                });
    }
    public void updateUserRoleToUser(String email) {
        memberRepository.findByEmail(email).ifPresent(user -> {
            if (user.getRole() == Role.GUEST) {
                user.authorizeUser();  // 역할을 USER로 변경
                memberRepository.save(user);
                log.info("사용자의 역할이 USER로 변경되었습니다. 이메일: {}", email);
            }
        });
    }
}