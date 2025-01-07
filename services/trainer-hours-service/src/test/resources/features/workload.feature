Feature: Handling trainer workload

  Scenario: Process new workload
    Given workload with username "iman.gadzhi", firstname "Iman", lastname "Gadzhi", status "true", date "2024-12-23", duration 5.0
    When send post request to "/api/v1/workload" to process
    Then receive status code 200

  Scenario: Generate summary for trainer workload
    When username of trainer "iman.gadzhi" send get request to "/api/v1/workload/summary/iman.gadzhi" to get summary
    Then get response code of 200 which is successful
    Then check username "iman.gadzhi" matches the request, and make sure getting result
