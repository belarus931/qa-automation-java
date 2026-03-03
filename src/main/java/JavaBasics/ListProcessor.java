package JavaBasics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListProcessor {
    public List<String> addNames (String... names){
        List<String> addedNames = new ArrayList<>();
        addedNames.addAll(Arrays.asList(names));
        return addedNames;
    }
    public List<String> filterLongNames(List<String> names, int maxLength){
        List<String> filteredNames = new ArrayList<>();
        for (String name: names){
            if (name.length()<=maxLength){
                filteredNames.add(name);
            }
        }
        return filteredNames;
    }
}
