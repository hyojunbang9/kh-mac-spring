package com.kh.mallapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kh.mallapi.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	// 조인 후 데이터 가져옴
	@EntityGraph(attributePaths = "imageList")
	@Query("select p from Product p where p.pno = :pno")
	Optional<Product> selectOne(@Param("pno") Long pno);

	// @@Modifying는 @Query가 SELECT가 아닌 DML(UPDATE, DELETE 등일 때 필요하다)
	@Modifying
	@Query("update Product p set p.delFlag = :flag where p.pno = :pno")
	void updateToDelete(@Param("pno") Long pno, @Param("flag") boolean flag);

	// 일반 SELECT문은 @Modifying xxxx
	@Query("select p, pi from Product p left join p.imageList pi where pi.ord = 0 and p.delFlag = false ")
	Page<Object[]> selectList(Pageable pageable);

}