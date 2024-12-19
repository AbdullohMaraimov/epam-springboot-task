package crm.trainerhoursservice.model.entity.prod;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Month;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySummary {
    @Indexed
    private Month month;
    private Double totalDuration;
}
