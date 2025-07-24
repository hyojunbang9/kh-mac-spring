package com.kh.mallapi.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kh.mallapi.security.filter.JWTCheckFilter;
import com.kh.mallapi.security.handler.APILoginFailHandler;
import com.kh.mallapi.security.handler.APILoginSuccessHandler;
import com.kh.mallapi.security.handler.CustomAccessDeniedHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class CustomSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("-----scrurityConfig------");

		// 프론트엔드에서 오는 교차 출처 요청(CORS)을 이 설정에 따라 허용
		http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
				.configurationSource(corsConfigurationSource()));

		// 세션을 생성하지 않음 (stateless). JWT 같은 토큰 기반 인증 시스템에서 사용
		http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// CSRF(Cross-Site Request Forgery) 보호 기능을 비활성화
		// REST API 서버에서는 일반적으로 CSRF 보호가 필요 없기 때문에 끄는 것이 일반적
		http.csrf(config -> config.disable());

		// 로그인페이지 URL 을 /api/member/login 지정하고, 인증되지 않은 사용자가 보호된 리소스를 요청하면 이 URL 로
		// 리다이렉트된다.
		http.formLogin(config -> {
			config.loginPage("/api/member/login");
			// 로그인 성공 시 실행될 핸들러 객체를 지정 코드
			config.successHandler(new APILoginSuccessHandler());
			// 로그인 실패 시 실행될 핸들러 객체를 지정 코드
			config.failureHandler(new APILoginFailHandler());
		});
		// JWT 체크 추가(시큐리티 처리 전 필터 진행)
		http.addFilterBefore(new JWTCheckFilter(), UsernamePasswordAuthenticationFilter.class);

		// 권한이 허가 되지 않았을 때 예외처리 메시지 처리
		http.exceptionHandling(config -> {
			config.accessDeniedHandler(new CustomAccessDeniedHandler());
		});
		
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
