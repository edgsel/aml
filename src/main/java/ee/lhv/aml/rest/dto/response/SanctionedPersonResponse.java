package ee.lhv.aml.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class SanctionedPersonResponse {

    private Long id;

    private Boolean isSanctioned;

    private String name1;

    private String name2;

    private String name3;

    private String name4;

    private String name5;

    private String name6;

    private String country;

    private String nationality;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate listedOn;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate lastUpdated;
}
