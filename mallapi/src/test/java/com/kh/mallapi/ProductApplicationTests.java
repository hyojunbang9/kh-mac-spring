
package com.kh.mallapi;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.kh.mallapi.domain.Product;
import com.kh.mallapi.dto.PageRequestDTO;
import com.kh.mallapi.dto.PageResponseDTO;
import com.kh.mallapi.dto.ProductDTO;
import com.kh.mallapi.repository.ProductRepository;
import com.kh.mallapi.service.ProductService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductApplicationTests {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductService productService;

	// 상품 정보 삽입
//	@Test
	public void productInsert() {
		for (int i = 0; i < 10; i++) {
			Product product = Product.builder().pname("상품" + i).price(100 * i).pdesc("상품 설명입니다" + i).build();
			product.addImageString(UUID.randomUUID().toString() + "_" + "image1.jpg");
			product.addImageString(UUID.randomUUID().toString() + "_" + "image2.jpg");
			productRepository.save(product);
		}
	}

	// 상품 정보 SELECT(Lazy 방식)
//	@Test
//	@Transactional
	public void testRead() {
		Long pno = 1L;
		Optional<Product> result = productRepository.findById(pno);
		Product product = result.orElseThrow();
		log.info(product); // ----------------------- 1
		log.info(product.getImageList()); // -------- 2
	}

	// 상품 정보 SELECT( 방식)
//	@Test
	public void testRead2() {
		Long pno = 1L;
		Optional<Product> result = productRepository.selectOne(pno);
		Product product = result.orElseThrow();
		log.info(product); // ----------------------- 1
		log.info(product.getImageList()); // -------- 2
	}

	// 상품 정보 삭제
//	@Test
//	@Commit
//	@Transactional
	public void testDelete() {
		Long pno = 2L;
		productRepository.updateToDelete(pno, true);
	}

	// 상품 정보 수정
//	@Test
	public void testUpdate() {
		Long pno = 10L;
		Product product = productRepository.selectOne(pno).get();
		product.changeName("10번 상품*");
		product.changeDesc("10번 상품 설명입니다.*");
		product.changePrice(11111);
		// 첨부파일 수정
		product.clearList();

		product.addImageString(UUID.randomUUID().toString() + "-" + "NEWIMAGE1.jpg");
		product.addImageString(UUID.randomUUID().toString() + "-" + "NEWIMAGE2.jpg");
		product.addImageString(UUID.randomUUID().toString() + "-" + "NEWIMAGE3.jpg");

		productRepository.save(product);
	}

	// 상품 정보 리스트 페이징 기법
	// 조인을 통해 Object[0] product와 Object[1] productImage
	// result 속에는 page정보가 다 들어있다
//	@Test
	public void testList() {
		// org.springframework.data.domain 패키지
		Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());
		Page<Object[]> result = productRepository.selectList(pageable);
		// java.util
		result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
	}

//	@Test
	public void testList2() {
		// 1 page, 10 size
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
		PageResponseDTO<ProductDTO> result = productService.getList(pageRequestDTO);
		result.getDtoList().forEach(dto -> log.info(dto));
	}

}