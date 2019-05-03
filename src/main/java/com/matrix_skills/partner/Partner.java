package com.matrix_skills.partner;

import com.matrix_skills.Skill.Skill;
import com.matrix_skills.team.Team;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "partner")
@Data
public class Partner {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
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



}
