package com.example.stocks.filter;

import com.example.stocks.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 인증 필터
 * 각 요청마다 한 번만 실행되며, Authorization 헤더를 확인하고 jwt 토큰을 추출하는 클래스
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;// JWT 관련 처리를 담당하는 Provider 클래스

    /**
     * JWT 인증 펄터
     * @param request HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     * @param filterChain FilterChain 객체
     * @throws ServletException 필터 처리 중 발생하는 Servlet 예외
     * @throws IOException 필터 처리 중 발생하는 IO 예외
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 요청에서 Bearer 토큰 추출
            String token = parseBearerToken(request);
            if (token == null) {
                // 토큰이 없으면 다음 필터로 요청 전달 
                filterChain.doFilter(request, response);
                return;
            }

            // JWT 토큰 검증 및 이메일 추출
            String email = jwtProvider.validate(token);
            if(email == null) {
                // 토큰이 유효하지 않거나 이메일이 없으면 다음 필터로 요청 전달
                filterChain.doFilter(request, response);
                return;
            }

            // 인증 객체 생성
            AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,null, AuthorityUtils.NO_AUTHORITIES);
            authenticationToken.setDetails(new WebAuthenticationDetails(request));

            //SecurityContext에 인증 객체 저장
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);
        }catch (Exception e) {
            e.printStackTrace();
        }
        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    /**
     * Authorization 헤더에서 Bearer 토큰을 추출
     * @param request HttpServletRequest 객체
     * @return Bearer 토큰 문자열(존재하지 않는 경우 null 반환)
     */
    private String parseBearerToken(HttpServletRequest request) {
        // Authorization 헤더 가져오기
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더가 Bearer 타입인지 확인
        boolean isBearerAuthorization = StringUtils.hasText(authorization) && authorization.startsWith("Bearer");
        if (!isBearerAuthorization) return null;

        //"Bearer " 이후의 토큰 문자열 반환
        System.out.println(authorization.substring(7));
        return authorization.substring(7);
    }
}
