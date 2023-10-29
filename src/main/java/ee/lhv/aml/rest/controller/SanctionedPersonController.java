package ee.lhv.aml.rest.controller;

import ee.lhv.aml.rest.dto.request.SanctionedPersonCheckRequest;
import ee.lhv.aml.service.SanctionedPersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequiredArgsConstructor
public class SanctionedPersonController extends AmlApiController {

    private final SanctionedPersonService sanctionedPersonService;

    @PostMapping("/sanctioned-person/checking")
    public ResponseEntity<Boolean> checkPerson(@Valid @RequestBody SanctionedPersonCheckRequest request) {
        boolean result = sanctionedPersonService.isNameInSanctionedPersons(request.getSl(), request.getUser());

        return new ResponseEntity<>(result, OK);
    }
}
