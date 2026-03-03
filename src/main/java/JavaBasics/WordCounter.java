package JavaBasics;

import java.util.HashMap;
import java.util.Map;

public class WordCounter {
    public Map<String, Integer> countWords(String sentence){

        Map<String, Integer> wordCount = new HashMap<>();

        String[] words = sentence.split(" ");
        for (String word: words){
            if (wordCount.containsKey(word)) {
                int currentCount = wordCount.get(word);
                wordCount.put(word, currentCount+1);
            }
            else
                wordCount.put(word, 1);

        }
        return wordCount;
    }
}
