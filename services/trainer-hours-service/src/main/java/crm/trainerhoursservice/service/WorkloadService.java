package crm.trainerhoursservice.service;

import crm.trainerhoursservice.model.dto.TrainerWorkload;
import crm.trainerhoursservice.model.entity.prod.MonthlySummary;
import crm.trainerhoursservice.model.entity.prod.WorkloadSummary;
import crm.trainerhoursservice.model.entity.prod.YearlySummary;

import crm.trainerhoursservice.repository.WorkloadRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
public class WorkloadService {

    private final WorkloadRepository workloadRepository;

    public void handleWorkload(TrainerWorkload workload) {
        log.info("Handling workload for trainer: {}", workload.username());
        WorkloadSummary workloadSummary = workloadRepository.findByUsername(workload.username())
                .orElse(
                        WorkloadSummary.builder()
                                .username(workload.username())
                                .firstName(workload.firstName())
                                .lastName(workload.lastName())
                                .status(workload.isActive())
                                .years(new ArrayList<>())
                                .build()
                );

        log.info("From repo -> service: {}", workloadSummary);

        YearlySummary yearlySummary = null;
        for (YearlySummary year : workloadSummary.getYears()) {
            if  (year.getYear() == workload.trainingDate().getYear()) {
                yearlySummary = year;
                log.info("Found existing yearly summary for year: {}", year.getYear());
                break;
            }
        }

        if (yearlySummary == null) {
            log.info("No yearly summary found for year: {}. Creating a new one.", workload.trainingDate().getYear());
            MonthlySummary month = MonthlySummary.builder()
                    .month(workload.trainingDate().getMonth())
                    .totalDuration(workload.trainingDuration())
                    .build();

            yearlySummary = YearlySummary.builder()
                    .year(workload.trainingDate().getYear())
                    .totalDuration(workload.trainingDuration())
                    .months(new ArrayList<>())
                    .build();

            yearlySummary.addMonth(month);
            workloadSummary.addTrainingYear(yearlySummary);

            log.info("New yearly summary and workload summary saved : {}", workload);
            workloadRepository.save(workloadSummary);
            log.info("New yearly summary and workload summary saved for trainer: {}", workload.username());
            return;
        }

        MonthlySummary monthlySummary = null;
        for (MonthlySummary month : yearlySummary.getMonths()) {
            if (month.getMonth() == workload.trainingDate().getMonth()) {
                monthlySummary = month;
                log.info("Found existing monthly summary for month: {}", month.getMonth());
                break;
            }
        }

        if (monthlySummary == null) {
            log.info("No monthly summary found for month: {}. Creating a new one.", workload.trainingDate().getMonth());
            MonthlySummary month = MonthlySummary.builder()
                    .month(workload.trainingDate().getMonth())
                    .totalDuration(workload.trainingDuration())
                    .build();

            yearlySummary.setTotalDuration(yearlySummary.getTotalDuration() + workload.trainingDuration());
            yearlySummary.addMonth(month);
            workloadRepository.save(workloadSummary);
            log.info("New monthly summary and updated workload summary saved for trainer: {}", workload.username());
            return;
        }

        monthlySummary.setTotalDuration(monthlySummary.getTotalDuration() + workload.trainingDuration());
        yearlySummary.setTotalDuration(yearlySummary.getTotalDuration() + workload.trainingDuration());
        workloadRepository.save(workloadSummary);
        log.info("Workload summary saved: {}", workloadSummary);
    }


    public WorkloadSummary summarize(String trainerUsername) {
        log.info("Summarizing workload for trainer: {}", trainerUsername);
        WorkloadSummary workloadSummary = workloadRepository.findByUsername(trainerUsername)
                .orElse(null);
        log.info("Retrieved from MongoDB: {}", workloadSummary);
        return workloadSummary;
    }
}