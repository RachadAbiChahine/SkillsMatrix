package com.matrix_skills.team;

import com.matrix_skills.common.Constants;
import com.matrix_skills.team.dto.TeamDTO;
import com.matrix_skills.team.errors.TeamAlreadyExistException;
import com.matrix_skills.team.errors.TeamNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping(Constants.API_BASE_PATH + "/team")
@Validated
public class TeamResource {
    private TeamService teamService;

    @Autowired
    public TeamResource(TeamService teamService) {
        this.teamService = teamService;
    }


    @PostMapping(value = "/{teamName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> addTeam(@Size(max = 255)
                                           @PathVariable("teamName") String teamName) throws TeamAlreadyExistException {
        TeamDTO teamDTO = teamService.addTeam(teamName);
        return ResponseEntity.ok(teamDTO);
    }


    @DeleteMapping("/{teamName}")
    public ResponseEntity<List<TeamDTO>> deleteTeam(@Size(max = 255)
                                                   @PathVariable("teamName") String teamName) throws TeamNotFoundException {
        List<TeamDTO> teamDTOS = teamService.delete(teamName);
        return ResponseEntity.ok(teamDTOS);
    }

    @GetMapping("/{teamName}")
    public ResponseEntity<TeamDTO> getTeam(@Size(max = 255)
                                           @PathVariable("teamName") String teamName) throws TeamNotFoundException {
        TeamDTO teamDTO = teamService.getTeam(teamName);
        return ResponseEntity.ok(teamDTO);

    }


    @GetMapping("/")
    public ResponseEntity<List<TeamDTO>> getAllTeams() throws TeamNotFoundException {
        List<TeamDTO> teamDTOS = teamService.getAllTeams();
        return ResponseEntity.ok(teamDTOS);
    }


}
