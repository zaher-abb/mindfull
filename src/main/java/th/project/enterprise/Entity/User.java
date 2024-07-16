package th.project.enterprise.Entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "euser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;


    private String teamName;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;


    private String roles;



    @NotNull
    @Size(min = 4)
    private String password;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String profilePictureUrl;


    @NotNull
    @Column(nullable = false, unique = true)
    private String Email;

    public User() {
        setRoles("USER");
    }

    private long stepsTarget;

    public String getFullname() {
        return this.getFirstName() + " " + this.getLastName();
    }


}