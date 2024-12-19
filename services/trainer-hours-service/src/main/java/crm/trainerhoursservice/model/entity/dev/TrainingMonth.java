package crm.trainerhoursservice.model.entity.dev;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Month;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TrainingMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "training_month")
    private Month month;

    private Double durationInHour;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private TrainingYear trainingYear;
}
