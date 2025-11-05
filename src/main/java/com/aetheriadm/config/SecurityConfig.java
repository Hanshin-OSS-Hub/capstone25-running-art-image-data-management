package com.aetheriadm.config;

import com.aetheriadm.jwt.infrastructure.JwtAccessDeniedHandler;
import com.aetheriadm.jwt.infrastructure.JwtAuthenticationEntryPoint;
import com.aetheriadm.jwt.infrastructure.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    
    /**
     * 비밀번호 암호화를 위한 PasswordEncoder Bean을 등록합니다.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF, Form-Login, Http-Basic 비활성화
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            // 세션 관리를 STATELESS로 설정 (JWT 사용)
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 요청별 권한 설정
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/webjars/swagger-ui/**",
                            "/api-docs/**"
                    ).permitAll()
                    
                    // TODO: 여기에 다른 경로 규칙 추가 (예: /api/admin/** 은 'ADMIN' 권한 필요)
                    // .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    // 위에서 지정한 경로 외의 모든 경로는 인증 필요
                    .anyRequest().authenticated()
            )
            
            // JWT 필터 추가
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        
            // 인증/인가 예외 처리 핸들러 추가
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)  // 인증 실패 (401)
                .accessDeniedHandler(jwtAccessDeniedHandler)            // 인가 실패 (403)
            );

        return http.build();
    }
}