package com.matrix_skills.Skill;

import com.matrix_skills.partner.Partner;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
@Entity
@Table(name = "skill")
@Data
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "skill_id")
    private int skillLevelId;

    @Column(name = "skill_name", nullable = false)
    @Size(max = 255)
    private String skillName;

    @OneToOne
    @JoinColumn(name = "skill_level")
    private SkillsLevel SkillsLevel;

  @OneToOne
    @JoinColumn(name = "skill_owner")
    private Partner owner;

    @OneToOne
    @JoinColumn(name = "skill_category")
    private SkillCategory skillCategory;

}
