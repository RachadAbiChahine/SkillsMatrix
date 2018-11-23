package com.decathlon.matrix_skills.partner.dto;

import com.decathlon.matrix_skills.Skill.Skill;
import com.decathlon.matrix_skills.partner.PartnerType;
import com.decathlon.matrix_skills.team.Team;

import javax.validation.constraints.Size;
import java.util.List;

public class PartnerDTO {

    @Size(max = 255)
    private String name;

    private String surname;
    private PartnerType enumPartnerType;
    private Team team;
    private List<Skill> skills;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public PartnerType getEnumPartnerType() {
        return enumPartnerType;
    }

    public void setEnumPartnerType(PartnerType enumPartnerType) {
        this.enumPartnerType = enumPartnerType;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
