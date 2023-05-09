package com.proj.togedutch.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MatchingDto {
    int MatchingId;
    int UserFirstId;
    int UserSecondId;
    int UserThirdId;
    int count;
    int postIdx;
}
