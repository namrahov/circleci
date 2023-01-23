package com.example.demo.util;

import com.example.demo.logger.DPLogger;
import com.example.demo.model.Statistics;
import com.example.demo.model.Word;
import com.google.common.base.Joiner;
import com.google.common.io.Resources;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class VowelUtil {
    private static final DPLogger log = DPLogger.getLogger(VowelUtil.class);

    public String[] readFileAndTakeWords() {
        String text = "";
        URL url = Resources.getResource("input.txt");
        try {
            text = Resources.toString(url, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.info("ActionLog.readFileAndTakeWords.can't read text");
        }

        //remove all punctuations, convert to lowercase and split by space
        return text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
    }

    public List<Word> findWordsIndicators(String[] words) {
        List<Word> wordsIndicatorsList = new ArrayList<>();
        String vowels = "aeiou";


        for (int i = 0; i < words.length; i++) {

            Set<Character> vowelsOfWord = new TreeSet<>();
            int countOfVowelsOfWord = 0;


            for (int k = 0; k < words[i].length(); k++) {
                char letterOfString = words[i].charAt(k);

                Predicate<Character> p = (element) -> vowels.indexOf(element) != -1;

                if (p.test(letterOfString)) {
                    vowelsOfWord.add(letterOfString);
                    countOfVowelsOfWord++;
                }
            }

            wordsIndicatorsList.add(new Word(setToString(vowelsOfWord), countOfVowelsOfWord, words[i].length()));
        }

        return wordsIndicatorsList;
    }

    /**
     * select vowelsOfWord, sizeOfWord, count(*), sum(countOfVowelsOfWord)
     * from Word
     * group by vowelsOfWord, sizeOfWord
     **/
    public Map<List<Object>, Statistics> groupByVowelsOfWordAndSizeOfWord(List<Word> wordsIndicatorsList) {
        Function<Word, List<Object>> compositeKey = word ->
                Arrays.asList(word.getVowelsOfWord(), word.getSizeOfWord());

        return wordsIndicatorsList.stream()
                .collect(Collectors.groupingBy(compositeKey,
                        Collector.of(Statistics::new, Statistics::add, Statistics::merge)));
    }

    public void writeMapToFile(Map<List<Object>, Statistics> map) {
        final String outputFilePath = "output.txt";

        File file = new File(outputFilePath);
        try (FileWriter fw = new FileWriter(file); BufferedWriter bf = new BufferedWriter(fw)) {

            for (Map.Entry<List<Object>, Statistics> entry : map.entrySet()) {
                bf.write(entry.getKey() + " -> " + ((double) entry.getValue().getSum()) / entry.getValue().getCount());

                bf.newLine();
            }

            bf.flush();
        } catch (IOException e) {
            log.info("ActionLog.writeMapToFile.can't create a text file");
        }
    }

    private String setToString(Set<Character> vowelsOfWord) {

        String str = Joiner.on(",").join(vowelsOfWord);

        return "{" + str + "}";
    }
}
