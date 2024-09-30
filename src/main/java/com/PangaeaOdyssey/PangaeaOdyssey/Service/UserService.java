package com.PangaeaOdyssey.PangaeaOdyssey.Service;

import com.PangaeaOdyssey.PangaeaOdyssey.DTO.UserSignUpDto;
import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Member;
import com.PangaeaOdyssey.PangaeaOdyssey.Enum.Role;
import com.PangaeaOdyssey.PangaeaOdyssey.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
        if(memberRepository.findById(userSignUpDto.getId()).isPresent()){
            throw new Exception("이미 존재하는 아이디입니다.");
        }
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
}