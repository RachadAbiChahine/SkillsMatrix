package com.decathlon.matrix_skills.team;

import com.decathlon.matrix_skills.Application;

import com.decathlon.matrix_skills.team.dto.TeamDTO;
import com.decathlon.matrix_skills.team.errors.TeamAlreadyExistException;
import org.junit.Assert;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    @DisplayName("adds new team that does not existe in database should passed correctly")
    public void addsTeamTest() throws TeamAlreadyExistException {
        teamService.addTeam("toto");
        Assert.assertEquals("test should pass OK because Team is not duplicated in database",
                "toto", teamRepository.findFirstByTeamNameIgnoreCase("toto").getTeamName()
                );
    }

    @Test
    @DisplayName("adds Same team twice should give Exception")
    public void addsTeamTest_addSameTeam() throws TeamAlreadyExistException {
        teamService.addTeam("toto");
        Assertions.assertThrows(TeamAlreadyExistException.class, () -> {
            teamService.addTeam("toto");
        }, "test fails because toto team already existe");
    }


    @Test
    @DisplayName("make sure Id of dto equals Id of object")
    public void addsTeamTest_assertDtoID() throws TeamAlreadyExistException {
        TeamDTO teamDTO = teamService.addTeam("toto");
        Assert.assertEquals("to make sure real id is returned in DTO", teamRepository.findFirstByTeamNameIgnoreCase("toto").getTeamId(), teamDTO.getTeamId());

    }

}