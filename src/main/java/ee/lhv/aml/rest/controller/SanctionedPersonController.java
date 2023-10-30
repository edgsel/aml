package ee.lhv.aml.rest.controller;

import ee.lhv.aml.entity.SanctionedPerson;
import ee.lhv.aml.rest.dto.request.SanctionedPersonCheckRequest;
import ee.lhv.aml.rest.dto.response.SanctionedPersonResponse;
import ee.lhv.aml.rest.mapper.SanctionedPersonMapper;
import ee.lhv.aml.service.SanctionedPersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequiredArgsConstructor
public class SanctionedPersonController extends AmlApiController {

    private final SanctionedPersonService sanctionedPersonService;

    private final SanctionedPersonMapper sanctionedPersonMapper;

    @PostMapping("/sanctioned-person/checking")
    public ResponseEntity<List<SanctionedPersonResponse>> checkPerson(@Valid @RequestBody SanctionedPersonCheckRequest request) {
        List<SanctionedPerson> results = sanctionedPersonService
            .isNameInSanctionedPersons(request.getSl(), request.getUser());

        return new ResponseEntity<>(sanctionedPersonMapper.mapToSanctionedPersonsResponse(results), OK);
    }
}
