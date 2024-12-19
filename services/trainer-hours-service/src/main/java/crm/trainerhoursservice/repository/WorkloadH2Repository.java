package crm.trainerhoursservice.repository;

import crm.trainerhoursservice.model.entity.dev.TrainingHour;
import crm.trainerhoursservice.model.entity.dev.TrainingMonth;
import crm.trainerhoursservice.model.entity.dev.TrainingYear;
import crm.trainerhoursservice.model.entity.prod.MonthlySummary;
import crm.trainerhoursservice.model.entity.prod.WorkloadSummary;
import crm.trainerhoursservice.model.entity.prod.YearlySummary;
import crm.trainerhoursservice.repository.local.TrainerHourRepository;
import crm.trainerhoursservice.repository.local.TrainingMonthRepository;
import crm.trainerhoursservice.repository.local.TrainingYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Profile(value = "dev")
@Component("workloadH2Repository")
public class WorkloadH2Repository implements WorkloadRepository{

    private final TrainerHourRepository trainerHourRepository;
    private final TrainingYearRepository trainingYearRepository;
    private final TrainingMonthRepository trainingMonthRepository;

    @Override
    public void save(WorkloadSummary workloadSummary) {
        TrainingHour trainingHour = trainerHourRepository.findByTrainerUsername(workloadSummary.getUsername())
                .orElse(null);

        if (trainingHour == null) {
            trainingHour = TrainingHour.builder()
                    .trainerUsername(workloadSummary.getUsername())
                    .firstName(workloadSummary.getFirstName())
                    .lastName(workloadSummary.getLastName())
                    .isActive(workloadSummary.getStatus())
                    .build();
        }

        List<YearlySummary> workloadSummaryYears = workloadSummary.getYears();
        List<TrainingYear> trainingYears = new ArrayList<>();

        for (YearlySummary year : workloadSummaryYears) {
            TrainingYear trainingYear = trainingYearRepository.findByYear(year.getYear())
                    .orElse(null);

            if (trainingYear == null) {
                trainingYear = TrainingYear.builder()
                        .year(year.getYear())
                        .totalDuration(year.getTotalDuration())
                        .build();
            } else {
                Double totalDuration = trainingYear.getTotalDuration();
                trainingYear.setTotalDuration(totalDuration + year.getTotalDuration() - totalDuration);
            }
            trainingYear.setTrainingHour(trainingHour);

            List<TrainingMonth> trainingMonths = new ArrayList<>();
            List<MonthlySummary> months = year.getMonths();

            for (MonthlySummary month : months) {
                TrainingMonth trainingMonth = trainingMonthRepository.findByMonth(month.getMonth())
                                .orElse(null);

                if (trainingMonth == null) {
                    trainingMonth = TrainingMonth.builder()
                            .month(month.getMonth())
                            .durationInHour(month.getTotalDuration())
                            .trainingYear(trainingYear)
                            .build();
                } else {
                    Double durationInHour = trainingMonth.getDurationInHour();
                    trainingMonth.setDurationInHour(durationInHour + month.getTotalDuration() - durationInHour);
                }
                trainingMonths.add(trainingMonth);
                trainingMonth.setTrainingYear(trainingYear);
            }

            trainingYear.setMonths(trainingMonths);
            trainingYears.add(trainingYear);

            trainingHour.setTrainingYears(trainingYears);
        }


        trainerHourRepository.save(trainingHour);
    }

    @Override
    public Optional<WorkloadSummary> findByUsername(String username) {
        TrainingHour trainingHour = trainerHourRepository.findByTrainerUsername(username)
                .orElse(null);

        if (trainingHour == null) {
            return Optional.empty();
        }

        List<TrainingYear> trainingYears = trainingHour.getTrainingYears();

        List<YearlySummary> yearlySummaries = new ArrayList<>();

        for (TrainingYear trainingYear : trainingYears) {
            List<MonthlySummary> monthlySummaries = new ArrayList<>();
            List<TrainingMonth> months = trainingYear.getMonths();
            for (TrainingMonth month : months) {
                monthlySummaries.add(new MonthlySummary(month.getMonth(), month.getDurationInHour()));
            }
            yearlySummaries.add(new YearlySummary(trainingYear.getYear(), trainingYear.getTotalDuration(), monthlySummaries));
        }

        return Optional.of(
                WorkloadSummary.builder()
                        .username(trainingHour.getTrainerUsername())
                        .firstName(trainingHour.getFirstName())
                        .lastName(trainingHour.getLastName())
                        .status(trainingHour.isActive())
                        .years(yearlySummaries)
                        .build()
        );
    }
}
