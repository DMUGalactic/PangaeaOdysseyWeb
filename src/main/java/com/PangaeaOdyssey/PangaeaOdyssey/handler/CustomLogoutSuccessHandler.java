package com.PangaeaOdyssey.PangaeaOdyssey.handler;

import com.PangaeaOdyssey.PangaeaOdyssey.Service.JwtService;
import com.PangaeaOdyssey.PangaeaOdyssey.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private JwtService jwtService;
    private UserService userService;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("로그아웃 성공 처리됨");

        // Refresh Token 추출
        String refreshToken = jwtService.extractRefreshToken(request).orElse(null);
        if (refreshToken != null) {
            userService.logout(refreshToken);  // Refresh Token으로 로그아웃 처리
            log.info("UserService 로그아웃 함수 호출 됨");
        } else {
            log.info("Refresh Token이 존재하지 않음");
        }

        // 응답 설정
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("로그아웃에 성공하였습니다.");
    }

}