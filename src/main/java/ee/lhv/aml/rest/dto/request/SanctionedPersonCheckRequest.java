package ee.lhv.aml.rest.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SanctionedPersonCheckRequest {

    @NotNull(message = "Sl cannot be null!")
    @NotEmpty(message = "Sl cannot be empty!")
    private String sl;

    @NotNull(message = "User cannot be null!")
    @NotEmpty(message = "User cannot be empty!")
    private String user;
}
