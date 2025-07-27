package com.kh.diaryapi.service;

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

import com.kh.diaryapi.domain.Diary;
import com.kh.diaryapi.dto.DiaryDTO;
import com.kh.diaryapi.dto.PageRequestDTO;
import com.kh.diaryapi.dto.PageResponseDTO;
import com.kh.diaryapi.repository.DiaryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2

@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
	// 자동주입 대상은 final로
	private final ModelMapper modelMapper;
	private final DiaryRepository diaryRepository;

	@Override
	public Long register(DiaryDTO diaryDTO) {
		log.info(". ........................");
		Diary diary = modelMapper.map(diaryDTO, Diary.class);
		Diary savedDiary = diaryRepository.save(diary);

		return savedDiary.getDno();
	}

	@Override
	public DiaryDTO get(Long dno) {
		java.util.Optional<Diary> result = diaryRepository.findById(dno);
		Diary diary = result.orElseThrow();
		DiaryDTO dto = modelMapper.map(diary, DiaryDTO.class);

		return dto;
	}

	@Override
	public void modify(DiaryDTO diaryDTO) {
		Optional<Diary> result = diaryRepository.findById(diaryDTO.getDno());
		Diary diary = result.orElseThrow();
		diary.changeTitle(diaryDTO.getTitle());
		diary.changeDueDate(diaryDTO.getDueDate());
		diary.changeComplete(diaryDTO.isComplete());
		diaryRepository.save(diary);
	}

	@Override
	public void remove(Long dno) {
		diaryRepository.deleteById(dno);
	}

	@Override
	public PageResponseDTO<DiaryDTO> list(PageRequestDTO pageRequestDTO) {
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, // 1 페이지가 0 이므로 주의
				pageRequestDTO.getSize(), Sort.by("dno").descending());
		Page<Diary> result = diaryRepository.findAll(pageable);
		List<DiaryDTO> dtoList = result.getContent().stream().map(diary -> modelMapper.map(diary, DiaryDTO.class))
				.collect(Collectors.toList());
		long totalCount = result.getTotalElements();
		PageResponseDTO<DiaryDTO> responseDTO = PageResponseDTO.<DiaryDTO>withAll().dtoList(dtoList)
				.pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();
		return responseDTO;
	}
}