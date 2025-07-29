package com.kh.diaryapi.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.diaryapi.dto.DiaryDTO;
import com.kh.diaryapi.dto.PageRequestDTO;
import com.kh.diaryapi.dto.PageResponseDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class DiaryServiceTests {

	@Autowired
	private DiaryService diaryService;

////	@Test
//	public void testRegister() {
//		DiaryDTO diaryDTO = DiaryDTO.builder().title("서비스 테스트").writer("tester").dueDate(LocalDate.of(2025, 07, 27))
//				.build();
//		Long dno = diaryService.register(diaryDTO);
//		log.info("DNO: " + dno);
//	}
//
////	@Test
//	public void testGet() {
//		Long dno = 101L;
//		DiaryDTO diaryDTO = diaryService.get(dno);
//		log.info(diaryDTO);
//	}
//	
////	@Test
//	public void testList() {
//		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(2).size(10).build();
//		PageResponseDTO<DiaryDTO> response = diaryService.list(pageRequestDTO);
//		log.info(response);
//	}

}
