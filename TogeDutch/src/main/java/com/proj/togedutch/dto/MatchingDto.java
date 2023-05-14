package com.proj.togedutch.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MatchingDto {
    private int MatchingId;
    private Integer UserFirstId;
    private Integer UserSecondId;
    private Integer UserThirdId;
    private Integer count;
    private Integer postIdx;
}
