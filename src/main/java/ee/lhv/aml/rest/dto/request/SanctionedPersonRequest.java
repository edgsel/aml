package ee.lhv.aml.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SanctionedPersonRequest {

    @NotNull(message = "name6 cannot be null!")
    @NotEmpty(message = "name6 cannot be empty!")
    private String name6;

    private String name1;

    private String name2;

    private String name3;

    private String name4;

    private String name5;

    private String title;

    private String nameNonLatinScript;

    private String nonLatinScriptType;

    private String nonLatinScriptLanguage;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;

    private String townOfBirth;

    private String countryOfBirth;

    private String nationality;

    private String passportNumber;

    private String passportDetails;

    private String nationalIdentificationNumber;

    private String nationalIdentificationDetails;

    private String position;

    private String address1;

    private String address2;

    private String address3;

    private String address4;

    private String address5;

    private String address6;

    private String postZipCode;

    private String country;

    private String otherInformation;

    @NotNull(message = "groupType cannot be null!")
    @NotEmpty(message = "groupType cannot be empty!")
    private String groupType;

    @NotNull(message = "aliasType cannot be null!")
    @NotEmpty(message = "aliasType cannot be empty!")
    private String aliasType;

    private String aliasQuality;

    @NotNull(message = "regime cannot be null!")
    @NotEmpty(message = "regime cannot be empty!")
    private String regime;

    @NotNull(message = "groupId cannot be null!")
    private Integer groupId;
}
