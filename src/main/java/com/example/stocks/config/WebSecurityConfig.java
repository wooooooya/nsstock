package com.example.stocks.config;

import com.example.stocks.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;

/**
 * 웹 보안 설정 클래스
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;// JWT 인증 처리를 위한 필터 클래스

    /**
     * HTTP 요청에 대한 보안 설정
     * @param httpSecurity HttpSecurity 객체
     * @return 보안 설정된 SecurityFilterChain 객체 반환
     * @throws Exception 보안 설정 중 발생한 예외
     */
    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors->cors.configurationSource(corsConfigrationSource()))// Cors 설정: Cross-Origin 요청을 처리
                .csrf(CsrfConfigurer::disable)// Csrf 설정: JWT를 사용하여 Csrf의 필요성이 없기에 비활성화
                .httpBasic(HttpBasicConfigurer::disable)// Http Basic 인증 비활성화
                .sessionManagement(configure->configure.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// 세션 관리 설정: StateLess 서버가 클라이언트의 상태를 저장하지 않음
                .authorizeHttpRequests(authorize->authorize// Http 요청 권한 설정
                        .requestMatchers("/","/api/v1/auth/**").permitAll()// 설정 주소의 모든 요청은 허용
                        .anyRequest().authenticated())// 이 외의 요청은 인증된 사용자만 접근 가능
                .exceptionHandling(authenticationException-> authenticationException.authenticationEntryPoint(new FailedAuthenticationEntryPoint()))// 인증 실패 시, 인증 실패 처리
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);// 사용자 정의 JWT 인증 필터를 UsernamePasswordAuthenticationFilter 전에 추가

        // 구성된 보안 필터 체인을 반환
        return httpSecurity.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigrationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}

/**
 * 인증 실패 시 응답을 처리하는 클래스
 */
class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 인증 실패 시 응답 처리
     * @param request 요청 객체
     * @param response 응답 객체
     * @param authException 인증 예외
     * @throws IOException 응답 처리 중 발생할 수 있는 입출력 예외
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);// 401(Unauthorized)
        response.getWriter().write("{ \"code\": \"AF\", \"message\": \"Authorization Failed\" }");
    }
}
