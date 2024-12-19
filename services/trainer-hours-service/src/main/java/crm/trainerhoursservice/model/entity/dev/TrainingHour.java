package crm.trainerhoursservice.model.entity.dev;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "training_hour")
public class TrainingHour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String trainerUsername;

    private String firstName;

    private String lastName;

    private boolean isActive = true;

    @OneToMany(mappedBy = "trainingHour", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TrainingYear> trainingYears = new ArrayList<>();


    public void addTrainingYear(TrainingYear year) {
        if (trainingYears == null) {
            trainingYears = new ArrayList<>();
        }
        trainingYears.add(year);
        year.setTrainingHour(this);
    }
}

