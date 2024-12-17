package crm.trainerhoursservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
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
