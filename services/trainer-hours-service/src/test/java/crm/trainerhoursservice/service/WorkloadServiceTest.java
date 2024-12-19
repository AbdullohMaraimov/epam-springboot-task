//package crm.trainerhoursservice.service;
//
//import crm.trainerhoursservice.model.constant.ActionType;
//import crm.trainerhoursservice.model.dto.TrainerWorkload;
//import crm.trainerhoursservice.model.entity.prod.MonthlySummary;
//import crm.trainerhoursservice.model.entity.prod.WorkloadSummary;
//import crm.trainerhoursservice.model.entity.prod.YearlySummary;
//import crm.trainerhoursservice.repository.prod.WorkloadSummaryRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class WorkloadServiceTest {
//
//    private WorkloadSummaryRepository workloadRepository;
//    private WorkloadService workloadService;
//
//    @BeforeEach
//    void setUp() {
//        workloadRepository = mock(WorkloadSummaryRepository.class);
//        workloadService = new WorkloadService(workloadRepository);
//    }
//
//    @Test
//    void handleWorkload_NewYearAndMonth_CreatesSummary() {
//        TrainerWorkload workload = new TrainerWorkload(
//                "trainer1", "Ali", "Vali", true,
//                LocalDate.of(2024, 6, 15), 5, ActionType.ADD
//        );
//
//        when(workloadRepository.findByUsername("trainer1")).thenReturn(Optional.empty());
//
//        workloadService.handleWorkload(workload);
//
//        ArgumentCaptor<WorkloadSummary> captor = ArgumentCaptor.forClass(WorkloadSummary.class);
//        verify(workloadRepository).save(captor.capture());
//
//        WorkloadSummary savedSummary = captor.getValue();
//
//        assertEquals("trainer1", savedSummary.getUsername());
//        assertEquals(1, savedSummary.getYears().size());
//        YearlySummary yearlySummary = savedSummary.getYears().getFirst();
//        assertEquals(2024, yearlySummary.getYear());
//        assertEquals(5, yearlySummary.getTotalDuration());
//        assertEquals(1, yearlySummary.getMonths().size());
//
//        MonthlySummary monthlySummary = yearlySummary.getMonths().getFirst();
//        assertEquals(6, monthlySummary.getMonth().getValue());
//        assertEquals(5, monthlySummary.getTotalDuration());
//    }
//
//    @Test
//    void handleWorkload_ExistingYearButNewMonth_AddsNewMonth() {
//        MonthlySummary existingMonth = MonthlySummary.builder()
//                .month(LocalDate.of(2024, 5, 1).getMonth())
//                .totalDuration(8.0)
//                .build();
//
//        YearlySummary existingYear = YearlySummary.builder()
//                .year(2024)
//                .totalDuration(8.0)
//                .months(new ArrayList<>(List.of(existingMonth)))
//                .build();
//
//        WorkloadSummary existingSummary = WorkloadSummary.builder()
//                .username("trainer1")
//                .firstName("Ali")
//                .lastName("Vali")
//                .status(true)
//                .years(List.of(existingYear))
//                .build();
//
//        TrainerWorkload workload = new TrainerWorkload(
//                "trainer1", "Ali", "Vali", true,
//                LocalDate.of(2024, 6, 10), 5, ActionType.ADD
//        );
//
//        when(workloadRepository.findByUsername("trainer1")).thenReturn(Optional.of(existingSummary));
//
//        workloadService.handleWorkload(workload);
//
//        ArgumentCaptor<WorkloadSummary> captor = ArgumentCaptor.forClass(WorkloadSummary.class);
//        verify(workloadRepository).save(captor.capture());
//
//        WorkloadSummary updatedSummary = captor.getValue();
//
//        assertEquals(2024, updatedSummary.getYears().getFirst().getYear());
//        assertEquals(13, updatedSummary.getYears().getFirst().getTotalDuration());
//        assertEquals(2, updatedSummary.getYears().getFirst().getMonths().size());
//    }
//
//    @Test
//    void handleWorkload_ExistingYearAndMonth_UpdatesDuration() {
//        MonthlySummary existingMonth = MonthlySummary.builder()
//                .month(LocalDate.of(2024, 6, 1).getMonth())
//                .totalDuration(5.0)
//                .build();
//
//        YearlySummary existingYear = YearlySummary.builder()
//                .year(2024)
//                .totalDuration(5.0)
//                .months(List.of(existingMonth))
//                .build();
//
//        WorkloadSummary existingSummary = WorkloadSummary.builder()
//                .username("trainer1")
//                .firstName("Ali")
//                .lastName("Vali")
//                .status(true)
//                .years(List.of(existingYear))
//                .build();
//
//        TrainerWorkload workload = new TrainerWorkload(
//                "trainer1", "Ali", "Vali", true,
//                LocalDate.of(2024, 6, 15), 3, ActionType.ADD
//        );
//
//        when(workloadRepository.findByUsername("trainer1")).thenReturn(Optional.of(existingSummary));
//
//        workloadService.handleWorkload(workload);
//
//        ArgumentCaptor<WorkloadSummary> captor = ArgumentCaptor.forClass(WorkloadSummary.class);
//        verify(workloadRepository).save(captor.capture());
//
//        WorkloadSummary updatedSummary = captor.getValue();
//
//        assertEquals(8, updatedSummary.getYears().getFirst().getTotalDuration());
//        assertEquals(8, updatedSummary.getYears().getFirst().getMonths().getFirst().getTotalDuration());
//    }
//
//    @Test
//    void summarize_ExistingTrainer_ReturnsSummary() {
//        WorkloadSummary summary = WorkloadSummary.builder()
//                .username("trainer1")
//                .firstName("Ali")
//                .lastName("Vali")
//                .status(true)
//                .build();
//
//        when(workloadRepository.findByUsername("trainer1")).thenReturn(Optional.of(summary));
//
//        WorkloadSummary result = workloadService.summarize("trainer1");
//
//        assertNotNull(result);
//        assertEquals("trainer1", result.getUsername());
//        verify(workloadRepository).findByUsername("trainer1");
//    }
//
//    @Test
//    void summarize_NonExistingTrainer_ReturnsNull() {
//        when(workloadRepository.findByUsername("trainer1")).thenReturn(Optional.empty());
//
//        WorkloadSummary result = workloadService.summarize("trainer1");
//
//        assertNull(result);
//        verify(workloadRepository).findByUsername("trainer1");
//    }
//}
