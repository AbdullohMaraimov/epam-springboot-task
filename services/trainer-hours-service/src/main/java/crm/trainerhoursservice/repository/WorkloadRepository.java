package crm.trainerhoursservice.repository;

import crm.trainerhoursservice.model.entity.prod.WorkloadSummary;

import java.util.Optional;


public interface WorkloadRepository {

    void save(WorkloadSummary workloadSummary);

    Optional<WorkloadSummary> findByUsername(String username);

}
