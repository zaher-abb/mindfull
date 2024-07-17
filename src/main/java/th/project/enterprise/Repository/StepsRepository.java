package th.project.enterprise.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import th.project.enterprise.Entity.StepsSummaryDTO;
import th.project.enterprise.Entity.RankDTO;
import th.project.enterprise.Entity.Steps;

import java.time.LocalDate;
import java.util.List;

public interface StepsRepository extends CrudRepository<Steps, Long> {

//    @Query("SELECT SUM(s.steps_number) as su, t.teamName " +
//            "FROM Steps s " +
//            "JOIN s.user u " +
//            "JOIN u.team t " +
//            "GROUP BY t.teamName order by su desc ")
//    List<RankDTO> getStepsSumByTeam();

    @Query("SELECT new th.project.enterprise.Entity.RankDTO(SUM(s.steps_number), t.teamName) " +
            "FROM Steps s " +
            "LEFT JOIN s.user u " +
            "LEFT JOIN u.team t " +
            "GROUP BY t.teamName " +
            "ORDER BY SUM(s.steps_number) DESC")
    List<RankDTO> getStepsSumByTeam();

    @Query("SELECT new th.project.enterprise.Entity.RankDTO(u.firstName, u.lastName, SUM(s.steps_number), u.Email, u.teamName)" +
            "FROM Steps s " +
            "JOIN s.user u " +
            " GROUP BY u.firstName, u.lastName , u.Email , u.teamName "+
            " order by SUM(s.steps_number) DESC")
   List<RankDTO> getAllMemberStepsSum();

    @Query("SELECT SUM(s.steps_number) FROM Steps s WHERE s.user.id = :userId AND s.date = :date")
    Long getStepsSumForDay(Long userId, LocalDate date);

    @Query("SELECT SUM(s.steps_number) FROM Steps s WHERE s.user.id = :userId AND s.date >= :startDate AND s.date < :endDate")
    Long getStepsSumForPeriod(Long userId, LocalDate startDate, LocalDate endDate);


}













