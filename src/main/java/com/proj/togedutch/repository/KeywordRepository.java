package com.proj.togedutch.repository;

import com.proj.togedutch.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {
    public Keyword findKeywordByKeywordIdx(int keywordIdx);
}