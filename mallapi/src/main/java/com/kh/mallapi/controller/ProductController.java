package com.kh.mallapi.controller;

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

import com.kh.mallapi.dto.ProductDTO;
import com.kh.mallapi.util.CustomFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/products")
public class ProductController {
	//생성자 의존성 자동 주입
	private final CustomFileUtil fileUtil;

	@PostMapping("/")
	public Map<String, String> register(ProductDTO productDTO) {
		log.info("rgister: " + productDTO);
		List<MultipartFile> files = productDTO.getFiles();
		//업로드 된 파일을 UUID_실제 파일.타입으로 변환해서 서버 저장소에 저장 후에
		//List<String> UUID_실제 파일.타입으로 리턴한다.
		List<String> uploadFileNames = fileUtil.saveFiles(files);
		productDTO.setUploadFileNames(uploadFileNames);
		log.info(uploadFileNames);
		return Map.of("RESULT", "SUCCESS");
	}
	
	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
	return fileUtil.getFile(fileName);
	}
	
}