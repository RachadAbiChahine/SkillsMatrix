package com.decathlon.matrix_skills.team;

import com.decathlon.matrix_skills.Application;
import com.decathlon.matrix_skills.common.Constants;
import com.decathlon.matrix_skills.errors.CustomExceptionHandler;
import com.decathlon.matrix_skills.team.dto.TeamDTO;
import com.decathlon.matrix_skills.team.errors.TeamAlreadyExistException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void init() throws TeamAlreadyExistException {
        MockitoAnnotations.initMocks(this);
        mockedTeamDto = new TeamDTO();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockedTeamDto.setTeamName("toto");
        mockedTeamDto.setTeamId(1L);


    }

    @Test
    @DisplayName("Verify that contnent Type is Json")
    void addTeam_normalCaseWhenItShouldPass() throws Exception {
        Mockito.when(teamService.addTeam(ArgumentMatchers.anyString())).thenReturn(mockedTeamDto);
        mockMvc.perform(MockMvcRequestBuilders.post(Constants.API_BASE_PATH + "/team/" + TEAM_NAME)).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("teamId").value(mockedTeamDto.getTeamId()))
                .andExpect(jsonPath(("teamName")).value(mockedTeamDto.getTeamName()));
    }


    @Test
    void addTeam_AddTeamAlreadyExist() throws Exception {
        Mockito.when(teamService.addTeam(ArgumentMatchers.anyString())).thenThrow(new TeamAlreadyExistException());
        mockMvc.perform(MockMvcRequestBuilders.post(Constants.API_BASE_PATH + "/team/" + TEAM_NAME)).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value("Team already Exist"));
    }


}