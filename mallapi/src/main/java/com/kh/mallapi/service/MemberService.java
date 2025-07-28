package com.kh.mallapi.service;

import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.kh.mallapi.domain.Member;
import com.kh.mallapi.dto.MemberDTO;
import com.kh.mallapi.dto.MemberModifyDTO;

@Transactional
public interface MemberService {
	MemberDTO getKakaoMember(String accessToken);

	public void modifyMember(MemberModifyDTO memberModifyDTO);

	default MemberDTO entityToDTO(Member member) {
		MemberDTO dto = new MemberDTO(member.getEmail(), member.getPw(), member.getNickname(), member.isSocial(),
				member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList()));
		return dto;
	}
}