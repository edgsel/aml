package ee.lhv.aml.util;

import com.ibm.icu.text.Transliterator;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class NameUtil {

    // If needed, new noise words can be added
    private static final Set<String> NOISE_WORDS = new HashSet<>(asList("the", "to", "an", "mrs", "mr", "and"));

    private static final Transliterator TRANSLITERATOR = Transliterator.getInstance("Russian-Latin/BGN");

    public static Set<String> preprocessName(String name) {
        name = name.toLowerCase().replaceAll("[.,!?]", " ");
        name = TRANSLITERATOR.transliterate(name);

        String[] nameTokens = name.split("\\s+");
        HashSet<String> result = new LinkedHashSet<>(); // save the order of string tokens

        for (String nameToken: nameTokens) {
            if (!NOISE_WORDS.contains(nameToken)) {
                result.add(nameToken);
            }
        }

        return result;
    }

    public static String joinPreprocessedNameTokens(Set<String> nameTokens) {
        return String.join(" ", nameTokens);
    }

    public static String concatNameFields(String... nameFields) {
        return Stream.of(nameFields)
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
    }
}
