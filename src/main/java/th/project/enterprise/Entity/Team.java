package th.project.enterprise.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "eteam")
public class Team {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String teamName;

    public Team() {

    }
}
