package ee.lhv.aml.service;

import ee.lhv.aml.entity.SanctionedPerson;
import ee.lhv.aml.persistance.SanctionPersonEntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static ee.lhv.aml.util.JaroWinklerUtil.isSimilarEnough;
import static ee.lhv.aml.util.NamePreprocessorUtil.preprocessName;

@Service
@RequiredArgsConstructor
public class SanctionedPersonService {

    private final SanctionPersonEntityManager sanctionPersonEntityManager;

    public boolean isNameInSanctionedPersons(String sl, String user) {
        Set<String> slTokens = preprocessName(sl);
        Set<String> userTokens = preprocessName(user);

        List<SanctionedPerson> queryResults = sanctionPersonEntityManager.findSanctionedPersons(slTokens, userTokens);

        return queryResults.stream()
            .anyMatch(result -> isSimilarEnough(sl, result) || isSimilarEnough(user, result));
    }
}
