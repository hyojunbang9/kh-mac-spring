package com.kh.mallapi.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.mallapi.dto.MemberDTO;
import com.kh.mallapi.dto.MemberModifyDTO;
import com.kh.mallapi.service.MemberService;
import com.kh.mallapi.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
public class SocialController {
	private final MemberService memberService;

	@GetMapping("/api/member/kakao")
	public Map<String, Object> getMemberFromKakao(String accessToken) {
		log.info("access Token " + accessToken);
		// 카카오로 받은 엑세스 토큰을 갖고 사용자 이메일 -> 기존 회원이면 MemberDTO 리턴
		// 기존 회원이 아니면 임의 pw, 소셜로그인, 인가 멤버로 등록하고 MemberDTO
		MemberDTO memberDTO = memberService.getKakaoMember(accessToken);

		Map<String, Object> claims = memberDTO.getClaims();
		String jwtAccessToken = JWTUtil.generateToken(claims, 10);
		String jwtRefreshToken = JWTUtil.generateToken(claims, 60 * 24);

		claims.put("accessToken", jwtAccessToken);
		claims.put("refreshToken", jwtRefreshToken);

		return claims;
	}

	@PutMapping("/api/member/modify")
	public Map<String, String> modify(@RequestBody MemberModifyDTO memberModifyDTO) {
		log.info("member modify: " + memberModifyDTO);
		memberService.modifyMember(memberModifyDTO);
		return Map.of("result", "modified");
	}
}