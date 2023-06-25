package com.proj.togedutch.repository;

import com.proj.togedutch.domain.Review;
import com.proj.togedutch.dto.ReviewResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findReviewByPost(int post_Id);
    //List<Review> findByUserId(int user_Id);
    @Query(value = "select Application_Post_post_id , avg(emotion_status) from Review where Application_Post_post_id = ? ", nativeQuery = true)
    ReviewResDto findAvgByPostId(int post_Id);

}
