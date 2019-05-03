package com.matrix_skills.team;

import com.matrix_skills.partner.Partner;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "team")
@Data
@NoArgsConstructor
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

    public Team(String teamName) {
        this.teamName = teamName;
    }
}
