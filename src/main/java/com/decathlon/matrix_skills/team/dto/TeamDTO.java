package com.decathlon.matrix_skills.team.dto;


import com.decathlon.matrix_skills.team.Team;

public class TeamDTO {

    private Long teamId;
    private String teamName;

    public TeamDTO(){};



    public TeamDTO(Team team){
        teamId=team.getTeamId();
        teamName= team.getTeamName();
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}

