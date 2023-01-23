package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Word {
    private String vowelsOfWord;
    private int countOfVowelsOfWord;
    private int sizeOfWord;
}

