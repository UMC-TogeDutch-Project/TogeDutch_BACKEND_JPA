package com.proj.togedutch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeywordReqDto {
    private int keywordIdx;
    private String word1;
    private String word2;
    private String word3;
    private String word4;
    private String word5;
    private String word6;

    public KeywordReqDto(String word1, String word2, String word3, String word4, String word5, String word6) {
        this.word1 = word1;
        this.word2 = word2;
        this.word3 = word3;
        this.word4 = word4;
        this.word5 = word5;
        this.word6 = word6;
    }
}
