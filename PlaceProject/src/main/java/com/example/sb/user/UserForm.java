package com.example.sb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {
	
	@NotEmpty(message = "아이디를 입력하세요.")
	private String userId;
	
	@NotEmpty(message = "닉네임을 입력하세요.")
	private String nickName;
	
	@NotEmpty(message = "비밀번호를 입력하세요.")
	//@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{4,16}", message = "비밀번호는 4~16자 영문 소문자, 숫자를 사용하세요.")
	private String password;
	
	@NotEmpty(message = "비밀번호 확인을 입력하세요.")
	private String password2;
	
	@NotEmpty(message = "전화번호를 입력하세요.")
	private String phone;
	
	@Email
	@NotEmpty(message = "이메일을 입력하세요.")
	private String email;
}
