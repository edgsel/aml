package ee.lhv.aml.util;

import ee.lhv.aml.entity.SanctionedPerson;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;

import java.util.Set;

import static ee.lhv.aml.util.NameUtil.joinPreprocessedNameTokens;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class JaroWinklerUtil {

    private static final double SIMILARITY_THRESHOLD = 0.85;

    private static final double NAME_TOKEN_MATCHING_THRESHOLD = 0.75;

    private static final JaroWinklerSimilarity jaroWinklerSimilarity = new JaroWinklerSimilarity();

    public static boolean isSimilarEnough(Set<String> nameTokens, SanctionedPerson sanctionedPerson) {
        String joinedNameTokens = joinPreprocessedNameTokens(nameTokens);
        String fullNameLowered = sanctionedPerson.getFullName().toLowerCase();

        return checkWinklerSimilarity(joinedNameTokens, fullNameLowered) ||
            areMostNameTokensInJoinedName(nameTokens, fullNameLowered);
    }

    private static boolean checkWinklerSimilarity(String nameFromRequest, String nameFromDb) {
        if (isEmpty(nameFromRequest) || isEmpty(nameFromDb)) {
            return false;
        }

        return jaroWinklerSimilarity
            .apply(nameFromRequest.toLowerCase(), nameFromDb.toLowerCase()) > SIMILARITY_THRESHOLD;
    }

    private static boolean areMostNameTokensInJoinedName(Set<String> nameTokens, String joinedNameFields) {
        long count = nameTokens.stream()
            .filter(joinedNameFields::contains)
            .count();

        // 75% of name tokens should be in joinedNameFields
        return count >= (NAME_TOKEN_MATCHING_THRESHOLD * nameTokens.size());
    }
}
