package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WordFrequencySorter {

    public static void main(String[] args) {
        String filename = "words.txt";
        Map<String, Integer> wordCountMap = countWordFrequencies(filename);
        printSortedWordCounts(wordCountMap);
    }

    private static Map<String, Integer> countWordFrequencies(String filename) {
        Map<String, Integer> wordCountMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\s+");
                for (String word : words) {
                    word = word.replaceAll("[^a-z]", "");
                    if (!word.isEmpty()) {
                        wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return wordCountMap;
    }

    private static void printSortedWordCounts(Map<String, Integer> wordCountMap) {
        Map<String, Integer> sortedWordCountMap = wordCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        for (Map.Entry<String, Integer> entry : sortedWordCountMap.entrySet()) {
            System.out.printf("%s %d%n", entry.getKey(), entry.getValue());
        }
    }
}