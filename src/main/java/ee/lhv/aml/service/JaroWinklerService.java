package ee.lhv.aml.service;

import ee.lhv.aml.entity.SanctionedPerson;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

import static ee.lhv.aml.util.NameUtil.joinPreprocessedNameTokens;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class JaroWinklerService {

    @Value("${lhv.aml.similarity-threshold}")
    private double SIMILARITY_THRESHOLD;

    @Value("${lhv.aml.name-token-matching-threshold}")
    private double NAME_TOKEN_MATCHING_THRESHOLD;

    private static final JaroWinklerSimilarity jaroWinklerSimilarity = new JaroWinklerSimilarity();

    public boolean isSimilarEnough(Set<String> nameTokens, SanctionedPerson sanctionedPerson) {
        String joinedNameTokens = joinPreprocessedNameTokens(nameTokens);
        String fullNameLowered = sanctionedPerson.getFullName().toLowerCase();

        return checkWinklerSimilarity(joinedNameTokens, fullNameLowered) ||
            areMostNameTokensInJoinedName(nameTokens, fullNameLowered);
    }

    private boolean checkWinklerSimilarity(String nameFromRequest, String nameFromDb) {
        if (isEmpty(nameFromRequest) || isEmpty(nameFromDb)) {
            return false;
        }

        return jaroWinklerSimilarity
            .apply(nameFromRequest.toLowerCase(), nameFromDb.toLowerCase()) > SIMILARITY_THRESHOLD;
    }

    private boolean areMostNameTokensInJoinedName(Set<String> nameTokens, String joinedNameFields) {
        long count = nameTokens.stream()
            .filter(joinedNameFields::contains)
            .count();

        // 75% of name tokens should be in joinedNameFields
        return count >= (NAME_TOKEN_MATCHING_THRESHOLD * nameTokens.size());
    }
}
