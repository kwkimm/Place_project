package com.example.sb.oauth;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String attribueName;
	private String userId;
	private String nickname;
	private String email;
	private String phone;
	
	public static OAuthAttributes of(String registrantionId, String attributeName, Map<String, Object> attributes) {
		if(registrantionId.equals("naver")) {
			return ofNaver(attributeName, attributes);
		}else if(registrantionId.equals("kakao")) {
			return ofKakao(attributeName, attributes);
		}else {
			return ofGoogle(attributeName, attributes);
		}
	}
	
	public static OAuthAttributes ofNaver(String attributeName, Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");
		
		String userId = (String) response.get("email");
		String nickName = (String) response.get("nickname");
		String email = (String) response.get("email");
		
		response.forEach((key, value)->{
			System.out.println(key + " : " + value);
		});
		
		return new  OAuthAttributes(attributes, attributeName, userId, nickName, email, null);
	}
	
	public static OAuthAttributes ofKakao(String attributeName, Map<String, Object> attributes) {
		Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
		
		String userId = (String) kakao_account.get("email");
		String nickName = (String) properties.get("nickname");
		String email = (String) kakao_account.get("email");
		
		return new  OAuthAttributes(attributes, attributeName, userId, nickName, email, null);
	}
public static OAuthAttributes ofGoogle(String attributeName, Map<String, Object> attributes) {
		
		String userId = (String) attributes.get("email");
		String name = (String) attributes.get("name");
		String email = (String) attributes.get("email");
		
		return new OAuthAttributes(attributes, attributeName, userId, name, email, null);
	}

}

