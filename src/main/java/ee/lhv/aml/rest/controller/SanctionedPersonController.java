package ee.lhv.aml.rest.controller;

import ee.lhv.aml.entity.SanctionedPerson;
import ee.lhv.aml.rest.dto.request.SanctionedPersonRequest;
import ee.lhv.aml.rest.dto.request.SanctionedPersonCheckRequest;
import ee.lhv.aml.rest.dto.response.SanctionedPersonResponse;
import ee.lhv.aml.rest.mapper.SanctionedPersonMapper;
import ee.lhv.aml.service.SanctionedPersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequiredArgsConstructor
public class SanctionedPersonController extends AmlApiController {

    private final SanctionedPersonService sanctionedPersonService;

    private final SanctionedPersonMapper sanctionedPersonMapper;

    @PostMapping(value = "/sanctioned-person/check", produces = "application/json")
    public ResponseEntity<List<SanctionedPersonResponse>> checkPerson(@Valid @RequestBody SanctionedPersonCheckRequest request) {
        List<SanctionedPerson> results = sanctionedPersonService
            .isNameInSanctionedPersons(request.getSl(), request.getUser());

        return new ResponseEntity<>(sanctionedPersonMapper.mapToSanctionedPersonsResponse(results), OK);
    }

    @PostMapping(value = "/sanctioned-person", produces = "application/json")
    public ResponseEntity<SanctionedPersonResponse> addNewPerson(@Valid @RequestBody SanctionedPersonRequest request) {
        SanctionedPerson newSanctionedPerson = sanctionedPersonMapper.mapToSanctionPersonEntity(request);
        SanctionedPerson result = sanctionedPersonService.addNewSanctionedPerson(newSanctionedPerson);

        return new ResponseEntity<>(sanctionedPersonMapper.mapToSanctionPersonResponse(result), CREATED);
    }

    @PutMapping(value = "/sanctioned-person/{sanctionedPersonId}", produces = "application/json")
    public ResponseEntity<SanctionedPersonResponse> updatePerson(
        @PathVariable Long sanctionedPersonId,
        @Valid @RequestBody SanctionedPersonRequest request
    ) {
        SanctionedPerson sanctionedPersonUpdate = sanctionedPersonMapper.mapToSanctionPersonEntity(request);
        SanctionedPerson result = sanctionedPersonService.updateSanctionPerson(sanctionedPersonId, sanctionedPersonUpdate);

        return new ResponseEntity<>(sanctionedPersonMapper.mapToSanctionPersonResponse(result), OK);
    }
}
