package com.kh.mallapi.service;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.kh.mallapi.domain.Member;
import com.kh.mallapi.domain.MemberRole;
import com.kh.mallapi.dto.MemberDTO;
import com.kh.mallapi.dto.MemberModifyDTO;
import com.kh.mallapi.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public MemberDTO getKakaoMember(String accessToken) {
		String email = getEmailFromKakaoAccessToken(accessToken);
		log.info("email: " + email);
		// 기존의 회원(카카오로 받은 이메일이 내 멤버 테이블에 있는지 확인)
		Optional<Member> result = memberRepository.findById(email);
		if (result.isPresent()) {
			MemberDTO memberDTO = entityToDTO(result.get());
			return memberDTO;
		}

		// 회원이 아니었다면 // 닉네임은 '소셜회원'으로
		// 패스워드는 임의로 생성 된 member이 return됨.
		Member socialMember = makeSocialMember(email);
		memberRepository.save(socialMember);
		MemberDTO memberDTO = entityToDTO(socialMember);
		return memberDTO;
	}

	// 임의 패스워드 생성, 닉네임 : 소셜 회원 등록(사용자에게 패스워드 수정 요청)
	private Member makeSocialMember(String email) {
		String tempPassword = makeTempPassword();
		log.info("tempPassword: " + tempPassword);
		String nickname = "소셜회원";
		Member member = Member.builder().email(email).pw(passwordEncoder.encode(tempPassword)).nickname(nickname)
				.social(true).build();
		member.addRole(MemberRole.USER);
		return member;
	}

	private String makeTempPassword() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			buffer.append((char) ((int) (Math.random() * 55) + 65));
		}
		return buffer.toString();
	}

	private String getEmailFromKakaoAccessToken(String accessToken) {
		// 사용자정보를 가져오는 url
		String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";
		if (accessToken == null) {
			throw new RuntimeException("Access Token is null");
		}

		// RestTemplate 클래스는 자바코드로 외부 REST API 호출할 때 사용한다.
		RestTemplate restTemplate = new RestTemplate();

		// 이 아래가
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		HttpEntity<String> entity = new HttpEntity<>(headers);

		// 카카오 API 요청에 사용할 URL을 생성한다. Request 요청 https://kapi.kakao.com/v2/user/me;
		UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();
		ResponseEntity<LinkedHashMap> response = restTemplate.exchange(uriBuilder.toString(), // 요청 URL
				HttpMethod.GET, // HTTP 메서드 (GET)
				entity, // 요청 헤더가 포함된 HttpEntity (예: Authorization)
				LinkedHashMap.class // 응답 본문을 매핑할 클래스
		);
		log.info(response);
		LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();
		log.info(bodyMap);
		LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");
		log.info("kakaoAccount: " + kakaoAccount);
		return kakaoAccount.get("email");
	}

	@Override
	public void modifyMember(MemberModifyDTO memberModifyDTO) {
		Optional<Member> result = memberRepository.findById(memberModifyDTO.getEmail());
		Member member = result.orElseThrow();
		member.changePw(passwordEncoder.encode(memberModifyDTO.getPw()));
		member.changeSocial(false);
		member.changeNickname(memberModifyDTO.getNickname());
		memberRepository.save(member);
	}

}