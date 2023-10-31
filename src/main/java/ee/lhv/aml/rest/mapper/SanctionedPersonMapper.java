package ee.lhv.aml.rest.mapper;

import ee.lhv.aml.entity.SanctionedPerson;
import ee.lhv.aml.rest.dto.request.NewSanctionedPersonRequest;
import ee.lhv.aml.rest.dto.response.SanctionedPersonResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SanctionedPersonMapper {

    @Mapping(target = "id", source = "sanctionedPerson.id")
    @Mapping(target = "name1", source = "sanctionedPerson.name1")
    @Mapping(target = "name2", source = "sanctionedPerson.name2")
    @Mapping(target = "name3", source = "sanctionedPerson.name3")
    @Mapping(target = "name4", source = "sanctionedPerson.name4")
    @Mapping(target = "name5", source = "sanctionedPerson.name5")
    @Mapping(target = "name6", source = "sanctionedPerson.name6")
    @Mapping(target = "country", source = "sanctionedPerson.country")
    @Mapping(target = "nationality", source = "sanctionedPerson.nationality")
    @Mapping(target = "dob", source = "sanctionedPerson.dob")
    @Mapping(target = "listedOn", source = "sanctionedPerson.listedOn")
    @Mapping(target = "lastUpdated", source = "sanctionedPerson.lastUpdated")
    SanctionedPersonResponse mapToSanctionPersonResponse(SanctionedPerson sanctionedPerson);

    List<SanctionedPersonResponse> mapToSanctionedPersonsResponse(List<SanctionedPerson> sanctionedPersons);

    SanctionedPerson mapToSanctionPersonEntity(NewSanctionedPersonRequest request);

    @AfterMapping
    default void setIsSanctioned(
        SanctionedPerson sanctionedPerson,
        @MappingTarget SanctionedPersonResponse personResponse
    ) {
        personResponse.setIsSanctioned(true);
    }
}
