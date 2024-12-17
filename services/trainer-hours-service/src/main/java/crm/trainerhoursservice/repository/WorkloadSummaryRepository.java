package crm.trainerhoursservice.repository;


import crm.trainerhoursservice.model.WorkloadSummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkloadSummaryRepository extends MongoRepository<WorkloadSummary, String> {

    Optional<WorkloadSummary> findByUsername(String username);

}
