package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Application;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.domain.Review;
import com.proj.togedutch.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;


@Data
@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {

    private int reviewIdx;
    private int emotionStatus;
    private String content;
    private Date createdDate;


    private int applicationId;

    private int postId;
    //private int userId;

    private int userId;

    public Review toEntity(User user, Application application, Post post){
        return Review.builder()
                .reviewIdx(reviewIdx)
                .emotionStatus(emotionStatus)
                .content(content)
                .createdDate(createdDate)
                .application(application)
                .user(user)
                .post(post)
                .build();
    }

}
