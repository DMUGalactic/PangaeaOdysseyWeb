package com.PangaeaOdyssey.PangaeaOdyssey.Controller;

import com.PangaeaOdyssey.PangaeaOdyssey.DTO.UserSignUpDto;
import com.PangaeaOdyssey.PangaeaOdyssey.Repository.MemberRepository;
import com.PangaeaOdyssey.PangaeaOdyssey.Service.JwtService;
import com.PangaeaOdyssey.PangaeaOdyssey.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;
    private final MemberRepository userRepository;
    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = jwtService.extractAccessToken(request).orElse(null);
        String refreshToken = jwtService.extractRefreshToken(request).orElse(null);

        if (refreshToken != null) {
            userService.logout(refreshToken);  // 서비스 메서드를 호출하여 로그아웃 처리 
            log.info("UserService로그아웃 함수 호출 됨");
        }

        // 응답 헤더에서 토큰 제거
        jwtService.setAccessTokenHeader(response, "");
        jwtService.setRefreshTokenHeader(response, "");
        log.info("로그아웃 요청 처리 중. Refresh Token: {}", refreshToken);
        // 응답 인코딩 설정
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        return "로그아웃 되었습니다.";
    }
}