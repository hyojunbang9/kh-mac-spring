package com.kh.diaryapi;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.kh.diaryapi.domain.Diary;
import com.kh.diaryapi.repository.DiaryRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class DiaryRepositoryTests {
	@Autowired
	private DiaryRepository diaryRepository;
//	@Autowired
//	private DiaryService diaryService;

//	@Test
	public void testInsert() {
		for (int i = 1; i <= 100; i++) {
			Diary diary = Diary.builder().dtitle("제목..." + i).dwriter("user" + String.format("%02d", i % 10))
					.dcontent("내용입니다..." + i).dweather(i % 2 == 0 ? "맑음" : "흐림").ddate(LocalDate.of(2025, 7, 24))
					.build();

			diaryRepository.save(diary);
		}
	}

//	@Test
	public void testRead() {
		// 존재하는 번호로 확인
		Long dno = 33L;
		java.util.Optional<Diary> result = diaryRepository.findById(dno);
		Diary diary = result.orElseThrow();
		log.info(diary);
	}

//	@Test
	public void testModify() {
		Long dno = 33L;
		java.util.Optional<Diary> result = diaryRepository.findById(dno);
		Diary diary = result.orElseThrow();
		diary.changeDtitle("Modified 33...");
		diary.changeDcontent("Modified content");
		diary.changeDweather("Modified weather");
		diary.changeDdate(LocalDate.of(2025, 07, 25));
		diaryRepository.save(diary);
	}

//	@Test
	public void testDelete() {
		Long dno = 100L;
		diaryRepository.deleteById(dno);
	}

//	@Test
	public void testPaging() {
		// 0번째 페이지요청(페이지 인덱스는 0부터 시작), 한 페이지에 10개의 데이터,
		// 정렬기준은 dno 필드를 기준으로 내림차순
		Pageable pageable = PageRequest.of(0, 10, Sort.by("dno").descending());
		// Page<Diary>타입 반환되며, 전체 정보(총 개수, 현재 페이지 등)가 포함
		Page<Diary> result = diaryRepository.findAll(pageable);
		// 전체 데이터 개수(전체 Diary 엔티티 수)를 로그로 출력
		log.info(result.getTotalElements());
		// 현재 페이지(0페이지)에 포함된 Diary 목록을 가져온다.
		result.getContent().stream().forEach(diary -> log.info(diary));
	}

////	@Test
//	public void testRegister() {
//		DiaryDTO diaryDTO = DiaryDTO.builder().title("서비스 테스트").writer("tester").dueDate(LocalDate.of(2025, 07, 26))
//				.build();
//		Long tno = todoService.register(diaryDTO);
//		log.info("TNO: " + tno);
//	}
//
////	@Test
//	public void testGet() {
//		Long tno = 101L;
//		DiaryDTO diaryDTO = diaryDTO.builder().tno(tno).build();
//		DiaryDTO diaryDTO = todoService.get(diaryDTO);
//		log.info(_diaryDTO);
//	}
//
//	// 넘버 리스트
////	@Test
//	public void testNumber() {
//		List<Integer> listInteger = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
//		log.info(listInteger.toString());
//	}
//
//	// 페이징 리스트
////	@Test
//	public void testList() {
//		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(2).size(10).build();
//		PageResponseDTO<DiaryDTO> response = todoService.list(pageRequestDTO);
//		log.info(response);
//	}
}