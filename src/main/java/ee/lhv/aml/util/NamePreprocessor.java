package ee.lhv.aml.util;

import com.ibm.icu.text.Transliterator;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class NamePreprocessor {

    // If needed, new noise words can be added
    private static final Set<String> NOISE_WORDS = new HashSet<>(asList("the", "to", "an", "mrs", "mr", "and"));

    private static final Transliterator TRANSLITERATOR = Transliterator.getInstance("Any-Latin");

    public static Set<String> preprocessName(String name) {
        name = name.toLowerCase().replaceAll("[.,!?]", "");
        name = TRANSLITERATOR.transliterate(name);

        String[] nameTokens = name.split("\\s+");
        HashSet<String> result = new HashSet<>();

        for (String nameToken: nameTokens) {
            if (!NOISE_WORDS.contains(nameToken)) {
                result.add(nameToken);
            }
        }

        return result;
    }
}
