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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
            createNamePredicate(cb, sanctionedPersonRoot, slTokens),
            createNamePredicate(cb, sanctionedPersonRoot, userTokens)
        );

        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    public SanctionedPerson saveSanctionedPerson(SanctionedPerson newSanctionedPerson) {
        newSanctionedPerson.setUkSanctionsListDateDesignated(LocalDate.now());
        newSanctionedPerson.setLastUpdated(LocalDate.now());
        newSanctionedPerson.setListedOn(LocalDate.now());

        entityManager.persist(newSanctionedPerson);
        return newSanctionedPerson;
    }

    @Transactional
    public SanctionedPerson updateSanctionedPerson(SanctionedPerson existingSanctionedPerson) {
        existingSanctionedPerson.setLastUpdated(LocalDate.now());

        return entityManager.merge(existingSanctionedPerson);
    }


    private Predicate createNamePredicate(CriteriaBuilder cb, Root<SanctionedPerson> root, Set<String> nameTokens) {
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
}
