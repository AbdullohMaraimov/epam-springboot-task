package crm.trainerhoursservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "workload_summary")
@Builder
@ToString
@Getter
@Setter
public class WorkloadSummary {
    @Id
    private String id;
    @Indexed
    private String username;
    private String firstName;
    private String lastName;
    private Boolean status;
    private List<YearlySummary> years = new ArrayList<>();

    public void addTrainingYear(YearlySummary year) {
        if (years == null) {
            years = new ArrayList<>();
        }
        years.add(year);
    }
}
