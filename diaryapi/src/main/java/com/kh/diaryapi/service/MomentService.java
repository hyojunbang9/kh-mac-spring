package com.kh.diaryapi.service;

import org.springframework.transaction.annotation.Transactional;

import com.kh.diaryapi.dto.MomentDTO;
import com.kh.diaryapi.dto.PageRequestDTO;
import com.kh.diaryapi.dto.PageResponseDTO;

@Transactional
public interface MomentService {
	PageResponseDTO<MomentDTO> getList(PageRequestDTO pageRequestDTO);

	Long register(MomentDTO momentDTO);

	MomentDTO get(Long mno);

	public void modify(MomentDTO momentDTO);
	
	public void remove(Long mno);
}
