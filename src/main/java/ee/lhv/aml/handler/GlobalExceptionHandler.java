package ee.lhv.aml.handler;

import ee.lhv.aml.rest.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR_MSG = "Server made boo boo, please try later";

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(new ErrorResponse("Invalid JSON format"), BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception ex) {
        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(new ErrorResponse(INTERNAL_SERVER_ERROR_MSG), INTERNAL_SERVER_ERROR);
    }
}
