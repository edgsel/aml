package ee.lhv.aml.rest.controller;

import ee.lhv.aml.rest.dto.request.SanctionedPersonCheckRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class SanctionedPersonController extends AmlApiController {

    @PostMapping("/sanctioned-person/checking")
    public ResponseEntity<Boolean> checkPerson(@RequestBody SanctionedPersonCheckRequest request) {
        return new ResponseEntity<>(true, OK);
    }
}
