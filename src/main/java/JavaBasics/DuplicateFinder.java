package JavaBasics;

import javax.lang.model.element.Element;
import java.util.*;

public class DuplicateFinder {
    public Set<Integer> findDuplicates(List<Integer> numbers){
        Map<Integer, Integer> frequency = new HashMap<>();
        Set<Integer> duplicates = new HashSet<>();
        for (Integer number: numbers){
            if (frequency.containsKey(number)){
                duplicates.add(number);
            }
            else
                frequency.put(number, 1);
        }
        return duplicates;
    }
}
