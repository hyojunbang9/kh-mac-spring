package com.kh.diaryapi;

import java.time.LocalDate;

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

//	@Test
	public void test1() {
		log.info(" ");
		log.info(diaryRepository);
	}

//	@Test
	public void testInsert() {
		for (int i = 0; i <= 100; i++) {
			Diary diary = Diary.builder().title("Title..." + i).dueDate(LocalDate.of(2025, 7, 24)).writer("user00")
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
		diary.changeTitle("Modified 33...");
		diary.changeComplete(true);
		diary.changeDueDate(LocalDate.of(2025, 07, 25));
		diaryRepository.save(diary);
	}

//	@Test
	public void testDelete() {
		Long dno = 100L;
		diaryRepository.deleteById(dno);
	}

	@Test
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
}