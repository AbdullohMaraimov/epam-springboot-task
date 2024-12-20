package crm.trainerhoursservice.model.entity.prod;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "workload_summary")
@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(name = "user_name_index", def = "{'username': 1, 'firstName': 1, 'lastName': 1}")
public class WorkloadSummary {
    @Id
    private String id;
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
