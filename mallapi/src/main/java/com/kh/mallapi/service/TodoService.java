package com.kh.mallapi.service;

import com.kh.mallapi.dto.PageRequestDTO;
import com.kh.mallapi.dto.PageResponseDTO;
import com.kh.mallapi.dto.TodoDTO;

public interface TodoService {
	//insert
	public Long register(TodoDTO todoDTO);
	
	//select
	public TodoDTO get(TodoDTO todoDTO);
	
	//update 
	public void modify(TodoDTO todoDTO);
	
	//delete 
	public void remove(TodoDTO todoDTO);
	
	//페이징 처리 및 리스트 요청 
	public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);
}
