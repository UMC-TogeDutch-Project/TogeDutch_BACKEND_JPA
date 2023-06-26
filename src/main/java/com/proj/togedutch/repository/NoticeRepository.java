package com.proj.togedutch.repository;

import com.proj.togedutch.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Integer> {
    List<Notice> findAllByOrderByCreatedAtDesc();
}
