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
public class SanctionPersonEntityManager {

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

    public SanctionedPerson findSanctionedPersonById(Long sanctionedPersonId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SanctionedPerson> query = cb.createQuery(SanctionedPerson.class);
        Root<SanctionedPerson> sanctionedPersonRoot = query.from(SanctionedPerson.class);
        Predicate[] predicates = createIdAndDeleteDtimePredicate(cb, sanctionedPersonRoot, sanctionedPersonId);

        query.where(predicates);

        return Optional.of(entityManager.createQuery(query).getResultList())
            .filter(CollectionUtils::isNotEmpty)
            .map(results -> results.get(0))
            .orElse(null);
    }

    @Transactional
    public SanctionedPerson saveSanctionedPerson(SanctionedPerson newSanctionedPerson) {
        newSanctionedPerson.setUkSanctionsListDateDesignated(LocalDate.now());
        newSanctionedPerson.setLastUpdated(LocalDate.now());
        newSanctionedPerson.setListedOn(LocalDate.now());

        // get up-to-date entity
        entityManager.persist(newSanctionedPerson);
        entityManager.flush();
        entityManager.refresh(newSanctionedPerson);

        return newSanctionedPerson;
    }

    @Transactional
    public SanctionedPerson updateSanctionedPerson(
        SanctionedPerson existingSanctionedPerson,
        SanctionedPerson sanctionPersonUpdates
    ) {
        SanctionedPerson toBeUpdatedPerson = applyUpdates(existingSanctionedPerson, sanctionPersonUpdates);
        SanctionedPerson updated = entityManager.merge(toBeUpdatedPerson);

        // get up-to-date entity
        entityManager.flush();
        entityManager.refresh(updated);

        return updated;
    }

    @Transactional
    public SanctionedPerson markPersonAsDeleted(SanctionedPerson existingSanctionedPerson) {
        existingSanctionedPerson.setDeleteDtime(LocalDateTime.now());

        return entityManager.merge(existingSanctionedPerson);
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
        Long sanctionedPersonId
    ) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("id"), sanctionedPersonId));
        predicates.add(cb.isNull(root.get("deleteDtime")));

        return predicates.toArray(new Predicate[0]);
    }

    private SanctionedPerson applyUpdates(
        SanctionedPerson existingSanctionedPerson,
        SanctionedPerson sanctionPersonUpdates
    ) {
        sanctionPersonUpdates.setId(existingSanctionedPerson.getId());
        sanctionPersonUpdates.setLastUpdated(LocalDate.now());

        // The straightforward way is to update the whole object instead of handling each column
        BeanUtils.copyProperties(sanctionPersonUpdates, existingSanctionedPerson);

        return sanctionPersonUpdates;
    }
}
