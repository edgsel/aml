package ee.lhv.aml.service;

import ee.lhv.aml.entity.SanctionedPerson;
import ee.lhv.aml.persistance.SanctionPersonEntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static ee.lhv.aml.util.JaroWinklerUtil.isSimilarEnough;
import static ee.lhv.aml.util.NamePreprocessorUtil.preprocessName;

@Service
@RequiredArgsConstructor
public class SanctionedPersonService {

    private final SanctionPersonEntityManager sanctionPersonEntityManager;

    public boolean isNameInSanctionedPersons(String sl, String user) {
        Set<String> slTokens = preprocessName(sl);
        Set<String> userTokens = preprocessName(user);
        Set<String> mergedSlAndUserTokens = mergeNameTokenSets(slTokens, userTokens);

        List<SanctionedPerson> queryResults = sanctionPersonEntityManager.findSanctionedPersons(slTokens, userTokens);

        return queryResults.stream()
            .anyMatch(result -> mergedSlAndUserTokens.stream().anyMatch(token -> isSimilarEnough(token, result)));
    }

    @SafeVarargs
    private Set<String> mergeNameTokenSets(Set<String>... sets) {
        Set<String> merged = new HashSet<>();

        Stream.of(sets).forEach(merged::addAll);

        return merged;
    }
}
