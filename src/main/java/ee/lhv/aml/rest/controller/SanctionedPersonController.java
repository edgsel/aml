package ee.lhv.aml.rest.controller;

import ee.lhv.aml.entity.SanctionedPerson;
import ee.lhv.aml.rest.dto.request.SanctionedPersonRequest;
import ee.lhv.aml.rest.dto.request.SanctionedPersonCheckRequest;
import ee.lhv.aml.rest.dto.response.ErrorResponse;
import ee.lhv.aml.rest.dto.response.SanctionedPersonResponse;
import ee.lhv.aml.rest.mapper.SanctionedPersonMapper;
import ee.lhv.aml.service.SanctionedPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequiredArgsConstructor
public class SanctionedPersonController extends AmlApiController {

    private final SanctionedPersonService sanctionedPersonService;

    private final SanctionedPersonMapper sanctionedPersonMapper;

    @Operation(summary = "Check if person is sanctioned")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns list sanctioned person(s) or empty list",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = SanctionedPerson.class)) })
    })
    @PostMapping(value = "/sanctioned-person/check", produces = "application/json")
    public ResponseEntity<List<SanctionedPersonResponse>> checkPerson(@Valid @RequestBody SanctionedPersonCheckRequest request) {
        List<SanctionedPerson> results = sanctionedPersonService.isNameInSanctionedPersons(
            request.getSl(),
            request.getUser()
        );

        return new ResponseEntity<>(sanctionedPersonMapper.mapToSanctionedPersonsResponse(results), OK);
    }

    @Operation(summary = "Add a new sanctioned person")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "NB! Use dd/MM/yyyy for date fields. Required fields: name6, groupType, aliasType, regime, groupId")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "New sanctioned person is added",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = SanctionedPerson.class)) }),
        @ApiResponse(responseCode = "400", description = "Request validation failed",
            content = @Content)
    })
    @PostMapping(value = "/sanctioned-person", produces = "application/json")
    public ResponseEntity<SanctionedPersonResponse> addNewPerson(@Valid @RequestBody SanctionedPersonRequest request) {
        SanctionedPerson newPerson = sanctionedPersonMapper.mapToSanctionedPersonEntity(request);
        SanctionedPerson result = sanctionedPersonService.addNewSanctionedPerson(newPerson);

        return new ResponseEntity<>(sanctionedPersonMapper.mapToSanctionedPersonResponse(result), CREATED);
    }

    @Operation(summary = "Update an existing sanction person by its ID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "NB! Use dd/MM/yyyy for date fields. Required fields: name6, groupType, aliasType, regime, groupId")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sanctioned person updated",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = SanctionedPerson.class)) }),
        @ApiResponse(responseCode = "400", description = "Request validation failed",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Sanctioned person not found",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)) }) })
    @PutMapping(value = "/sanctioned-person/{personId}", produces = "application/json")
    public ResponseEntity<SanctionedPersonResponse> updatePerson(
        @PathVariable Long personId,
        @Valid @RequestBody SanctionedPersonRequest request
    ) {
        SanctionedPerson personUpdate = sanctionedPersonMapper.mapToSanctionedPersonEntity(request);
        SanctionedPerson result = sanctionedPersonService.updateSanctionedPerson(personId, personUpdate);

        return new ResponseEntity<>(sanctionedPersonMapper.mapToSanctionedPersonResponse(result), OK);
    }

    @Operation(summary = "Delete an existing sanction person by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sanctioned person deleted",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Boolean.class)) }),
        @ApiResponse(responseCode = "404", description = "Sanctioned person not found",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)) }) })
    @DeleteMapping(value = "/sanctioned-person/{personId}", produces = "application/json")
    public ResponseEntity<Boolean> deletePerson(@PathVariable Long personId) {
        return new ResponseEntity<>(sanctionedPersonService.deleteSanctionedPerson(personId), OK);
    }
}
