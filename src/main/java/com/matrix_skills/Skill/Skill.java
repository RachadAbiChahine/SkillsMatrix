package com.matrix_skills.Skill;

import com.matrix_skills.partner.Partner;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;
@Entity
@Table(name = "skill")
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


    public int getSkillLevelId() {
        return skillLevelId;
    }

    public void setSkillLevelId(int skillLevelId) {
        this.skillLevelId = skillLevelId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public com.matrix_skills.Skill.SkillsLevel getSkillsLevel() {
        return SkillsLevel;
    }

    public void setSkillsLevel(com.matrix_skills.Skill.SkillsLevel skillsLevel) {
        SkillsLevel = skillsLevel;
    }

    public Partner getOwner() {
        return owner;
    }

    public void setOwner(Partner owner) {
        this.owner = owner;
    }

    public SkillCategory getSkillCategory() {
        return skillCategory;
    }

    public void setSkillCategory(SkillCategory skillCategory) {
        this.skillCategory = skillCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Skill)) return false;

        Skill skill = (Skill) o;

        return new EqualsBuilder()
                .append(getSkillLevelId(), skill.getSkillLevelId())
                .append(getSkillName(), skill.getSkillName())
                .append(getSkillsLevel(), skill.getSkillsLevel())
                .append(getOwner(), skill.getOwner())
                .append(getSkillCategory(), skill.getSkillCategory())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSkillLevelId())
                .append(getSkillName())
                .append(getSkillsLevel())
                .append(getOwner())
                .append(getSkillCategory())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Skill{" +
                "skillLevelId=" + skillLevelId +
                ", skillName='" + skillName + '\'' +
                ", SkillsLevel=" + SkillsLevel +
                ", owner=" + owner +
                ", skillCategory=" + skillCategory +
                '}';
    }
}
