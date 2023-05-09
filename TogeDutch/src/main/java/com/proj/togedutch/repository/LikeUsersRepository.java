package com.proj.togedutch.repository;

import com.proj.togedutch.domain.LikeUsers;
import com.proj.togedutch.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeUsersRepository extends JpaRepository<LikeUsers, Integer>, JpaSpecificationExecutor<LikeUsers> {
    LikeUsers findByIdAndUploaderIdxAndKeeperIdx(int postIdx, int Uploader_userIdx, int userIdx);

    int deleteByKeeperIdxAndPostIdx(int userIdx, int postIdx);

    @Query(value = "select * from Post p where p.post_id IN (select l.Post_post_id from LikeUsers l where l.User_user_id = ? and p.status != \"공고사용불가\" )", nativeQuery = true)
    List<Post> findLikePostByUserIdx(int userIdx);
}
