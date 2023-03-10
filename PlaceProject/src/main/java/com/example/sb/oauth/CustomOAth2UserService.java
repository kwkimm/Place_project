package com.example.sb.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.sb.user.UserEntity;
import com.example.sb.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAth2UserService extends DefaultOAuth2UserService{
	
	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// 인가코드, 토큰, 유저정보가 처리되는 과정이 다 들어가도록
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		
		// 소셜 로그인을 구분하는 id(google, kakao, naver)
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		
		// 소셜별 속성 키 값 : (google=sub, naver=response, kakao=id)
		String attributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		
		// 소셜벌로 구분해서 각각 메서드 넣어줌
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, attributeName, oAuth2User.getAttributes());
		
		// 웹사이트 회원의 이메일기준으로 찾아보기
		UserEntity user = userRepository.findByUserId(attributes.getEmail()).orElseGet(()->{
			return new UserEntity();
		});
		
		if(user.getEmail() == null) {
			user.setUserId(attributes.getEmail());
			user.setEmail(attributes.getEmail());
			user.setNickName(attributes.getNickname());
			user.setPhone(null);
			user.setPw("1234");
			
			userRepository.save(user);
		}
		return new CustomUserDetails(user, attributes.getAttributes());
	}
	
	
}
