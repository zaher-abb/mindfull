package th.project.enterprise.Service;

import th.project.enterprise.Entity.*;
import th.project.enterprise.Repository.UserRepoistory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepoistory userRepoistory;

//    @Autowired
//    private TeamRepository teamRepository;
//


    public void creatUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepoistory.save(user);
    }


    public User findByEmail(String email) {
        return userRepoistory.getUserByEmail(email);
    }

    public boolean isUserPresent(String email) {
        User user = userRepoistory.getUserByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }


    public int getNumberOfStepsByDay(long userId, LocalDate dateStr) {

        return userRepoistory.getUserStepsSumByDate(userId, dateStr);
    }

    public int getNumberOfStepsByDifference(long userId, LocalDate startDate, LocalDate endDate) {

        return userRepoistory.getUSerStepsByDifference(userId, startDate, endDate);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepoistory.getUserByEmail(s);
        UserDetail userDetails;
        if (user == null) {
            throw new UsernameNotFoundException("user not exits with this name");
        }
        return new UserDetail(user);
    }

    public List<User> getAllUsersWhoDoesNotSubmitSteps(LocalDate date){
        return userRepoistory.findAllUsersWhoDoesNotSubmitStepsFromMoreThanOneDay(date);
    }


}

