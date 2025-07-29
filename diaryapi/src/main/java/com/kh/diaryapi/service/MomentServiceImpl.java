package com.kh.diaryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.diaryapi.domain.Moment;
import com.kh.diaryapi.domain.MomentImage;
import com.kh.diaryapi.dto.MomentDTO;
import com.kh.diaryapi.dto.PageRequestDTO;
import com.kh.diaryapi.dto.PageResponseDTO;
import com.kh.diaryapi.repository.MomentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MomentServiceImpl implements MomentService {

	private final MomentRepository momentRepository;

	public PageResponseDTO<MomentDTO> getList(PageRequestDTO pageRequestDTO) {

		log.info("getList called");

		// 페이징 정보 생성
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
				Sort.by("mno").descending());

		// 엔티티 페이징 조회
		Page<Moment> result = momentRepository.findAll(pageable);

		// 엔티티를 DTO로 변환
		List<MomentDTO> dtoList = result.get().map(moment -> {
			MomentDTO dto = MomentDTO.builder().mno(moment.getMno()).mtitle(moment.getMtitle())
					.mcontent(moment.getMcontent()).mlocation(moment.getMlocation()).mdate(moment.getMdate())
					.uploadFileNames(
							moment.getImageList().stream().map(MomentImage::getFileName).collect(Collectors.toList()))
					.build();

			return dto;
		}).collect(Collectors.toList());

		// 전체 개수
		long totalCount = result.getTotalElements();

		// 응답 DTO 생성
		return PageResponseDTO.<MomentDTO>withAll().dtoList(dtoList).totalCount(totalCount)
				.pageRequestDTO(pageRequestDTO).build();
	}

	@Override
	public Long register(MomentDTO momentDTO) {
		Moment moment = dtoToEntity(momentDTO); // DTO → Entity 변환
		Moment result = momentRepository.save(moment); // 저장
		return result.getMno(); // 생성된 MOMENT 번호 반환
	}

	private Moment dtoToEntity(MomentDTO momentDTO) {
		Moment moment = Moment.builder().mno(momentDTO.getMno()).mtitle(momentDTO.getMtitle())
				.mcontent(momentDTO.getMcontent()).mlocation(momentDTO.getMlocation()).mdate(momentDTO.getMdate())
				.build();

		List<String> uploadFileNames = momentDTO.getUploadFileNames();
		if (uploadFileNames == null) {
			return moment;
		}

		uploadFileNames.forEach(fileName -> {
			moment.addImageString(fileName); // 내부에서 순서 자동 처리
		});

		return moment;
	}

	@Override
	public MomentDTO get(Long mno) {
		Optional<Moment> result = momentRepository.selectOne(mno);
		Moment moment = result.orElseThrow(); // 존재하지 않으면 예외 발생
		return entityToDTO(moment);
	}

	private MomentDTO entityToDTO(Moment moment) {
		MomentDTO momentDTO = MomentDTO.builder().mno(moment.getMno()).mtitle(moment.getMtitle())
				.mcontent(moment.getMcontent()).mlocation(moment.getMlocation()).mdate(moment.getMdate()).build();

		List<MomentImage> imageList = moment.getImageList();

		if (imageList != null && !imageList.isEmpty()) {
			List<String> fileNameList = imageList.stream().map(MomentImage::getFileName).toList();
			momentDTO.setUploadFileNames(fileNameList);
		}

		return momentDTO;
	}

}