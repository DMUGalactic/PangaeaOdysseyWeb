package com.PangaeaOdyssey.PangaeaOdyssey.jwt;

import com.PangaeaOdyssey.PangaeaOdyssey.DTO.CustomUserDetails;
import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Member;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        //토큰이 없거나 인증방식이 이상하면 종료
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        //Bearer 부분 제거 후 순수 토큰만
        String token = authorization.split(" ")[1];

        //존재하는데 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        //토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        //userEntity를 생성하여 값 set
        Member userEntity = new Member();
        userEntity.setId(username);
        //비밀번호값은 토큰에 담겨있지 않지만 가짜 비밀번호를 하나 만들어 놓음
        userEntity.setPassword("temppassword");
        //userEntity.setRole(); -> Authority라 안 들어가짐
        userEntity.setRole(role); // RoleEnum이 실제 enum 타입이라고 가정
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
        try {
            if (jwtUtil.isExpired(token)) {
                System.out.println("token expired");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expired");
                return;
            }
    } catch (ExpiredJwtException e) {
            System.out.println("1");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expired");
        return;
    } catch (JwtException e) {
            System.out.println("2");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        return;
    } catch (IllegalArgumentException e) {
            System.out.println("3");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token format");
        return;
    } catch (Exception e) {
            System.out.println("");
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
        return;
    }

        filterChain.doFilter(request, response);
    }
}
