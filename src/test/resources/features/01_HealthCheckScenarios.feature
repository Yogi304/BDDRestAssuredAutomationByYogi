# This feature file validates that the API service is operational.
@HealthCheck 
Feature: Service Health Check Feature

  As a client of the API,
  I want to check the health status of the service,
  So that I can ensure it is operational before running other tests.
	
	@P0
  Scenario: Validating the service is up and running
    When the user sends a GET request to the health check endpoint
    Then the status code should be 200
    And the response body should indicate the status is 'up'
   	And the response should match the 'GetHealth.json' schema
 