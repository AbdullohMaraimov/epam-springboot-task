package crm.trainerhoursservice.repository;

import crm.trainerhoursservice.model.entity.prod.WorkloadSummary;
import crm.trainerhoursservice.repository.prod.WorkloadSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Profile(value = "prod")
@Component("workloadMongoRepository")
public class WorkloadMongoRepository implements WorkloadRepository{

    private final WorkloadSummaryRepository workloadSummaryRepository;

    @Override
    public void save(WorkloadSummary workloadSummary) {
        workloadSummaryRepository.save(workloadSummary);
    }

    @Override
    public Optional<WorkloadSummary> findByUsername(String username) {
        return workloadSummaryRepository.findByUsername(username);
    }
}
