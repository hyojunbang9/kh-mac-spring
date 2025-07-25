package com.kh.diaryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kh.diaryapi.domain.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long>{
}