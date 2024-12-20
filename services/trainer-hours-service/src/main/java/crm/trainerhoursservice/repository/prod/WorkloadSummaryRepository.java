package crm.trainerhoursservice.repository.prod;

import crm.trainerhoursservice.model.entity.prod.WorkloadSummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("WorkloadSummaryRepository2")
public interface WorkloadSummaryRepository extends MongoRepository<WorkloadSummary, String> {

    Optional<WorkloadSummary> findByUsername(String username);

}
