package com.matrix_skills.partner;

import com.matrix_skills.common.Constants;
import com.matrix_skills.partner.dto.PartnerDTO;
import com.matrix_skills.partner.dto.PartnerResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Tests action on Team")
class PartnerResourceTest {

    public static final String SURNAME = "SURNAME";
    public static final String NAME = "Name";
    @Autowired
    WebApplicationContext webApplicationContext;


    @MockBean
    PartnerService partnerService;


    private MockMvc mockMvc;

    ObjectMapper objectMapper;


    @PostConstruct
    void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void addPartner() throws Exception {
        PartnerDTO partnerDTO = new PartnerDTO();
        partnerDTO.setSurname(SURNAME);
        partnerDTO.setName(NAME);

        PartnerResponseDTO partnerResponseDTO = new PartnerResponseDTO();
        partnerResponseDTO.setId(1L);
        partnerResponseDTO.setName(NAME);
        partnerResponseDTO.setSurname(SURNAME);
        when(partnerService.addPartner(any())).thenReturn(partnerResponseDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(Constants.API_BASE_PATH + "/partner/")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(partnerDTO))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id").value(partnerResponseDTO.getId()))
                .andExpect((jsonPath("name").value(partnerResponseDTO.getName())))
                .andExpect(jsonPath("surname").value(partnerResponseDTO.getSurname()));
    }

    @Test
    @DisplayName("validates that we can't use an [a-z]")
    void editPartner_invalidId() throws Exception {
        PartnerDTO partnerDTO = new PartnerDTO();
        partnerDTO.setSurname(SURNAME);
        partnerDTO.setName(NAME);

        mockMvc.perform(patch(Constants.API_BASE_PATH + "/partner/" + "ec1").content(objectMapper.writeValueAsString(partnerDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }


    @Test
    void editPartner() throws Exception {
        PartnerResponseDTO partnerResponseDTO = new PartnerResponseDTO();
        partnerResponseDTO.setSurname(SURNAME);
        partnerResponseDTO.setName(NAME);
        partnerResponseDTO.setId(1L);

        PartnerDTO partnerDTO = new PartnerDTO();
        partnerDTO.setSurname(SURNAME);
        partnerDTO.setName(NAME);

        when(partnerService.editPartner(anyLong(),any())).thenReturn(partnerResponseDTO);
        mockMvc.perform(patch(Constants.API_BASE_PATH + "/partner/1").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(partnerDTO)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(NAME))
                .andExpect((jsonPath("surname").value(SURNAME)));
    }
}