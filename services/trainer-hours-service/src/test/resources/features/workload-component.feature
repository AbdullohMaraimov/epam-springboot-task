Feature: WorkloadService Component Tests

  Scenario: Process workload for a new trainer
    Given a trainer with username "testTrainer", first name "John", last name "Doe", and training duration 5 for 2025-01-15
    When the workload is processed
    Then the workload summary for username "testTrainer" should include training duration PT5M for 2025-01-15

  Scenario: Retrieve summary for a trainer with no workload summary
    Given a trainer with no workload summary for username "emptyTrainer"
    When the summary for trainer username "emptyTrainer" is retrieved
    Then the summary for username "emptyTrainer" should be null
