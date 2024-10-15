package com.PangaeaOdyssey.PangaeaOdyssey.Controller;

import com.PangaeaOdyssey.PangaeaOdyssey.DTO.UserSignUpDto;
import com.PangaeaOdyssey.PangaeaOdyssey.Repository.MemberRepository;
import com.PangaeaOdyssey.PangaeaOdyssey.Service.JwtService;
import com.PangaeaOdyssey.PangaeaOdyssey.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MemberRepository memberRepository;
    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    @GetMapping("/admin-jwt-test")
    public String adminJwtTest(){
        return "adminTest 요청 성공";
    }

    @PostMapping("/custom-logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // SecurityContext에서 현재 인증된 사용자 정보를 가져옴
        log.info("로그아웃 컨트롤러 요청들어옴");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        if (authentication != null) {
            String email = authentication.getName();
            log.info("로그아웃 if문 들어옴");
            // 서비스 계층을 통해 Refresh Token 삭제 처리
            userService.logoutUser(email);
            log.info("리프레쉬 삭제됨");
            // 로그아웃 처리 - SecurityContext 무효화
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            log.info("사용자가 로그아웃되었습니다. 이메일: {}", email);
        }

        return "로그아웃 성공";
    }
}