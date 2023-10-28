package ee.lhv.aml.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class HealthCheckController extends AmlApiController {

    @GetMapping(value = "/health", produces = "application/json")
    public ResponseEntity<Boolean> healthCheck() {
        return new ResponseEntity<>(true, OK);
    }
}
