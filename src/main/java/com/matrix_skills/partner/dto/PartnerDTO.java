package com.matrix_skills.partner.dto;

import com.matrix_skills.Skill.Skill;
import com.matrix_skills.partner.PartnerType;
import com.matrix_skills.team.Team;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PartnerDTO {

    @Size(max = 255)
    private String name;

    private String surname;

    private PartnerType enumPartnerType;

    private Team team;

    private List<Skill> skills;

}
