package com.kh.mallapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kh.mallapi.domain.Member;
import com.kh.mallapi.domain.MemberRole;
import com.kh.mallapi.repository.MemberRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MemberApplicationTests {
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testInsertMember() {
		for (int i = 0; i < 10; i++) {
			Member member = Member.builder().email("user" + i + "@kh.com").pw(passwordEncoder.encode("123456"))
					.nickname("USER" + i).build();
			member.addRole(MemberRole.USER);
			if (i >= 5) {
				member.addRole(MemberRole.MANAGER);
			}
			if (i >= 8) {
				member.addRole(MemberRole.ADMIN);

			}
			memberRepository.save(member);
		}
	}

	@Test
	public void testRead() {
		String email = "user9@kh.com";
		Member member = memberRepository.getWithRoles(email);
		log.info("--------");
		log.info(member);
	}
}