package com.kh.mallapi.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private Long pno;
	private String pname;
	private int price;
	private String pdesc;

	@Builder.Default
	//MultopartFile 클라이언트 자료가 업로드 된 정보를 저장하고 있는 클래스(파일명, 파일 사이즈,파일 타입, 파일 정보)
	private List<MultipartFile> files = new ArrayList<>();
	
	@Builder.Default
	//실제 서버 API에 저장된 이름 (UUID_실제 파일 명.파일 타입)
	private List<String> uploadFileNames = new ArrayList<>();
	
}