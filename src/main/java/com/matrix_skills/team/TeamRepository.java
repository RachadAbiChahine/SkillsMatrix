package com.matrix_skills.team;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Transactional
    List<Team> deleteByTeamName(String teamName);

    Team findFirstByTeamNameIgnoreCase(String teamName);


    Page<Team> findAll(Pageable pageable);


}
