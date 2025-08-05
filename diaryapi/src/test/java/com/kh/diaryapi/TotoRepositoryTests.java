package com.kh.diaryapi;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.diaryapi.domain.Todo;
import com.kh.diaryapi.repository.TodoRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class TotoRepositoryTests {
	@Autowired
	private TodoRepository todoRepository;

//	@Test
	public void testInsert() {
		for (int i = 1; i <= 10; i++) {
			Todo todo = Todo.builder().ttitle("제목..." + i).twriter("user" + String.format("%02d", i % 10))
					.tcontent("내용입니다..." + i).dueDate(LocalDate.of(2025, 7, 24))
					.done(i % 2 == 0) // 짝수 번호만 완료 처리
					.build();

			todoRepository.save(todo);
		}
	}
}
