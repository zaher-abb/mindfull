package th.project.enterprise.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StepsSummaryDTO {
    private long dailyStepsSum;
    private long weeklyStepsSum;
    private long monthlyStepsSum;
    private long dailyStepsSumPercentage;
    private long weeklyStepsSumPercentage;
    private long monthlyStepsSumPercentage;

}