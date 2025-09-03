# Author: Yogeeswara G
# Date: 2025-09-02
# This feature file covers the full CRUD (Create, Read, Update, Delete)
# functionality for the book management API endpoints.
@BookManagement
Feature: Book Management API

  @P3
  Scenario: Successfully create a new book
    When a POST request is sent to the create book endpoint with valid details
    Then the response status code should be 200
    And the new book should be present in the list of all books
    And the create book response should match the "GetBook.json" schema

  @P3
  Scenario: Successfully retrieve a specific book by its ID
    Given a new book is created
    When a GET request is sent to the get book endpoint with the book's ID
    Then the response status code should be 200
    And the response body should contain the correct book details
    And the book retrival response should match the "GetBook.json" schema
    
      @P3
  Scenario: Successfully retrieve all books
    When a GET request is sent to the get all books endpoint 
    Then the response status code should be 200
    And the response body should contain the List of all books
    And the get all books retrival response should match the "GetAllBooks.json" schema

  @P3
  Scenario: Successfully update an existing book
    Given a new book is created
    When a PUT request is sent to the update book endpoint with new details
    Then the response status code should be 200
    And the book details should be updated when retrieved again
    And the book updation response should match the "GetBook.json" schema

  @P3
  Scenario: Successfully delete a book
    Given a new book is created
    When a DELETE request is sent to the delete book endpoint with the book's ID
    Then the response status code should be 200
    And the response body should contain the success message "Book deleted successfully"
    And the book should no longer be found

  @P3
  Scenario Outline: Attempting to access book endpoints with invalid authentication
    When a GET request is sent to the get all books endpoint with an "<auth_type>" token
    Then the response status code should be 403
    And the response body should contain the error detail "<error_message>"

    Examples: 
      | auth_type | error_message                  | description       |
      | invalid   | Invalid token or expired token | Incorrect token   |
      | expired   | Invalid token or expired token | Expired token     |
      | missing   | Not authenticated              | No token provided |
   @P3
  Scenario Outline: Attempting to access Get book by BookID endpoint with invalid authentication
    When a GET request is sent to the book endpoint to retrieve book with valid bookId and with an "<auth_type>" token
    Then the response status code should be 403
    And the response body should contain the error detail "<error_message>"

    Examples: 
      | auth_type | error_message                  | description       |
      | invalid   | Invalid token or expired token | Incorrect token   |
      | expired   | Invalid token or expired token | Expired token     |
      | missing   | Invalid token or expired token | No token provided |

  @P3
  Scenario: Attempting to retrieve a book with a non-existent ID
    When a GET request is sent to the get book endpoint with a non-existent ID "12345"
    Then the response status code should be 404
    And the response body should contain the error detail "Book not found"
    And the book wih non existing Id retrieval response should match the "Detail.json" schema

  @P3
  Scenario: Attempting to delete a book that has already been deleted
    Given a new book is created
    And the book is successfully deleted
    When another DELETE request is sent to the delete book endpoint with the same book's ID
    Then the response status code should be 404
    And the response body should contain the error detail "Book not found"
    And the non existing book deletion response should match the "Detail.json" schema
