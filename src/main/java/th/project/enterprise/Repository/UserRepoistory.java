package th.project.enterprise.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import th.project.enterprise.Entity.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepoistory extends CrudRepository<User, Long> {

    @Query("SELECT m from User m WHERE m.Email=:Email")
    User getUserByEmail(String Email);


    @Query("SELECT SUM(s.steps_number) FROM Steps s WHERE s.user.id= :id AND s.date = :d")
    int getUserStepsSumByDate(long id, LocalDate d);
    @Query("SELECT SUM(s.steps_number) FROM Steps s WHERE s.user.id= :userId AND s.date BETWEEN :startDate AND :endDate")
    int getUSerStepsByDifference(long userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT u FROM User u left join Steps s on u.id = s.user.id WHERE s.date < :date")
    List<User> findAllUsersWhoDoesNotSubmitStepsFromMoreThanOneDay(LocalDate date);

}
