package com.example.demo.service;

import com.example.demo.util.VowelUtil;
import com.example.demo.logger.DPLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VowelService {
    private static final DPLogger log = DPLogger.getLogger(VowelService.class);
    private final VowelUtil vowelUtil;

    public void calculateAverageNumberOfVowels() {
        log.info("ActionLog.calculateAverageNumberOfVowels.start");

        var words = vowelUtil

                .readFileAndTakeWords();

        var wordsIndicatorsList = vowelUtil.findWordsIndicators(words);












        var resultMap = vowelUtil.groupByVowelsOfWordAndSizeOfWord(wordsIndicatorsList);

        vowelUtil.writeMapToFile(resultMap);

        log.info("ActionLog.calculateAverageNumberOfVowels.end");
    }
}
