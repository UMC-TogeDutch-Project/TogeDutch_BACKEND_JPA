package com.proj.togedutch.repository;

import com.proj.togedutch.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(nativeQuery = true, value = "SET time_zone = 'Asia/Seoul'")
    void timezoneSetting();

    // 최신순 조회
    List<Post> findByStatusNotInOrderByCreatedAtDesc(List<String> status);

    // 주문 임박 조회
    @Query(nativeQuery = true, value = "SELECT * FROM Post as p WHERE p.order_time between now() and DATE_ADD(NOW(), INTERVAL 10 MINUTE) and p.status NOT IN (:status) order by p.order_time asc")
    List<Post> findByOrderImminent(@Param("status") List<String> status);

    // 공고 특정 조회 (Post API 5번)
    Post findByPostIdxAndUserIdx(int postIdx, int userIdx);
}
