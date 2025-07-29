package com.kh.diaryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kh.diaryapi.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}