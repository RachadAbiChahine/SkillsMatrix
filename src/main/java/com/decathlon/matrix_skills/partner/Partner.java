package com.decathlon.matrix_skills.partner;

import com.decathlon.matrix_skills.Skill.Skill;
import com.decathlon.matrix_skills.team.Team;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "partner")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_id")
    private Long partnerId;

    @Column(name = "partner_name")
    @Size(message = "the name size should be maximum 256", max = 256)
    private String name;

    @Column(name = "partner_surname")
    @Size(message = "the surname size should be maximum 256", max = 256)
    private String surname;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "partner_type")
    private PartnerType enumPartnerType;

    @ManyToOne
    @JoinColumn(name = "team")
    private Team team;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "skill_owner")
    private List<Skill> skills;

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
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

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Partner)) return false;

        Partner partner = (Partner) o;

        return new EqualsBuilder()
                .append(partnerId, partner.partnerId)
                .append(getName(), partner.getName())
                .append(getSurname(), partner.getSurname())
                .append(getEnumPartnerType(), partner.getEnumPartnerType())
                .append(team, partner.team)
                .append(getSkills(), partner.getSkills())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(partnerId)
                .append(getName())
                .append(getSurname())
                .append(getEnumPartnerType())
                .append(team)
                .append(getSkills())
                .toHashCode();
    }


}
