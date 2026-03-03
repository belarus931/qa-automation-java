package JavaBasics;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UniqueWords {
    public Set<String> getUniqueWords(String sentence) {
        String[] wordSet = sentence.split(" ");
        Set<String> uniqueWords = new HashSet<>();
        uniqueWords.addAll(Arrays.asList(wordSet));
        return uniqueWords;
    }
}
