package ee.lhv.aml.exception;

public class SanctionedPersonNotFoundException extends RuntimeException {
    public SanctionedPersonNotFoundException(String message) {
        super(message);
    }
}
