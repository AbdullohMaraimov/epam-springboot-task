package crm.trainerhoursservice.util;

import crm.trainerhoursservice.model.entity.prod.WorkloadSummary;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import crm.trainerhoursservice.repository.prod.WorkloadSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@Profile("prod")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final WorkloadSummaryRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/initial.json");
            List<WorkloadSummary> workloadSummaries = objectMapper.readValue(inputStream, new TypeReference<>() {});

            repository.deleteAll();

            repository.saveAll(workloadSummaries);

            System.out.println("Data successfully loaded into MongoDB!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load data into MongoDB.");
        }
    }
}
