package th.project.enterprise.Entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "esteps")
public class Steps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stepsID;

    private int steps_number;
    private String stringDate;
    private LocalDate date;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

}
