package th.project.enterprise.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import th.project.enterprise.Entity.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {


        boolean existsByTeamName(String teamName);

        Team findByTeamName(String teamName);
        @Query("select SUM(s.steps_number) from Steps s , User u ,Team t where u.team =:team ORDER BY s.steps_number ASC")
        int getSumStepsByTeamAsc(Team team);

//        @Query("SELECT u FROM User u , Team t where  U.team.id = t.id and t.teamName LIKE %:team%")
//        List<User> getAllUserInTeam(Team team);

}
