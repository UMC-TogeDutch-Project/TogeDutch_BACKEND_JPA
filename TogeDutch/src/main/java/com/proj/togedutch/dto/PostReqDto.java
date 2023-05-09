package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Post;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostReqDto {
    private String title;
    private String url;
    private int delivery_tips;
    private int minimum;
    private String order_time;
    private int num_of_recruits;
    private int recruited_num;
    private String status;
    private Double latitude;
    private Double longitude;
    private String category;

}
