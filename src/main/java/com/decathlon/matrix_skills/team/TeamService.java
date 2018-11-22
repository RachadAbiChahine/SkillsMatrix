package com.decathlon.matrix_skills.team;

import com.decathlon.matrix_skills.team.dto.TeamDTO;
import com.decathlon.matrix_skills.team.errors.TeamAlreadyExistException;
import com.decathlon.matrix_skills.team.errors.TeamNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class TeamService {


    private TeamRepository teamRepository;
    private ModelMapper modelMapper;

    @Autowired
    public TeamService(TeamRepository teamRepository, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    public TeamDTO addTeam(String teamName) throws TeamAlreadyExistException {
        Team team;
        team = teamRepository.findFirstByTeamNameIgnoreCase(teamName);
        Team savedTeam;
        if (team == null) {
            team = new Team(teamName);
            savedTeam = teamRepository.save(team);
        } else {
            throw new TeamAlreadyExistException();
        }
        TeamDTO teamDTO = modelMapper.map(savedTeam, TeamDTO.class);
        return teamDTO;
    }

    public List<TeamDTO> delete(String teamName) throws TeamNotFoundException {
        List<Team> teams;
        teams = teamRepository.deleteByTeamName(teamName);
        if (teams == null || teams.size() == 0) {
            throw new TeamNotFoundException();
        }
        return teams.stream().map(TeamDTO::new).collect(Collectors.toList());

    }

    /**
     * returns Team searched by name
     *
     * @param teamName teamName is case insensitive
     * @return {@link TeamDTO}
     * @throws TeamNotFoundException
     */
    public TeamDTO getTeam(String teamName) throws TeamNotFoundException {
        Team team =teamRepository.findFirstByTeamNameIgnoreCase(teamName);
        if (team == null) {
            throw new TeamNotFoundException();
        }
        return modelMapper.map(team,TeamDTO.class);
    }

    //TODO change it to Page
    public List<TeamDTO> getAllTeams() throws TeamNotFoundException {
        List<Team> teams = teamRepository.findAll();
        ;
        if (teams == null||teams.size()==0) {
            throw new TeamNotFoundException();
        } else {
            return teams.stream().map(TeamDTO::new).collect(Collectors.toList());
        }

    }
}
