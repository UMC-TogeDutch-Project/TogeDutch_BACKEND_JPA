package com.proj.togedutch.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="Keyword")
@NoArgsConstructor
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Keyword_id")
    private int keywordIdx;

    private String word1;

    private String word2;

    private String word3;

    private String word4;

    private String word5;

    private String word6;

    public Keyword(String word1, String word2, String word3, String word4, String word5, String word6) {
        this.word1 = word1;
        this.word2 = word2;
        this.word3 = word3;
        this.word4 = word4;
        this.word5 = word5;
        this.word6 = word6;
    }
}