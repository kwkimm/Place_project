package com.example.sb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {
	
	private final UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/signup")
	public String signup(UserForm userForm) {
		return "join_form";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserForm userForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "join_form";
		}
		
		if(!userForm.getPassword().equals(userForm.getPassword2())) {
			bindingResult.rejectValue("password2", "pwNotEquals", "패스워드가 다름");
			return "join_form";
		}
		
		try {
			userService.create(userForm.getUserId(), userForm.getNickName(), userForm.getPassword(),userForm.getPhone(), userForm.getEmail());
		}catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("아이디 중복 오류", "이미 가입된 아이디입니다.");
			return "join_form";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
	
	@GetMapping("/userup")
	public String userup(UserForm userForm) {
		return "user_details";
	}
	
	@PostMapping("/userup/{idCode}")
	public String userup(@Valid UserForm userForm, BindingResult bindingResult, @PathVariable("idCode") Long idCode) {
		
		if(bindingResult.hasErrors()) { // 정규식 문제생기면 user_detail.html로 다시 돌아가고 Errors 메시지(UserForm에있는 message) 띄어줌
			return "user_details";
		}
		
		if(!userForm.getPassword().equals(userForm.getPassword2())) {
			bindingResult.rejectValue("password2", "pwNotEquals", "패스워드가 다름");
			return "user_details";
		}
		
		System.out.println(idCode);
		
		UserEntity user = userService.getUserInfo(idCode);
		
		userService.userUpdate(user, userForm.getNickName(), userForm.getPassword(), userForm.getPhone(), userForm.getEmail());
		
		/** ========== 변경된 세션 등록 ========== **/
	   /* 1. 새로운 UsernamePasswordAuthenticationToken 생성하여 AuthenticationManager 을 이용해 등록 */
	    Authentication authentication = authenticationManager.authenticate(
	    		new UsernamePasswordAuthenticationToken(userForm.getUserId(), userForm.getPassword())
	    );

	  /* 2. SecurityContextHolder 안에 있는 Context를 호출해 변경된 Authentication으로 설정 */
	   SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "user_details";
	}
}

