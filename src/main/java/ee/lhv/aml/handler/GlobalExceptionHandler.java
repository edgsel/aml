package ee.lhv.aml.handler;

import ee.lhv.aml.rest.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR_MSG = "Server made boo boo, please try later";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInvalidEnumExceptions(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(INTERNAL_SERVER_ERROR_MSG), INTERNAL_SERVER_ERROR);
    }
}
