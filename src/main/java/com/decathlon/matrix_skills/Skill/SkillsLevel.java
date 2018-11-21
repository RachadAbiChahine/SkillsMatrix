package com.decathlon.matrix_skills.Skill;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "skills_level")
public class SkillsLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "skills_level_id")
    private int skillsLevelId;
    @Enumerated(EnumType.STRING)
    @Column(name = "level", unique = true, nullable = false)
    @Size(max = 255)
    private EnumSkillsLevel level;

    public int getSkillsLevelId() {
        return skillsLevelId;
    }

    public void setSkillsLevelId(int skillsLevelId) {
        this.skillsLevelId = skillsLevelId;
    }

    public EnumSkillsLevel getLevel() {
        return level;
    }

    public void setLevel(EnumSkillsLevel level) {
        this.level = level;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SkillsLevel)) return false;

        SkillsLevel that = (SkillsLevel) o;

        return new EqualsBuilder()
                .append(getSkillsLevelId(), that.getSkillsLevelId())
                .append(getLevel(), that.getLevel())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSkillsLevelId())
                .append(getLevel())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SkillsLevel{" +
                "skillsLevelId=" + skillsLevelId +
                ", level=" + level +
                '}';
    }
}
