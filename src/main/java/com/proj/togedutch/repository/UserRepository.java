package com.proj.togedutch.repository;


import com.proj.togedutch.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select u.user_id, u.Keyword_keyword_id, u.name, u.role, u.email, u.password, u.phone, u.image, u.status, u.created_at, u.updated_at, u.latitude, u.longitude " +
            "from User u " +
            "where u.user_id = :userIdx", nativeQuery = true)
    UserInfo findByUserIdx(@Param("userIdx") int userIdx);
    interface UserInfo {
        int getUser_id();
        Integer getKeyword_keyword_id();
        String getName();
        String getRole();
        String getEmail();
        String getPassword();
        String getPhone();
        String getImage();
        String getStatus();
        Timestamp getCreated_at();
        Timestamp getUpdated_at();
        Double getLatitude();
        Double getLongitude();
    }
}
