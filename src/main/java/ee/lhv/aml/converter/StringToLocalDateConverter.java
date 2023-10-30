package ee.lhv.aml.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
@Converter(autoApply = true)
public class StringToLocalDateConverter implements AttributeConverter<LocalDate, String> {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public String convertToDatabaseColumn(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        return localDate.format(dtf);
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        if (isEmpty(dbData)) {
            return null;
        }

        try {
            return LocalDate.parse(dbData, dtf);
        } catch (DateTimeParseException ex) {
            log.warn("Failed to parse date string {}", dbData);
            return null;
        }
    }
}
