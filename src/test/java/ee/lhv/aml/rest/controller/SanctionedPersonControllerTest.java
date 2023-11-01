package ee.lhv.aml.rest.controller;

import ee.lhv.aml.entity.SanctionedPerson;
import ee.lhv.aml.rest.mapper.SanctionedPersonMapper;
import ee.lhv.aml.service.SanctionedPersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static ee.lhv.aml.util.TestUtil.getSanctionedList;
import static ee.lhv.aml.util.TestUtil.mockSanctionedPerson;
import static ee.lhv.aml.util.TestUtil.toJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SanctionedPersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SanctionedPersonService sanctionedPersonService;

    @MockBean
    private SanctionedPersonMapper sanctionedPersonMapper;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testCheckPerson() throws Exception {
        when(sanctionedPersonService.isNameInSanctionedPersons(anyString(), anyString()))
            .thenReturn(getSanctionedList());

        mockMvc.perform(post("/api/v1/sanctioned-person/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                    "    \"sl\": \"AAL HARAMMEIN AL MASJED AL-AQSA\",\n" +
                    "    \"user\": \"AL HARAMMEIN AL MASJED AL-AQSA\"\n" +
                    "}"))
            .andExpect(status().isOk());
    }

    @Test
    public void testAddNewPerson() throws Exception {
        SanctionedPerson mockedPerson = mockSanctionedPerson();
        when(sanctionedPersonMapper.mapToSanctionedPersonEntity(any())).thenReturn(mockedPerson);
        when(sanctionedPersonService.addNewSanctionedPerson(any())).thenReturn(mockedPerson);

        mockMvc.perform(post("/api/v1/sanctioned-person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(mockedPerson)))
            .andExpect(status().isCreated());
    }

    @Test
    public void testUpdatePerson() throws Exception {
        long personId = 1L;
        SanctionedPerson mockedPerson = mockSanctionedPerson();

        when(sanctionedPersonMapper.mapToSanctionedPersonEntity(any())).thenReturn(mockedPerson);
        when(sanctionedPersonService.updateSanctionedPerson(anyLong(), any())).thenReturn(mockedPerson);

        mockMvc.perform(put("/api/v1/sanctioned-person/" + personId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(mockedPerson)))
            .andExpect(status().isOk());
    }

    @Test
    public void testDeletePerson() throws Exception {
        Long personId = 1L;

        when(sanctionedPersonService.deleteSanctionedPerson(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/v1/sanctioned-person/" + personId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }
}
