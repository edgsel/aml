package ee.lhv.aml.service;

import ee.lhv.aml.entity.SanctionedPerson;
import ee.lhv.aml.exception.SanctionedPersonNotFoundException;
import ee.lhv.aml.persistance.SanctionedPersonEntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ee.lhv.aml.util.NameUtil.preprocessName;

@Service
@RequiredArgsConstructor
public class SanctionedPersonService {

    private final SanctionedPersonEntityManager sanctionedPersonEntityManager;

    private final JaroWinklerService jaroWinklerService;

    public List<SanctionedPerson> isNameInSanctionedPersons(String sl, String user) {
        Set<String> slTokens = preprocessName(sl);
        Set<String> userTokens = preprocessName(user);
        List<SanctionedPerson> queryResults = sanctionedPersonEntityManager.findSanctionedPersons(slTokens, userTokens);

        return queryResults.stream()
            .filter(result -> areNamesSimilarEnough(result, slTokens, userTokens))
            .sorted(Comparator.comparing(SanctionedPerson::getId))
            .toList();
    }

    public SanctionedPerson addNewSanctionedPerson(SanctionedPerson newPerson) {
        return sanctionedPersonEntityManager.saveSanctionedPerson(newPerson);
    }

    public SanctionedPerson updateSanctionedPerson(Long personId, SanctionedPerson personUpdates) {
        SanctionedPerson existingPerson = findSanctionedPerson(personId);

        return sanctionedPersonEntityManager.updateSanctionedPerson(existingPerson, personUpdates);
    }

    public Boolean deleteSanctionedPerson(Long personId) {
        SanctionedPerson existingPerson = findSanctionedPerson(personId);
        SanctionedPerson deletedPerson = sanctionedPersonEntityManager.markPersonAsDeleted(existingPerson);
        
        return deletedPerson.getDeleteDtime() != null;
    }

    private SanctionedPerson findSanctionedPerson(Long personId) {
        return Optional
            .ofNullable(sanctionedPersonEntityManager.findSanctionedPersonById(personId))
            .orElseThrow(() -> {
                final String errorMsg = "Sanctioned person with ID " + personId + " not found";
                return new SanctionedPersonNotFoundException(errorMsg);
            });
    }

    private Boolean areNamesSimilarEnough(SanctionedPerson person, Set<String> slTokens, Set<String> userTokens) {
        return jaroWinklerService.isSimilarEnough(slTokens, person)
            || jaroWinklerService.isSimilarEnough(userTokens, person);
    }
}
