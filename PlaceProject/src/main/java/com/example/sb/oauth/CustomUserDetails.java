package com.example.sb.oauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.sb.user.UserEntity;

import lombok.Data;

/*
 * UserDetails : Spring Security에서 사용자의 정보를 담는 인터페이스
 */
@Data
public class CustomUserDetails implements OAuth2User, UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private UserEntity user; // 유저정보 비교하기위한 UserEntity 생성
	private Map<String, Object> attributes; // 각종 속성 저장하기 위한 변수

	/**
	 * @param user
	 * @param attributes
	 * oauth - 소셜로그인 관련 코드
	 */
	public CustomUserDetails(UserEntity user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	/*
	 * 일반로그인 관련 메서드
	 */
	public CustomUserDetails(UserEntity user) {
		this.user = user;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//권한리턴
		Collection<GrantedAuthority> c = new ArrayList<>();
		
		c.add(() -> {
			return "ROLE_USER";
		});
		
		return c;
	}
	
	public String getPhone() {
		return user.getPhone();
	}
	
	public String getEmail() {
		//사용자 메일
		return user.getEmail();
	}
	
	public String getNickName() {
		// 사용자 닉네임
		return user.getNickName();
	}

	public Long getIdCode(){
		
		return user.getIdCode();
	}

	@Override
	public String getName() {
		//
		return user.getUserId();
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUserId();
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPw();
	}
	
	/*
	 * 계정이 만료되지 않았는지
	 * true : 만료 x 
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/*
	 * 계정이 잠기지 않았는지
	 * true : 잠김 x
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	/*
	 * 비밀번호가 만료되지 않았는지
	 * true : 만료 x
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * 계정이 활성화(사용가능한지)
	 * true : 활성화
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

}
