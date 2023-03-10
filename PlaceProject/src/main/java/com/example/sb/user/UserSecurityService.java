package com.example.sb.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.sb.oauth.CustomUserDetails;

import lombok.RequiredArgsConstructor;

/*
 * DB에 저장된 유저id, pw 가져와서 시큐리티에 세션으로 값리턴하여 유지하는 클래스
 * 권한설정도 가능한 클래스이지만 현재 우리는 권한 설정이 없음
 * UserDetailsService : Spring Security에서 유저의 정보를 가져오는 인터페이스
 */
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// DB에 id가 있는지 확인하는 작업 - username은 애초에 시큐리티에서 읽어오는 방식임 어차피 repository에서 ByUserId로 DB에 등록된
		// userId 컬럼을 검색하기 때문에 username메소드는 유지해야함
		Optional<UserEntity> _userE = userRepository.findByUserId(username);
		
		if(_userE.isEmpty()) {
			throw new UsernameNotFoundException("가입되지 않은 회원입니다.");
		}
		
		UserEntity userE = _userE.get();
		
		return new CustomUserDetails(userE);
	}

}
