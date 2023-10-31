package ee.lhv.aml.persistance;

import ee.lhv.aml.entity.SanctionedPerson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SanctionedPersonEntityManager {

    @PersistenceContext
    private final EntityManager entityManager;

    public List<SanctionedPerson> findSanctionedPersons(Set<String> slTokens, Set<String> userTokens) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SanctionedPerson> query = cb.createQuery(SanctionedPerson.class);
        Root<SanctionedPerson> sanctionedPersonRoot = query.from(SanctionedPerson.class);

        Predicate predicate = cb.or(
            createNameSearchPredicate(cb, sanctionedPersonRoot, slTokens),
            createNameSearchPredicate(cb, sanctionedPersonRoot, userTokens)
        );

        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    public SanctionedPerson findSanctionedPersonById(Long personId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SanctionedPerson> query = cb.createQuery(SanctionedPerson.class);
        Root<SanctionedPerson> sanctionedPersonRoot = query.from(SanctionedPerson.class);
        Predicate[] predicates = createIdAndDeleteDtimePredicate(cb, sanctionedPersonRoot, personId);

        query.where(predicates);

        return Optional.of(entityManager.createQuery(query).getResultList())
            .filter(CollectionUtils::isNotEmpty)
            .map(results -> results.get(0))
            .orElse(null);
    }

    @Transactional
    public SanctionedPerson saveSanctionedPerson(SanctionedPerson newPerson) {
        newPerson.setUkSanctionsListDateDesignated(LocalDate.now());
        newPerson.setLastUpdated(LocalDate.now());
        newPerson.setListedOn(LocalDate.now());

        // get up-to-date entity
        entityManager.persist(newPerson);
        entityManager.flush();
        entityManager.refresh(newPerson);

        return newPerson;
    }

    @Transactional
    public SanctionedPerson updateSanctionedPerson(
        SanctionedPerson existingPerson,
        SanctionedPerson personUpdates
    ) {
        SanctionedPerson toBeUpdatedPerson = applyUpdates(existingPerson, personUpdates);
        SanctionedPerson updated = entityManager.merge(toBeUpdatedPerson);

        // get up-to-date entity
        entityManager.flush();
        entityManager.refresh(updated);

        return updated;
    }

    @Transactional
    public SanctionedPerson markPersonAsDeleted(SanctionedPerson existingPerson) {
        existingPerson.setDeleteDtime(LocalDateTime.now());

        SanctionedPerson markedAsDeleted = entityManager.merge(existingPerson);

        // get up-to-date entity
        entityManager.flush();
        entityManager.refresh(markedAsDeleted);

        return markedAsDeleted;
    }

    private Predicate createNameSearchPredicate(CriteriaBuilder cb, Root<SanctionedPerson> root, Set<String> nameTokens) {
        Predicate[] predicates = nameTokens.stream()
            .map(token -> cb.or(
                cb.like(cb.lower(root.get("name6")), "%" + token + "%"),
                cb.like(cb.lower(root.get("name1")), "%" + token + "%"),
                cb.like(cb.lower(root.get("name2")), "%" + token + "%"),
                cb.like(cb.lower(root.get("name3")), "%" + token + "%"),
                cb.like(cb.lower(root.get("name4")), "%" + token + "%"),
                cb.like(cb.lower(root.get("name5")), "%" + token + "%")
            )).toArray(Predicate[]::new);

        return cb.or(predicates);
    }

    private Predicate[] createIdAndDeleteDtimePredicate(
        CriteriaBuilder cb,
        Root<SanctionedPerson> root,
        Long personId
    ) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("id"), personId));
        predicates.add(cb.isNull(root.get("deleteDtime")));

        return predicates.toArray(new Predicate[0]);
    }

    private SanctionedPerson applyUpdates(
        SanctionedPerson existingPerson,
        SanctionedPerson personUpdates
    ) {
        personUpdates.setId(existingPerson.getId());
        personUpdates.setLastUpdated(LocalDate.now());

        // The straightforward way is to update the whole object instead of handling each column
        BeanUtils.copyProperties(personUpdates, existingPerson);

        return personUpdates;
    }
}
