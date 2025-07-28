package com.kh.diaryapi.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.diaryapi.dto.DiaryDTO;
import com.kh.diaryapi.dto.PageRequestDTO;
import com.kh.diaryapi.dto.PageResponseDTO;
import com.kh.diaryapi.service.DiaryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/diary")
public class DiaryController {
	private final DiaryService service;

	@GetMapping("/{dno}")
	public DiaryDTO get(@PathVariable(name = "dno") Long dno) {
		return service.get(dno);
	}

	@GetMapping("/list")
	public PageResponseDTO<DiaryDTO> list(PageRequestDTO pageRequestDTO) {
		log.info(pageRequestDTO);
		return service.list(pageRequestDTO);
	}

	@PostMapping("/")
	public Map<String, Long> register(@RequestBody DiaryDTO diaryDTO) {
		log.info("DiaryDTO: " + diaryDTO);
		Long dno = service.register(diaryDTO);
		log.info(diaryDTO);
		return Map.of("DNO", dno);
	}

	@PutMapping("/{dno}")
	public Map<String, String> modify(@PathVariable(name = "dno") Long dno, @RequestBody DiaryDTO diaryDTO) {
		diaryDTO.setDno(dno);
		log.info("Modify: " + diaryDTO);
		service.modify(diaryDTO);
		return Map.of("RESULT", "SUCCESS");
	}

	@DeleteMapping("/{dno}")
	public Map<String, String> remove(@PathVariable(name = "dno") Long dno) {
		log.info("Remove: " + dno);
		service.remove(dno);
		return Map.of("RESULT", "SUCCESS");
	}
}
