package com.PangaeaOdyssey.PangaeaOdyssey.Service;

import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Member;
import com.PangaeaOdyssey.PangaeaOdyssey.Repository.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Optional;

public class CustomLogoutFilter extends GenericFilterBean {
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    public CustomLogoutFilter(JwtService jwtService, MemberRepository memberRepository) {
        this.jwtService = jwtService;
        this.memberRepository = memberRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/logout$")) {

            filterChain.doFilter(request, response);
            return;
        }
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }
        //get refresh token
        String refresh = null;
        refresh = request.getHeader("Authorization");

    // "Bearer "라는 접두사를 제거합니다 (보통 헤더에 "Bearer <토큰>" 형식으로 토큰을 보냅니다)
        if (refresh != null && refresh.startsWith("Bearer ")) {
            refresh = refresh.substring(7);
        }

    // refresh null 체크
        if (refresh == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

    // 만료 여부 확인
        try {
            jwtService.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            // response 상태 코드 설정
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

    // 토큰이 refresh인지 확인 (발급 시 페이로드에 명시됨)
        String category = jwtService.getCategory(refresh);
        if (!"refresh".equals(category)) {
            // response 상태 코드 설정
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

    // DB에 저장되어 있는지 확인
        Boolean isExist = memberRepository.existsByRefreshToken(refresh);
        if (!isExist) {
            // response 상태 코드 설정
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    // DB에 저장되어 있는지 확인
        Optional<Member> memberOptional = memberRepository.findByRefreshToken(refresh);
        if (memberOptional.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 로그아웃 진행 - Refresh 토큰 제거
        Member member = memberOptional.get();
        member.clearRefreshToken(); // Refresh 토큰 제거
        memberRepository.save(member); // 업데이트된 엔티티 저장
    }
}