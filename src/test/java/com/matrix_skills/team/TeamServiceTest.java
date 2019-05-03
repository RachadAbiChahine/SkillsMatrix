package com.matrix_skills.team;

import com.matrix_skills.Application;
import com.matrix_skills.team.dto.TeamDTO;
import com.matrix_skills.team.errors.TeamAlreadyExistException;
import com.matrix_skills.team.errors.TeamNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
public class TeamServiceTest {

    public static final String TEAM_NAME = "toto";
    public static final String TEAM_ALREADY_EXIST = "Team already Exist";
    public static final String TEAM_NOT_FOUND = "Team not found";

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    @DisplayName("adds new team that does not existe in database should passed correctly")
    public void addsTeamTest() throws TeamAlreadyExistException {
        teamService.addTeam(TEAM_NAME);
        assertEquals("test should pass OK because Team is not duplicated in database",
                TEAM_NAME, teamRepository.findFirstByTeamNameIgnoreCase(TEAM_NAME).getTeamName()
        );
    }

    @Test
    @DisplayName("adds Same team twice should give Exception")
    public void addsTeamTest_addSameTeam() throws TeamAlreadyExistException {
        teamService.addTeam(TEAM_NAME);
        TeamAlreadyExistException thrown = assertThrows(TeamAlreadyExistException.class, () -> {
            teamService.addTeam(TEAM_NAME);
        }, "Team name should be unique ");
        assertEquals(TEAM_ALREADY_EXIST, thrown.getMessage());
    }


    @Test
    @DisplayName("make sure Id of dto equals Id of object")
    public void addsTeamTest_assertDtoID() throws TeamAlreadyExistException {
        TeamDTO teamDTO = teamService.addTeam(TEAM_NAME);
        assertEquals("to make sure real id is returned in DTO", teamRepository.findFirstByTeamNameIgnoreCase(TEAM_NAME).getTeamId(), teamDTO.getTeamId());

    }


    /*test for delete method*/
    @Test
    @DisplayName("delete non existing team should throw TeamNotFoundException ")
    void delete_unExistingTeam() {
        assertThrows(TeamNotFoundException.class, () -> teamService.delete(TEAM_NAME));
    }

    @Test
    @DisplayName("delete existing should pass ok")
    void delete_deleteExistingTeam() throws TeamNotFoundException {
        Team team = new Team();
        team.setTeamName(TEAM_NAME);

        teamRepository.save(team);
        List<TeamDTO> teamDTOS = teamService.delete(TEAM_NAME);
        Assertions.assertEquals(1, teamDTOS.size());
        assertEquals(TEAM_NAME, teamDTOS.get(0).getTeamName());
    }


    /*test for getTem method*/

    @Test
    void getTeam() throws TeamNotFoundException {
        TeamNotFoundException thrown = assertThrows(TeamNotFoundException.class, () -> teamService.getTeam(TEAM_NAME));
        assertEquals(TEAM_NOT_FOUND, thrown.getMessage());
        teamRepository.save(new Team(TEAM_NAME));
        TeamDTO teamDTO = teamService.getTeam(TEAM_NAME);
        assertEquals(TEAM_NAME, teamDTO.getTeamName());
        assertEquals(teamRepository.findFirstByTeamNameIgnoreCase(TEAM_NAME).getTeamId(), teamDTO.getTeamId());
    }


    @Test
    void getAllTeams() throws TeamNotFoundException {
        TeamNotFoundException thrown = assertThrows(TeamNotFoundException.class,
                () -> teamService.getAllTeams(), "thrown if table team is empty");
        assertEquals(TEAM_NOT_FOUND, thrown.getMessage());
        List<Team> teams = new ArrayList<Team>(Arrays.asList(
                new Team[]{new Team("1"), new Team("2"), new Team("3")}));
        teamRepository.saveAll(teams);
        assertTrue(teams.size()==teamService.getAllTeams().size());
    }
}