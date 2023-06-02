package com.proj.togedutch.repository;

import com.proj.togedutch.domain.ChatLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLocationRepository extends JpaRepository<ChatLocation, Integer> {
}
