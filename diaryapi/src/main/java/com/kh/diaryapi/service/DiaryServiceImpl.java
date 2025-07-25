package com.kh.diaryapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.diaryapi.dto.DiaryDTO;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class DiaryServiceImpl implements DiaryService {
	@Override
	public Long register(DiaryDTO diaryDTO) {
		log.info(" ");
		return null;
	}
}
