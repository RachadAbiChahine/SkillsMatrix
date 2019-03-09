package com.matrix_skills.team;

import com.matrix_skills.partner.Partner;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long teamId;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "team")
    private List<Partner> partners;

    public Team() {
    }

    public Team(String teamName) {
        this.teamName = teamName;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Team)) return false;

        Team team = (Team) o;

        return new EqualsBuilder()
                .append(getTeamId(), team.getTeamId())
                .append(getTeamName(), team.getTeamName())
                .isEquals();
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getTeamId())
                .append(getTeamName())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
