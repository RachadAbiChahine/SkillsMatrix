package com.decathlon.matrix_skills.team;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> deleteByTeamName(String teamName);

    Team findFirstByTeamNameIgnoreCase(String teamName);


    Page<Team> findAll(Pageable pageable);


}
