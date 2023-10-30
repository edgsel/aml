package ee.lhv.aml.util;

import ee.lhv.aml.entity.SanctionedPerson;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class JaroWinklerUtil {

    private static final double SIMILARITY_THRESHOLD = 0.85;

    private static final JaroWinklerSimilarity jaroWinklerSimilarity = new JaroWinklerSimilarity();

    public static boolean isSimilarEnough(String name, SanctionedPerson person) {
        return checkWinklerSimilarity(name, person.getName6()) ||
            checkWinklerSimilarity(name, person.getName1()) ||
            checkWinklerSimilarity(name, person.getName2()) ||
            checkWinklerSimilarity(name, person.getName3()) ||
            checkWinklerSimilarity(name, person.getName4()) ||
            checkWinklerSimilarity(name, person.getName5());
    }

    private static boolean checkWinklerSimilarity(String nameFromRequest, String nameFromDb) {
        if (isEmpty(nameFromRequest) || isEmpty(nameFromDb)) {
            return false;
        }

        return jaroWinklerSimilarity
            .apply(nameFromRequest.toLowerCase(), nameFromDb.toLowerCase()) > SIMILARITY_THRESHOLD;
    }
}
