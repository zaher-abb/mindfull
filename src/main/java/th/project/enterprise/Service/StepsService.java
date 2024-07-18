package th.project.enterprise.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.project.enterprise.Entity.RankDTO;
import th.project.enterprise.Entity.Steps;
import th.project.enterprise.Entity.StepsSummaryDTO;
import th.project.enterprise.Repository.StepsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class StepsService {

    @Autowired
    private StepsRepository stepsRepository;

    public void addNewSteps(Steps steps) {
        stepsRepository.save(steps);
    }

    public List<RankDTO> getStepsSumByTeam() {
        return stepsRepository.getStepsSumByTeam();
    }

    public List<RankDTO> getAllMemberStepsSum() {
        return stepsRepository.getAllMemberStepsSum();
    }

    public StepsSummaryDTO getStepsSummaryForUser(Long userId) {
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        LocalDate oneWeekAgo = now.minusWeeks(1);
        LocalDate oneMonthAgo = now.minusMonths(1);
        Long dailyStepsSum = (Optional.ofNullable(stepsRepository.getStepsSumForDay(userId, yesterday)).orElse(0L) / 300000) * 100;
        Long dailyStepsSumPercentage = Math.round(((double) dailyStepsSum / 300000) * 100);

        Long weeklyStepsSum = Optional.ofNullable(stepsRepository.getStepsSumForPeriod(userId, oneWeekAgo, now)).orElse(0L);
        Long weeklyStepsPercentage = Math.round(((double) weeklyStepsSum / 300000) * 100);

        Long monthlyStepsSum = Optional.ofNullable(stepsRepository.getStepsSumForPeriod(userId, oneMonthAgo, now)).orElse(0L);

        Long monthStepsPercentage = Math.round(((double) monthlyStepsSum / 300000) * 100);

        return new StepsSummaryDTO(dailyStepsSum,weeklyStepsSum,monthlyStepsSum ,dailyStepsSumPercentage ,weeklyStepsPercentage, monthStepsPercentage);
    }

}
