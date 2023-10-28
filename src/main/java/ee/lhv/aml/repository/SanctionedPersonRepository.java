package ee.lhv.aml.repository;

import ee.lhv.aml.entity.SanctionedPerson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionedPersonRepository extends CrudRepository<SanctionedPerson, Long> {

}
