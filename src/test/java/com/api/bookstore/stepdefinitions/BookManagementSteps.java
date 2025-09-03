package com.api.bookstore.stepdefinitions;


import com.api.bookstore.assertions.WrappedAssert;
import com.api.bookstore.endpoints.EndPoints;
import com.api.bookstore.logs.WrappedReportLogger;
import com.api.bookstore.requestBuilder.ApiClient;
import com.api.bookstore.requestBuilder.RequestBuilder;
import com.api.bookstore.requestpojos.BookManagement;
import com.api.bookstore.responsepojos.Detail;
import com.api.bookstore.responsepojos.GetBook;
import com.api.bookstore.testcontext.TestContext;
import com.api.bookstore.utils.DataGenerator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class BookManagementSteps {
    
    public static int newBookId;
	public static int newPublishedYear;
	
	public static Response response;
	
	@Given("a new book is created")
    public void a_new_book_is_created() {
        WrappedReportLogger.trace("Creating a new book as a precondition...");
        BookManagement createBook = new BookManagement();
        TestContext.setNewBookId(DataGenerator.randomID());
        createBook.setId(TestContext.getNewBookId());
        createBook.setName("A Game of Thrones " + TestContext.getNewBookId());
        createBook.setAuthor("George R.R. Martin");
        createBook.setBook_summary("A tale of kings and dragons.");
        createBook.setPublished_year(1996);

        response = ApiClient.post(RequestBuilder.withBodyAndAuthToken(createBook, TestContext.getAuthToken(), null, null), EndPoints.CREATE_BOOK);
        TestContext.setResponse(response);
        WrappedAssert.assertEquals(TestContext.getResponse().getStatusCode(), 200, "Precondition: Book creation SuccessFul.");
        WrappedReportLogger.trace("Book with ID " + TestContext.getNewBookId() + " created successfully.");
    }
    
    @Given("the book is successfully deleted")
    public void the_book_is_successfully_deleted() {
        WrappedReportLogger.trace("Deleting the book as a precondition...");
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("bookId", TestContext.getNewBookId());
        ApiClient.delete(RequestBuilder.withAuthToken(TestContext.getAuthToken(), null, pathParams), EndPoints.DELETE_BOOK);
        WrappedReportLogger.trace("Book with ID " + TestContext.getNewBookId() + " deleted.");
    }


    // ============== WHEN STEPS ==============

    @When("a POST request is sent to the create book endpoint with valid details")
    public void a_post_request_is_sent_to_the_create_book_endpoint_with_valid_details() {
         a_new_book_is_created(); // Reusing the given step for the main action
    }
    
    @When("a GET request is sent to the get book endpoint with the book's ID")
    public void a_get_request_is_sent_to_the_get_book_endpoint_with_the_book_s_id() {
        WrappedReportLogger.trace("Retrieving book with ID: " + TestContext.getNewBookId());
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("bookId", TestContext.getNewBookId());
        response = ApiClient.get(RequestBuilder.withAuthToken(TestContext.getAuthToken(), null, pathParams), EndPoints.GET_BOOK);
        TestContext.setResponse(response);
    }

    @When("a PUT request is sent to the update book endpoint with new details")
    public void a_put_request_is_sent_to_the_update_book_endpoint_with_new_details() {
        WrappedReportLogger.trace("Updating book with ID: " + TestContext.getNewBookId());
        BookManagement updateBook = new BookManagement();
        updateBook.setId(TestContext.getNewBookId());
        updateBook.setName("A Clash of Kings");
        updateBook.setAuthor("Updated Author");
        updateBook.setBook_summary("Updated summary.");
        updateBook.setPublished_year(1998);

        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("bookId", TestContext.getNewBookId());
        response = ApiClient.put(RequestBuilder.withBodyAndAuthToken(updateBook, TestContext.getAuthToken(), null, pathParams), EndPoints.UPDATE_BOOK);
        TestContext.setResponse(response);
    }
    
    @When("a DELETE request is sent to the delete book endpoint with the book's ID")
    public void a_delete_request_is_sent_to_the_delete_book_endpoint_with_the_book_s_id() {
        WrappedReportLogger.trace("Deleting book with ID: " + TestContext.getNewBookId());
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("bookId", TestContext.getNewBookId());
        response = ApiClient.delete(RequestBuilder.withAuthToken(TestContext.getAuthToken(), null, pathParams), EndPoints.DELETE_BOOK);
        TestContext.setResponse(response);
    }

    @When("another DELETE request is sent to the delete book endpoint with the same book's ID")
    public void another_delete_request_is_sent_to_the_delete_book_endpoint_with_the_same_book_s_id() {
        a_delete_request_is_sent_to_the_delete_book_endpoint_with_the_book_s_id();
    }
    
    @When("a GET request is sent to the get all books endpoint with an {string} token")
    public void a_get_request_is_sent_to_the_get_all_books_endpoint_with_an_token(String auth_type) {
        String token;
        switch(auth_type) {
            case "invalid":
                token = "thisIsAnInvalidToken";
                break;
            case "expired":
                 token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaXZhQGdtYWlsIiwiZXhwIjoxNzU1NDk5ODIyfQ.R9Kuds3CU1smTccbs-PUwBfu7b-OFyYl6F7fGUKIquY"; // Example of an expired token
                break;
            case "missing":
            default:
                token = null;
                break;
        }

        WrappedReportLogger.trace("Sending GET /books request with " + auth_type + " token.");
        if (token != null) {
            response = ApiClient.get(RequestBuilder.withAuthToken(token, null, null), EndPoints.GET_ALL_BOOKS);
            TestContext.setResponse(response);
        } else {
             response = ApiClient.get(RequestBuilder.defaultSpec(), EndPoints.GET_ALL_BOOKS);
             TestContext.setResponse(response);
        }
    }
    
    @When("a GET request is sent to the get book endpoint with a non-existent ID {string}")
    public void a_get_request_is_sent_to_the_get_book_endpoint_with_a_non_existent_id(String bookId) {
        WrappedReportLogger.trace("Retrieving book with non-existent ID: " + bookId);
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("bookId", bookId);
        response = ApiClient.get(RequestBuilder.withAuthToken(TestContext.getAuthToken(), null, pathParams), EndPoints.GET_BOOK);
        TestContext.setResponse(response);
    }

    // ============== THEN STEPS ==============

    @And("the new book should be present in the list of all books")
    public void the_new_book_should_be_present_in_the_list_of_all_books() {
        WrappedReportLogger.trace("Verifying new book is in the GET /books list...");
        Response response = ApiClient.get(RequestBuilder.withAuthToken(TestContext.getAuthToken(), null, null), EndPoints.GET_ALL_BOOKS);
        List<GetBook> allBooks = Arrays.asList(response.as(GetBook[].class));
        
        boolean found = allBooks.stream().anyMatch(book -> book.getId() == TestContext.getNewBookId());
        WrappedAssert.assertTrue(found, "Newly created book was not found in the list of all books.");
        WrappedReportLogger.trace("Book found successfully.");
    }
    
    @And("the response body should contain the correct book details")
    public void the_response_body_should_contain_the_correct_book_details() {
        WrappedReportLogger.trace("Verifying book details in response...");
        GetBook getBook = TestContext.getResponse().as(GetBook.class);
        WrappedAssert.assertEquals(getBook.getId(), TestContext.getNewBookId(), "Validating book ID in response.");
    }
    
    @And("the book details should be updated when retrieved again")
    public void the_book_s_details_should_be_updated_when_retrieved_again() {
         a_get_request_is_sent_to_the_get_book_endpoint_with_the_book_s_id(); // Make the GET call again
         GetBook getBook = TestContext.getResponse().as(GetBook.class);
         WrappedAssert.assertEquals(getBook.getAuthor(), "Updated Author", "Validating updated author name.");
         WrappedAssert.assertEquals(getBook.getName(), "A Clash of Kings", "Validating updated book name.");
         WrappedReportLogger.trace("Verified book details were updated successfully.");
    }
    
    @And("the book should no longer be found")
    public void the_book_should_no_longer_be_found() {
        a_get_request_is_sent_to_the_get_book_endpoint_with_the_book_s_id(); // Try to get the deleted book
        WrappedAssert.assertEquals(TestContext.getResponse().getStatusCode(), 404, "Verifying deleted book returns 404.");
        Detail detail = TestContext.getResponse().as(Detail.class);
        WrappedAssert.assertEquals(detail.getDetail(), "Book not found", "Verifying 'Book not found' message for deleted book.");
        WrappedReportLogger.trace("Verified book is no longer accessible.");
    }

    @And("the create book response should match the {string} schema")
    public void the_create_book_response_should_match_the_schema(String schemaFileName) {
        WrappedReportLogger.trace("Validating create book response against schema: " + schemaFileName);
        ApiClient.validateResponseAgainstSchema(TestContext.getResponse(), EndPoints.GET_ALL_BOOKS,schemaFileName);
    }
    
    @And("the book retrival response should match the {string} schema")
    public void the_book_retrival_response_should_match_the_schema(String schemaFileName) {
        WrappedReportLogger.trace("Validating book retrival response against schema: " + schemaFileName);
        ApiClient.validateResponseAgainstSchema(TestContext.getResponse(), EndPoints.GET_BOOK,schemaFileName);
    }
    
    @And("the book updation response should match the {string} schema")
    public void the_book_updation_response_should_match_the_schema(String schemaFileName) {
        WrappedReportLogger.trace("Validating hbook updation response against schema: " + schemaFileName);
        ApiClient.validateResponseAgainstSchema(TestContext.getResponse(), EndPoints.UPDATE_BOOK,schemaFileName);
    }
    
    
    @And("the book deletion response should match the {string} schema")
    public void the_bookDeletion_response_should_match_the_schema(String schemaFileName) {
        WrappedReportLogger.trace("Validating  book deletion response against schema: " + schemaFileName);
        ApiClient.validateResponseAgainstSchema(TestContext.getResponse(), EndPoints.DELETE_BOOK,schemaFileName);
    }
    
    @And("the book wih non existing Id retrieval response should match the {string} schema")
    public void the_book_withNonExistingID_response_should_match_the_schema(String schemaFileName) {
        WrappedReportLogger.trace("Validating the book wih non existing Id retrieval response against schema: " + schemaFileName);
        ApiClient.validateResponseAgainstSchema(TestContext.getResponse(), EndPoints.GET_BOOK,schemaFileName);
    }
    @And("the non existing book deletion response should match the {string} schema")
    public void the_non_existingBook_bookDeletion_response_should_match_the_schema(String schemaFileName) {
        WrappedReportLogger.trace("Validating  book deletion response against schema: " + schemaFileName);
        ApiClient.validateResponseAgainstSchema(TestContext.getResponse(), EndPoints.DELETE_BOOK,schemaFileName);
    }
    
   


   
}
