package com.example.sb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.sb.oauth.CustomOAth2UserService;

//import com.example.sb.google.OAuth2UserDetailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//환경설정 파일이다
@Configuration
@EnableWebSecurity // 사용자가 요청하면 여기서 먼저 제어할 수 있게끔 만들어주는 어노테이션
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	private final CustomOAth2UserService customOAth2UserService;
	
	@Bean // javaBean같은것 - 여기를 한번 거쳐 나가게
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		//사용자 인증 정보 받아오기 
		http.authorizeHttpRequests() // 권한관련
		.requestMatchers(new AntPathRequestMatcher("/**")) // 모든 사용자가 접근할 수 있게끔
		.permitAll()
		.and() 
			.csrf()
			.disable()
			.formLogin()
			.loginPage("/user/login") // 로그인 설정 페이지로 넘어갈것이다.
			.defaultSuccessUrl("/") // 로그인이 성공했을 때 넘어갈 페이지 - 메인페이지
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
		.and()
			.oauth2Login()
			.userInfoEndpoint()
			.userService(customOAth2UserService);
		
		return http.build();
	
	}

	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		
		return authenticationConfiguration.getAuthenticationManager();
	}
}
