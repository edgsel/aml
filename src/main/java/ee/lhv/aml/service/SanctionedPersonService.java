package ee.lhv.aml.service;

import ee.lhv.aml.entity.SanctionedPerson;
import ee.lhv.aml.persistance.SanctionPersonEntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static ee.lhv.aml.util.JaroWinklerUtil.isSimilarEnough;
import static ee.lhv.aml.util.NameUtil.preprocessName;

@Service
@RequiredArgsConstructor
public class SanctionedPersonService {

    private final SanctionPersonEntityManager sanctionedPersonEntityManager;

    public List<SanctionedPerson> isNameInSanctionedPersons(String sl, String user) {
        Set<String> slTokens = preprocessName(sl);
        Set<String> userTokens = preprocessName(user);
        List<SanctionedPerson> queryResults = sanctionedPersonEntityManager.findSanctionedPersons(slTokens, userTokens);

        return queryResults.stream()
            .filter(result -> isSimilarEnough(slTokens, result) || isSimilarEnough(slTokens, result))
            .toList();
    }

    public SanctionedPerson addNewSanctionedPerson(SanctionedPerson sanctionedPerson) {
        return sanctionedPersonEntityManager.saveSanctionedPerson(sanctionedPerson);
    }
}
