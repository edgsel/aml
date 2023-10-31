package ee.lhv.aml.service;

import ee.lhv.aml.entity.SanctionedPerson;
import ee.lhv.aml.exception.SanctionedPersonNotFoundException;
import ee.lhv.aml.persistance.SanctionPersonEntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public SanctionedPerson updateSanctionPerson(Long sanctionedPersonId, SanctionedPerson sanctionedPersonUpdates) {
        SanctionedPerson existingSanctionedPerson = Optional
            .ofNullable(sanctionedPersonEntityManager.findSanctionedPersonById(sanctionedPersonId))
            .orElseThrow(() -> {
                final String errorMsg = "Sanctioned person with ID " + sanctionedPersonId + " not found";
                return new SanctionedPersonNotFoundException(errorMsg);
            });

        return sanctionedPersonEntityManager.updateSanctionedPerson(existingSanctionedPerson, sanctionedPersonUpdates);
    }
}
