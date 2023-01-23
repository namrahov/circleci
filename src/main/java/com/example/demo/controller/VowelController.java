package com.example.demo.controller;

import com.example.demo.service.VowelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/vowel")
@RequiredArgsConstructor
public class VowelController {

    private final VowelService vowelService;

    @GetMapping("/average")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void calculateAverageNumberOfVowels() {
        vowelService.calculateAverageNumberOfVowels();
    }
}