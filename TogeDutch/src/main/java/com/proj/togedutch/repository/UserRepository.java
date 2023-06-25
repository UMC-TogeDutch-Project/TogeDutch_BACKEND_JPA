package com.proj.togedutch.repository;

import com.proj.togedutch.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findUserByEmail(String email);
    public User findUserByUserIdx(int userIdx);
    public List<User> findAllUser();

}
