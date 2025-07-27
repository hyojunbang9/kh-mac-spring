package com.kh.diaryapi.service;

import com.kh.diaryapi.dto.DiaryDTO;
import com.kh.diaryapi.dto.PageRequestDTO;
import com.kh.diaryapi.dto.PageResponseDTO;

public interface DiaryService {
	Long register(DiaryDTO dirayDTO);

	DiaryDTO get(Long dno);

	public void modify(DiaryDTO dariyDTO);

	public void remove(Long dno);
	
	PageResponseDTO<DiaryDTO> list(PageRequestDTO pageRequestDTO);
}
