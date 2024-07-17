package th.project.enterprise.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.project.enterprise.Entity.RankDTO;
import th.project.enterprise.Entity.Steps;
import th.project.enterprise.Entity.StepsSummaryDTO;
import th.project.enterprise.Repository.StepsRepository;

import java.time.LocalDate;
import java.util.List;


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
        Long dailyStepsSum;
        try {
            dailyStepsSum = stepsRepository.getStepsSumForDay(userId, yesterday);
        } catch (Exception e) {
            dailyStepsSum = 0L;
        }
        Long weeklyStepsSum;
        try {
            weeklyStepsSum = stepsRepository.getStepsSumForPeriod(userId, oneWeekAgo, now);
        } catch (Exception e) {
            weeklyStepsSum = 0L;
        }

        Long monthlyStepsSum;
        try {
            monthlyStepsSum = stepsRepository.getStepsSumForPeriod(userId, oneMonthAgo, now);
        } catch (Exception e) {
            monthlyStepsSum = 0L;
        }
        return new StepsSummaryDTO(dailyStepsSum, weeklyStepsSum, monthlyStepsSum);
    }

}
