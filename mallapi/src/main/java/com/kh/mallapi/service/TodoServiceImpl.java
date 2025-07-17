package com.kh.mallapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.mallapi.domain.Todo;
import com.kh.mallapi.dto.PageRequestDTO;
import com.kh.mallapi.dto.PageResponseDTO;
import com.kh.mallapi.dto.TodoDTO;
import com.kh.mallapi.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
	// 생성자 의존성 주입
	private final TodoRepository todoRepository;
	private final ModelMapper modelMapper;

	@Override
	public Long register(TodoDTO todoDTO) {
		Todo todo = modelMapper.map(todoDTO, Todo.class);
		Todo saveTodo = todoRepository.save(todo);

		return saveTodo.getTno();
	}

	@Override
	public TodoDTO get(TodoDTO todoDTO) {
		java.util.Optional<Todo> result = todoRepository.findById(todoDTO.getTno());
		Todo todo = result.orElseThrow();
		TodoDTO dto = modelMapper.map(todo, TodoDTO.class);
		return dto;
	}

	@Override
	public void modify(TodoDTO todoDTO) {
		Optional<Todo> result = todoRepository.findById(todoDTO.getTno());
		Todo todo = result.orElseThrow();
		todo.changeTitle(todoDTO.getTitle());
		todo.changeDueDate(todoDTO.getDueDate());
		todo.changeComplete(todoDTO.isComplete());
		todoRepository.save(todo);
	}

	@Override
	public void remove(TodoDTO todoDTO) {
		todoRepository.deleteById(todoDTO.getTno());
	}

	@Override
	public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
		// 1 페이지 정보를 줘야한다 (PageRequestDTO로 받음)
		// 2 목록 리스트를 매개변수로 줘야한다.
		// 3 객체의 전체 갯수를 줘야한다.
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, // 1 페이지가 0 이므로 주의
				pageRequestDTO.getSize(), Sort.by("tno").descending());

		Page<Todo> result = todoRepository.findAll(pageable);

		// 1페이지 10
		List<TodoDTO> dtoList = result.getContent().stream().map(todo -> modelMapper.map(todo, TodoDTO.class))
				.collect(Collectors.toList());
		// 전체 갯수
		long totalCount = result.getTotalElements();

		// 생성자 (List<TodoDTO>, PageRequestDTO, totalCount)를 주면 된다.
		PageResponseDTO<TodoDTO> responseDTO = PageResponseDTO.<TodoDTO>withAll().dtoList(dtoList)
				.pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();

		return responseDTO;
	}
}
