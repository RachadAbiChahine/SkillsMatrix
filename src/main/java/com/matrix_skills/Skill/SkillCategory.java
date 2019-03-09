package com.matrix_skills.Skill;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "skill_category")
public class SkillCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "skill_category_id")
    private int SkillCategoryId;

    @Column(name = "skill_category_name", unique = true, nullable = false)
    @Size(max = 255)
    private String category;

    public int getSkillCategoryId() {
        return SkillCategoryId;
    }

    public void setSkillCategoryId(int skillCategoryId) {
        SkillCategoryId = skillCategoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SkillCategory)) return false;

        SkillCategory that = (SkillCategory) o;

        return new EqualsBuilder()
                .append(getSkillCategoryId(), that.getSkillCategoryId())
                .append(getCategory(), that.getCategory())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSkillCategoryId())
                .append(getCategory())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SkillCategory{" +
                "SkillCategoryId=" + SkillCategoryId +
                ", category='" + category + '\'' +
                '}';
    }
}
