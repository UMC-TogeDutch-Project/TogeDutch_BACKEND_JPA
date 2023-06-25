package com.proj.togedutch.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ReviewResDto {
    private int post_id;
    private int avg;
}
