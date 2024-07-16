package th.project.enterprise.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.project.enterprise.Entity.Team;
import th.project.enterprise.Repository.TeamRepository;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    public void addNewTeam(String team) {
        Team newTeam = new Team();
        newTeam.setTeamName(team);
        teamRepository.save(newTeam);

    }

    public boolean chechTeamIsAlreadyExisted(String team) {
        if (teamRepository.existsByTeamName(team)) {
            return true;
        } else {
            return false;
        }
    }

    public Team getTeamByName(String team) {
        return teamRepository.findByTeamName(team);
    }

    public int getAllUsersInTeamSteps(Team team){
        return teamRepository.getSumStepsByTeamAsc(team);

    }


}
