package crm.trainerhoursservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Month;

@Builder
@Getter
@Setter
@ToString
public class MonthlySummary {
    @Indexed
    private Month month;
    private Double totalDuration;
}
