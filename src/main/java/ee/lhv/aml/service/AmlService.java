package ee.lhv.aml.service;

import ee.lhv.aml.repository.SanctionedPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AmlService {

    private final SanctionedPersonRepository sanctionedPersonRepository;
}
