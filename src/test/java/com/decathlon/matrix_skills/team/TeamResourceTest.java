package com.decathlon.matrix_skills.team;

import com.decathlon.matrix_skills.common.Constants;
import com.decathlon.matrix_skills.team.dto.TeamDTO;
import com.decathlon.matrix_skills.team.errors.TeamAlreadyExistException;
import com.decathlon.matrix_skills.team.errors.TeamNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Tests action on Team")
class TeamResourceTest {

    private static final String TEAM_NAME = "TOTO";

    @Autowired
    WebApplicationContext webApplicationContext;


    @MockBean
    TeamService teamService;


    private MockMvc mockMvc;


    TeamDTO mockedTeamDto;


    @PostConstruct
    void init() throws TeamAlreadyExistException {
        MockitoAnnotations.initMocks(this);
        mockedTeamDto = new TeamDTO();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockedTeamDto.setTeamName(TEAM_NAME);
        mockedTeamDto.setTeamId(1L);


    }

    @Test
    @DisplayName("Verify that contnent Type is Json")
    void addTeam() throws Exception {
        when(teamService.addTeam(ArgumentMatchers.anyString())).thenReturn(mockedTeamDto);
        mockMvc.perform(post(Constants.API_BASE_PATH + "/team/" + TEAM_NAME)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("teamId").value(mockedTeamDto.getTeamId()))
                .andExpect(jsonPath(("teamName")).value(mockedTeamDto.getTeamName()));
    }


    @Test
    void addTeam_AddTeamAlreadyExist() throws Exception {
        when(teamService.addTeam(ArgumentMatchers.anyString())).thenThrow(new TeamAlreadyExistException());
        mockMvc.perform(post(Constants.API_BASE_PATH + "/team/" + TEAM_NAME)).andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value("Team already Exist"));
    }


    @Test
    @DisplayName("test the case where everything is OK")
    void deleteTam() throws Exception {
        when(teamService.delete(TEAM_NAME)).thenReturn(Collections.singletonList((mockedTeamDto)));
        mockMvc.perform(delete(Constants.API_BASE_PATH + "/team/" + TEAM_NAME)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].teamName").value(TEAM_NAME));
    }


    @Test
    @DisplayName("when we try to delete unexiste team we should get 404")
    void deleteTeam() throws Exception {
        when(teamService.delete(anyString())).thenThrow(new TeamNotFoundException());
        mockMvc.perform(delete(Constants.API_BASE_PATH + "/team/" + TEAM_NAME)).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Team not found"));
    }

    @Test
    @DisplayName("test validated on pathVariable")
    void deleteTeam_validatedPAthVariable() throws Exception {
        String pathVariable = RandomStringUtils.randomAlphanumeric(256);
        mockMvc.perform(delete(Constants.API_BASE_PATH + "/team/" + pathVariable)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("fieldErrors", hasSize(1)))
                .andExpect((jsonPath("fieldErrors[0].field").value("teamName")));
    }


    @Test
    @DisplayName("test get existing Team")
    void getTeam() throws Exception {
        when(teamService.getTeam(anyString())).thenReturn(mockedTeamDto);
        when(teamService.delete(anyString())).thenThrow(new TeamNotFoundException());
        mockMvc.perform(get(Constants.API_BASE_PATH + "/team/" + TEAM_NAME)).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("teamName").value(mockedTeamDto.getTeamName()))
                .andExpect(jsonPath("teamId").value(mockedTeamDto.getTeamId()));
    }

    @Test
    @DisplayName("test get nonexistent Team ")
    void getTeam_nonexistentTeam() throws Exception {
        when(teamService.getTeam(anyString())).thenThrow(new TeamNotFoundException());
        mockMvc.perform(get(Constants.API_BASE_PATH + "/team/" + TEAM_NAME)).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Team not found"));
    }

    @Test
    @DisplayName("test get all team on Existing Team data")
    void getAllTeams() throws Exception {
        when(teamService.getAllTeams()).thenReturn(Collections.singletonList((mockedTeamDto)));
        mockMvc.perform(get(Constants.API_BASE_PATH + "/team/")).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].teamName").value(TEAM_NAME));
    }

    @Test
    @DisplayName("get all team on empty table")
    void getAllTeams_EmptyTable() throws Exception {
        when(teamService.getAllTeams()).thenThrow(new TeamNotFoundException());
        mockMvc.perform(get(Constants.API_BASE_PATH + "/team/")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Team not found"));
    }
}