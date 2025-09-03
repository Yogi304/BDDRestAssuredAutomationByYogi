
RestAssured BDD Framework for testing Bookstore API 


## How to Run the Tests

### Prerequisites

- Maven 3.8.4+
- TestNG 7.4.1+
- Cucumber 3.4.0+
- Java 17+
- Git
- Any IDE (e.g., IntelliJ IDEA, Eclipse, VS Code)
- Run Book Store API on local machine after reading the Book API's own README.md file

### Steps to Execute

1. Clone the repository:
   ```bash
   git clone https://github.com/Yogi304/BDDRestAssuredAutomationByYogi.git
   cd yogiRestAssuredAutomation
2. set env as QA in config file using, env=QA
3. Run the Scenarios
   ```bash
   mvn clean test
4.  Reports are generated with a time stamped named for easy identification
   cd yogiRestAssuredAutomation/report

## Sample Report
**[Download Sample Report](ReadMeImages/SampleExtetntReport.png)**

## Validation Covered
Validation is handled through the *WrappedAssert* class, which extends TestNG’s built-in assertions with Log4j and ExtentReports integration.

1. Header and body validations are covered within the test cases.
2. Schema validation is done through wrapperAssertJsonSchema using JsonSchemaValidator.matchesJsonSchemaInClasspath
3. Status code validation is done through wrapperAssertEquals
4. Negative test cases are included as well.


## Project Structure Implementation Details

- Each relavent API requests are grouped and  mapped to Cucumber BDD feature and stepdefinitions files with meaningful file names.  
  Example: All user related API requests Like *UserSignUp, Login* are tested in **`UserManagement.feature`** and **`UserManagementsteps.java`**.

- A single feature file can contain **multiple scenarios**.

- **POJO classes** are used for serialization and deserialization of request and response bodies.

#### Request Chaining
With the Help of TestContext.java and cucumber Hooks Mechanism:
- The token generated from a **login request** is reused across subsequent requests.  
- Newly created **username and password** are also reused in other requests.  

#### Logging
- Test cases use **`WrappedReportLogger`** for logging.  
- Logs are displayed on the **console** and also captured in the **Extent Report**.

#### ApiClient
- Handles sending HTTP requests: `GET`, `POST`, `PUT`, `DELETE`.

#### WrapperAssert
- Validates responses against:
  - **Status codes**  
  - **JSON schemas**  
- Internally uses **`TestNG Assertrions and JsonSchemaValidator`** for assertions.

#### RequestBuilder
- Provides reusable request specifications.  
- Supports different combinations such as:
  - With Auth Token  
  - With Body (without Auth Token)  
- Accepts **path parameters** and **query parameters**.

#### Data-Driven Testing
- Implemented using **`Scenario Outline with Examples Table`** to supply multiple input sets for test cases.

#### Assertions
- Standard assertions: `assertEquals`, `assertNotNull`, `assertTrue`.  
- Schema validation (`assertJsonSchema`).

#### Schema Management
- JSON Schemas for expected responses are stored in a **dedicated folder** for better organization.

#### EnvConfigResolver
- The **`EnvConfigResolver`** class dynamically resolves environment-specific configurations:
  - `URL`  
  - `Email`  
  - `Password`  
- Supported environments: **QA**, **DEV**, **PROD**.


## Testing Strategy 
- Using TestContext and Cucumber Hooks for the request chaining was done to share the token, new email, new pwd.

- `**POSTIVE WORKFLOW** health Check up>> creation of new user >> login to get access token >> get all books >> create new Book >> Validating of new book is displayed in the list of all the books >> getting newly created book by ID >> update newly added book >> check this newly added book in list of all the book >> Delete newly added book`

- `**NEGATIVE TESTCASES** Validating creation of new user with existing email, Login using incorrect _SET OF_ credentails, Validating if books are not displayed when invalid token used, Validating if books are not displayed when NO token used, Validating user unable to search a book with incorrect ID, try to twice delete a book, unable to search deleted book, Incorrectly formatted JSON file validation`

- POJO classes used for searlization and desearlization
- in each scenario stepdefinitions Logging was done. this Logs are pushed on to the Console and Extent report
- Negative testcases are covered.
- validation of Body, header, status code, schema is also covered.
-  environment-specific configurations like URL, email and password are resolved dynamically
-  yml file for **gitHub action** has already been added

## List of issues
- When incorrect credentials are provided during login, the server responds with a 400 Bad Request instead of a 401 Unauthorized.
- During the login request validation, I found that the request is processed regardless of the provided user ID.
- If an user tries to retrieve all books with an incorrect token, they receive a 403 Forbidden error instead of a 401 Unauthorized.
- When attempting to add a new book with an existing ID, the server returns a 500 Internal Server Error, indicating it cannot handle duplicate IDs.
- There is currently no functionality to delete users, making test data cleanup impossible.
- since API provided to me was on local machine. I started the application using process builder  
- Whenever I am pushing my code to gitHub, execution is trigerring automtically on **github actions**. using yaml file commands.
 ![Github actions](ReadMeImages/GitHubActionsBuildSuccessImage.png)


## FrameWork structure
```
+---src
|   +---main
|   |   +---java
|   |   |   \---com
|   |   |       \---api
|   |   |           \---bookstore
|   |   |               +---assertions
|   |   |               |       WrappedAssert.java
|   |   |               |       
|   |   |               +---endpoints
|   |   |               |       EndPoints.java
|   |   |               |       
|   |   |               +---logs
|   |   |               |       ConsoleColors.java
|   |   |               |       WrappedReportLogger.java
|   |   |               |       
|   |   |               +---report
|   |   |               |       ExtentReporter.java
|   |   |               |       
|   |   |               +---requestBuilder
|   |   |               |       ApiClient.java
|   |   |               |       RequestBuilder.java
|   |   |               |       
|   |   |               +---requestpojos
|   |   |               |       BookManagement.java
|   |   |               |       UserManagement.java
|   |   |               |       
|   |   |               +---responsepojos
|   |   |               |       Detail.java
|   |   |               |       GetAllBooks.java
|   |   |               |       GetBook.java
|   |   |               |       GetHealth.java
|   |   |               |       LoginForAccessTokenResponse.java
|   |   |               |       Message.java
|   |   |               |       
|   |   |               \---utils
|   |   |                       ConfigurationManager.java
|   |   |                       DataGenerator.java
|   |   |                       EnvConfigResolver.java
|   |   |                       
|   |   \---resources
|   |       |   log4j2.xml
|   |       |   
|   |       \---schemas
|   |               Detail.json
|   |               GetAllBooks.json
|   |               GetBook.json
|   |               GetHealth.json
|   |               IncorrectJsonFomat.json
|   |               LoginForAccessToken.json
|   |               Message.json
|   |               
|   \---test
|       +---java
|       |   \---com
|       |       \---api
|       |           \---bookstore
|       |               +---runner
|       |               |       TestRunner.java
|       |               |       
|       |               +---stepdefinitions
|       |               |       BookManagementSteps.java
|       |               |       HealthCheckSteps.java
|       |               |       UserManagementSteps.java
|       |               |       
|       |               \---testcontext
|       |                       TestContext.java
|       |                       
|       \---resources
|           \---features
|                   01_HealthCheckScenarios.feature
|                   02_UserManagementSceanrios.feature
|                   03_BookManagementScenarios.feature
|                   

config.properties                       
pom.xml                                     
testng.xml


## All the tools used

- Maven 3.8.4+
- Java 17+
- Cucumber 3.4.0+
- Git
- Any IDE (e.g., IntelliJ IDEA, Eclipse, VS Code)
- RestAssured
- TestNG
- log4j2
- ExtentReports
- GitHub Actions: CI/CD pipeline for automated testing and deployment


