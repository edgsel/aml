package ee.lhv.aml.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class HealthCheckController extends AmlApiController {

    @GetMapping(value = "/health", produces = "application/json")
    public ResponseEntity<Boolean> healthCheck() {
        return new ResponseEntity<>(true, OK);
    }
}
