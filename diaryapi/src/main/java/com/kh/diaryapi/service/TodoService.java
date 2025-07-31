package com.kh.diaryapi.service;

import com.kh.diaryapi.dto.PageRequestDTO;
import com.kh.diaryapi.dto.PageResponseDTO;
import com.kh.diaryapi.dto.TodoDTO;

public interface TodoService {
	Long register(TodoDTO todoDTO);

	TodoDTO get(Long tno);

	public void modify(TodoDTO todoDTO);

	public void remove(Long tno);
	
	PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);
}
