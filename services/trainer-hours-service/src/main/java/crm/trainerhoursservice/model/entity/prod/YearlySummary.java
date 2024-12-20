package crm.trainerhoursservice.model.entity.prod;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class YearlySummary {
    @Indexed
    private Integer year;
    private Double totalDuration;
    private List<MonthlySummary> months = new ArrayList<>();

    public void addMonth(MonthlySummary month) {
        if (months == null) {
            months = new ArrayList<>();
        }
        months.add(month);
    }
}
