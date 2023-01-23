package com.example.demo.model;

import lombok.Data;

@Data
public class Statistics {
    private int count = 0;
    private int sum = 0;

    public void add(Word word) {
        count++;
        sum += word.getCountOfVowelsOfWord();
    }

    public Statistics merge(Statistics another) {
        count += another.count;
        sum += another.sum;
        return this;
    }
}
