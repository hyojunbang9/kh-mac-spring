package com.kh.diaryapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kh.diaryapi.domain.Moment;

public interface MomentRepository extends JpaRepository<Moment, Long> {
	@EntityGraph(attributePaths = "imageList")
	@Query("select m from Moment m where m.mno = :mno")
	Optional<Moment> selectOne(@Param("mno") Long mno);

}