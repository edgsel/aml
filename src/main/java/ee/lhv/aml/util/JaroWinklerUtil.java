package ee.lhv.aml.util;

import ee.lhv.aml.entity.SanctionedPerson;
import org.apache.commons.text.similarity.JaroWinklerDistance;

public class JaroWinklerUtil {

    private static final double JARO_WINKLER_THRESHOLD = 0.85;

    private static final JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();

    public static boolean isSimilarEnough(String name, SanctionedPerson person) {
        return jaroWinklerDistance.apply(name, person.getName6()) > JARO_WINKLER_THRESHOLD ||
            jaroWinklerDistance.apply(name, person.getName1()) > JARO_WINKLER_THRESHOLD ||
            jaroWinklerDistance.apply(name, person.getName2()) > JARO_WINKLER_THRESHOLD ||
            jaroWinklerDistance.apply(name, person.getName3()) > JARO_WINKLER_THRESHOLD ||
            jaroWinklerDistance.apply(name, person.getName4()) > JARO_WINKLER_THRESHOLD ||
            jaroWinklerDistance.apply(name, person.getName5()) > JARO_WINKLER_THRESHOLD;
    }
}
