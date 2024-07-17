package th.project.enterprise.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RankDTO {

    private String teamName;
    private long stepsSum;
    private int teamMemberCount;
    private String memberName;
    private String memberEmail;
    private  User user;
    private int rank;
    private boolean isVisuable;
    private String stepsSumFormatted;
    public RankDTO(long stepsSum,String teamName){
        this.teamName=teamName;
        this.stepsSum = stepsSum;

        this.isVisuable= false;
    }
    public RankDTO(String firstName, String lastName,  long stepsSum, String memberEmail, String teamName){
        this.memberName = firstName + " " + lastName;
        this.teamName=teamName;
        this.stepsSum = stepsSum;
        this.memberEmail = memberEmail;
        this.isVisuable= false;
    }
//    public RankDTO(int stepsSum,Team teamName){
//        this.teamName=teamName;
//        this.stepsSum = stepsSum;
//    }
}
