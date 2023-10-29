package ee.lhv.aml.util;

import ee.lhv.aml.entity.SanctionedPerson;
import org.apache.commons.text.similarity.JaroWinklerDistance;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class JaroWinklerUtil {

    private static final double JARO_WINKLER_THRESHOLD = 0.85;

    private static final JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();

    public static boolean isSimilarEnough(String name, SanctionedPerson person) {
        return checkWinklerDistance(name, person.getName6()) ||
            checkWinklerDistance(name, person.getName1()) ||
            checkWinklerDistance(name, person.getName2()) ||
            checkWinklerDistance(name, person.getName3()) ||
            checkWinklerDistance(name, person.getName4()) ||
            checkWinklerDistance(name, person.getName5());
    }

    private static boolean checkWinklerDistance(String nameFromRequest, String nameFromDb) {
        if (isEmpty(nameFromRequest) || isEmpty(nameFromDb)) {
            return false;
        }

        return jaroWinklerDistance.apply(nameFromRequest, nameFromDb) > JARO_WINKLER_THRESHOLD;
    }
}
