package com.proj.togedutch.repository;

import com.proj.togedutch.domain.LikeUsers;
import com.proj.togedutch.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LikeUsersRepository extends JpaRepository<LikeUsers, Integer> {
    LikeUsers findByPostIdxAndUploaderIdxAndKeeperIdx(int postIdx, int Uploader_userIdx, int userIdx);

    int deleteByKeeperIdxAndPostIdx(int userIdx, int postIdx);
}
