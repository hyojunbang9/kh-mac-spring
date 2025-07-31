package com.kh.diaryapi.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PutMapping("/{mno}")
	public Map<String, String> modify(@PathVariable(name = "mno") Long mno, MomentDTO momentDTO) {
		momentDTO.setMno(mno);

		// 기존 데이터 조회
		MomentDTO oldMomentDTO = momentService.get(mno);

		// 기존 파일 목록 (DB에 있던 파일들)
		List<String> oldFileNames = oldMomentDTO.getUploadFileNames();

		// 새로 업로드된 파일들
		List<MultipartFile> files = momentDTO.getFiles();

		// 새로 업로드되어 만들어진 파일 이름
		List<String> currentUploadFileNames = fileUtil.saveFiles(files);

		// 유지된 파일들
		List<String> uploadedFileNames = momentDTO.getUploadFileNames();

		// 유지 + 새로 업로드한 파일들을 모두 합쳐 최종적으로 저장해야 할 파일 리스트 구성
		if (currentUploadFileNames != null && !currentUploadFileNames.isEmpty()) {
			uploadedFileNames.addAll(currentUploadFileNames);
		}

		// 수정 처리
		momentService.modify(momentDTO);

		// 삭제 대상 파일 처리
		if (oldFileNames != null && !oldFileNames.isEmpty()) {
			// 예전 파일 중에서 더 이상 유지되지 않는 파일들만 필터링
			List<String> removeFiles = oldFileNames.stream().filter(fileName -> !uploadedFileNames.contains(fileName))
					.collect(Collectors.toList());

			fileUtil.deleteFiles(removeFiles);
		}

		return Map.of("RESULT", "SUCCESS");
	}

	@DeleteMapping("/{mno}")
	public Map<String, String> remove(@PathVariable("mno") Long mno) {
		// 삭제해야 할 파일들 알아내기
		List<String> oldFileNames = momentService.get(mno).getUploadFileNames();
		momentService.remove(mno);
		fileUtil.deleteFiles(oldFileNames);
		return Map.of("RESULT", "SUCCESS");
	}

}