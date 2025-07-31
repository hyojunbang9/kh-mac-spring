package com.kh.diaryapi;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.diaryapi.domain.Moment;
import com.kh.diaryapi.repository.MomentRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MomentRepositoryTests {

	@Autowired
	MomentRepository momentRepository;

//	@Test
	public void testInsert() {
		for (int i = 0; i < 10; i++) {
			Moment moment = Moment.builder().mtitle("제목 " + i).mcontent("기억에 남는 순간 내용 " + i)
					.mlocation("서울 강남구 " + i + "번지").mdate(LocalDate.of(2025, 7, 27)).build();
			momentRepository.save(moment);
			// 2 개의 이미지 파일 추가
			moment.addImageString(UUID.randomUUID().toString() + "-" + "IMAGE1.jpg");
			moment.addImageString(UUID.randomUUID().toString() + "-" + "IMAGE2.jpg");
			momentRepository.save(moment);
			log.info(" ");
		}
	}

//	@Test
//	@Transactional
	public void testRead() {
		Long mno = 1L;
		Optional<Moment> result = momentRepository.findById(mno);
		Moment moment = result.orElseThrow();
		log.info(moment); // ----------------------- 1
		log.info(moment.getImageList()); // -------- 2
	}

//	@Test
	public void testRead2() {
		Long mno = 1L;
		Optional<Moment> result = momentRepository.selectOne(mno);
		Moment moment = result.orElseThrow();
		log.info(moment);
		log.info(moment.getImageList());
	}

//	@Test
	public void testUpdate() {
		Long mno = 10L;
		Moment moment = momentRepository.selectOne(mno).get();
		moment.changeMtitle("10번 모먼트");
		moment.changeMcontent("10번 모먼트 설명입니다.");
		moment.changeMdate(LocalDate.now()); // 현재 날짜로 변경
		moment.changeMlocation("경기");
		// 첨부파일 수정
		moment.clearList();
		moment.addImageString(UUID.randomUUID().toString() + "-" + "NEWIMAGE1.jpg");
		moment.addImageString(UUID.randomUUID().toString() + "-" + "NEWIMAGE2.jpg");
		moment.addImageString(UUID.randomUUID().toString() + "-" + "NEWIMAGE3.jpg");
		momentRepository.save(moment);
	}

}
