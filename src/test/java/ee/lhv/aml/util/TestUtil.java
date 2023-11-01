package ee.lhv.aml.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ee.lhv.aml.entity.SanctionedPerson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class TestUtil {

    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final static ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .configure(WRITE_DATES_AS_TIMESTAMPS, false);

    public static List<SanctionedPerson> getSanctionedList() {
        return Collections.singletonList(new SanctionedPerson());
    }

    public static SanctionedPerson mockSanctionedPerson() {
        SanctionedPerson mocked = new SanctionedPerson();

        mocked.setName1("TEST");
        mocked.setName2("TEST");
        mocked.setName3("TEST");
        mocked.setName4("TEST");
        mocked.setName5("TEST");
        mocked.setName6("TEST");
        mocked.setTitle("TEST");
        mocked.setNameNonLatinScript("TEST");
        mocked.setNonLatinScriptType("TEST");
        mocked.setNonLatinScriptLanguage("TEST");
        mocked.setTownOfBirth("TEST");
        mocked.setCountryOfBirth("TEST");
        mocked.setPassportNumber("TEST");
        mocked.setPassportDetails("TEST");
        mocked.setNationalIdentificationNumber("TEST");
        mocked.setNationalIdentificationDetails("TEST");
        mocked.setPosition("TEST");
        mocked.setAddress1("TEST");
        mocked.setAddress2("TEST");
        mocked.setAddress3("TEST");
        mocked.setAddress4("TEST");
        mocked.setAddress5("TEST");
        mocked.setAddress6("TEST");
        mocked.setPostZipCode("TEST");
        mocked.setCountry("TEST");
        mocked.setOtherInformation("TEST");
        mocked.setGroupType("TEST");
        mocked.setAliasType("TEST");
        mocked.setAliasQuality("TEST");
        mocked.setRegime("TEST");
        mocked.setGroupId(42069);

        return mocked;
    }

    public static String toJson(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }
}
