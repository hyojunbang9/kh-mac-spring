package com.kh.mallapi.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO extends User {
	private static final long serialVersionUID = 1L;
	private String email;
	private String pw;
	private String nickname;
	private boolean social;
	private List<String> roleNames = new ArrayList<>();

	public MemberDTO(String email, String pw, String nickname, boolean social, List<String> roleNames) {
		// 이메일(사용자 아이디), 비밀번호, 권한목록(roles 또는 authorities)을 받아 UserDetails 객체로 만들어준다.
		// 시큐리티는 인증, 인가 모두 등록된다.
		super(email, pw,
				roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_" + str)).collect(Collectors.toList()));
		this.email = email;
		this.pw = pw;
		this.nickname = nickname;
		this.social = social;
		this.roleNames = roleNames;
	}
	
	//사용자 화면에 컨트롤러에서 MemberDTO 정보를 json 방식으로 보내기 위한 함수
	//후에 이 부분에 accessToken, refreshToken 추가 
	public Map<String, Object> getClaims() {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("email", email);
		dataMap.put("pw", pw);
		dataMap.put("nickname", nickname);
		dataMap.put("social", social);
		dataMap.put("roleNames", roleNames);
		return dataMap;
	}
}