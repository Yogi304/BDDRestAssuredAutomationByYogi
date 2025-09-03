package com.api.bookstore.stepdefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;


import com.api.bookstore.assertions.WrappedAssert;
import com.api.bookstore.endpoints.EndPoints;
import com.api.bookstore.logs.WrappedReportLogger;
import com.api.bookstore.requestBuilder.ApiClient;
import com.api.bookstore.requestBuilder.RequestBuilder;
import com.api.bookstore.responsepojos.GetHealth;
import com.api.bookstore.testcontext.TestContext;

public class HealthCheckSteps {

    private Response response;

    @When("the user sends a GET request to the health check endpoint")
    public void the_user_sends_a_get_request_to_the_health_check_endpoint() {
        WrappedReportLogger.trace("PreRequiste- Validating if Server is up and Running...");
      response = ApiClient.get(RequestBuilder.defaultSpec(), EndPoints.HEALTH);
      TestContext.setResponse(response);
    }
    
    @And("the response body should indicate the status is 'up'")
    public void the_response_body_should_indicate_the_status_is_up() {
        WrappedReportLogger.trace("Validating the status in the response body...");
        GetHealth getHealthResponse = TestContext.getResponse().as(GetHealth.class);
        WrappedAssert.assertEquals(getHealthResponse.getStatus(), "up", "Validating status value in response body");
        WrappedReportLogger.trace("PreRequiste- Validated Server is up and Running!!!");
    }
    @Then("the status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        WrappedReportLogger.trace("Asserting that the response status code is " + expectedStatusCode);
        WrappedAssert.assertEquals(TestContext.getResponse().getStatusCode(), expectedStatusCode, "Validating response status code.");
    }
    @And("the response should match the {string} schema")
    public void the_response_should_match_the_schema(String schemaFileName) {
        WrappedReportLogger.trace("Validating health check response against schema: " + schemaFileName);
        ApiClient.validateResponseAgainstSchema(TestContext.getResponse(), EndPoints.HEALTH,schemaFileName);
    }
  
}
