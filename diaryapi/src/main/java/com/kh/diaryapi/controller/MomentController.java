package com.kh.diaryapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.diaryapi.dto.MomentDTO;
import com.kh.diaryapi.dto.PageRequestDTO;
import com.kh.diaryapi.dto.PageResponseDTO;
import com.kh.diaryapi.service.MomentService;
import com.kh.diaryapi.util.MyFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/moment")
public class MomentController {
	private final MomentService momentService;
	private final MyFileUtil fileUtil;

	@PostMapping("/")
	public Map<String, Long> register(MomentDTO momentDTO) {
		log.info("rgister: " + momentDTO);
		List<MultipartFile> files = momentDTO.getFiles();
		List<String> uploadFileNames = fileUtil.saveFiles(files);
		momentDTO.setUploadFileNames(uploadFileNames);
		log.info(uploadFileNames);

		Long mno = momentService.register(momentDTO);

		return Map.of("RESULT", mno);
	}

	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {
		return fileUtil.getFile(fileName);
	}

	@GetMapping("/list")
	public PageResponseDTO<MomentDTO> list(PageRequestDTO pageRequestDTO) {
		log.info("list............." + pageRequestDTO);
		return momentService.getList(pageRequestDTO);
	}

	@GetMapping("/{mno}")
	public MomentDTO read(@PathVariable(name = "mno") Long mno) {
		return momentService.get(mno);
	}
}