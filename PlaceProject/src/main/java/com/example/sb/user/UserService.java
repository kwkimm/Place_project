package com.example.sb.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserEntity create(String userId, String nickName, String password, String phone, String email) {
		
		UserEntity user = new UserEntity();
		
		user.setUserId(userId); //userid
		user.setNickName(nickName);
		user.setPw(passwordEncoder.encode(password));
		user.setPhone(phone);
		user.setEmail(email);
		
		userRepository.save(user);

		return user;
	}
	
	public UserEntity getUserInfo(Long idCode) {
		
		Optional<UserEntity> userId = userRepository.findById(idCode);
		
		return userId.get();
	}
	
	public void userUpdate(UserEntity user, String nickName, String password, String phone, String email) {
		
		user.setNickName(nickName);
		user.setPw(passwordEncoder.encode(password));
		user.setPhone(phone);
		user.setEmail(email);
		
		userRepository.save(user);
	}
	
	public UserEntity getUser(String userId) {
		Optional<UserEntity> userE = userRepository.findByUserId(userId);
		
		if(userE.isPresent()) {
			return userE.get();
		}else {
			return new UserEntity();
		}
	}
}
