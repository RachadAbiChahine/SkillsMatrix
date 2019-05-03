package com.matrix_skills.team.dto;


import com.matrix_skills.team.Team;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamDTO {

    private Long teamId;
    private String teamName;

    public TeamDTO(Team team){
        teamId=team.getTeamId();
        teamName= team.getTeamName();
    }


}

