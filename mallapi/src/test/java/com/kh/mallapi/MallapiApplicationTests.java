
package com.kh.mallapi;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.kh.mallapi.domain.Todo;
import com.kh.mallapi.dto.PageRequestDTO;
import com.kh.mallapi.dto.PageResponseDTO;
import com.kh.mallapi.dto.TodoDTO;
import com.kh.mallapi.repository.TodoRepository;
import com.kh.mallapi.service.TodoService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class MallapiApplicationTests {
	@Autowired
	private TodoRepository todoRepository;
	@Autowired
	private TodoService todoService;

//	@Test
	void contextLoads() {
		// Todo Table에 insert 기능 = jpa.save(entity 기능), post
		for (int i = 0; i <= 100; i++) {
			Todo todo = Todo.builder().title("Title" + i).dueDate(LocalDate.of(2025, 7, 15)).writer("user00").build();
			todoRepository.save(todo);
		}
	}     

//	@Test
	// todo, get select * from todo where tno === ? === findById
	public void testRead() {
		// 존재하는 번호로 확인
		Long tno = 33L;
		Optional<Todo> result = todoRepository.findById(tno);
		Todo todo = result.orElseThrow();
		log.info(todo);
	}

//	@Test
	// 수정을 하기 위해 findBuId => Todo(정보가 이미 들어있음) => Todo setter 수정한다. => save(Entity)
	// save는 해당 tno가 있으면 update 없으면 insert
	public void testModify() {
		Long tno = 33L;
		Optional<Todo> result = todoRepository.findById(tno);
		Todo todo = result.orElseThrow();
		todo.changeTitle("Modified 33...");
		todo.changeComplete(true);
		todo.changeDueDate(LocalDate.of(2023, 10, 10));
		todoRepository.save(todo);
	}

//	@Test
	public void testDelete() {
		Long tno = 1L;
		todoRepository.deleteById(tno);
	}

//	@Test
	public void testPaging() {
		// 0번째 페이지요청(페이지 인덱스는 0부터 시작), 한 페이지에 10개의 데이터,
		// 정렬기준은 tno 필드를 기준으로 내림차순
		Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
		// Page<Todo>타입 반환되며, 전체 정보(총 개수, 현재 페이지 등)가 포함
		Page<Todo> result = todoRepository.findAll(pageable);
		// 전체 데이터 개수(전체 Todo 엔티티 수)를 로그로 출력
		log.info(result.getTotalElements());
		// 현재 페이지(0페이지)에 포함된 Todo 목록을 가져온다.
		result.getContent().stream().forEach(todo -> log.info(todo));
	}

	// TodoDTO 값을 서비스를 이용해 다형성 처리, 저장

//	@Test
	public void testRegister() {
		TodoDTO todoDTO = TodoDTO.builder().title("서비스 테스트").writer("tester").dueDate(LocalDate.of(2025, 07, 26))
				.build();
		Long tno = todoService.register(todoDTO);
		log.info("TNO: " + tno);
	}

//	@Test
	public void testGet() {
		Long tno = 101L;
		TodoDTO todoDTO = TodoDTO.builder().tno(tno).build();
		TodoDTO _todoDTO = todoService.get(todoDTO);
		log.info(_todoDTO);
	}

	// 넘버 리스트
//	@Test
	public void testNumber() {
		List<Integer> listInteger = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
		log.info(listInteger.toString());
	}

	// 페이징 리스트
//	@Test
	public void testList() {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(2).size(10).build();
		PageResponseDTO<TodoDTO> response = todoService.list(pageRequestDTO);
		log.info(response);
	}

}
