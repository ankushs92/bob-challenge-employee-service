# Functionalities Implemented :

1. Authentication and Authorization on employee endpoints
2. Endpoints for adding department, CRUD operations for Employees, and an endpoint to login 
3. Swagger documentation on department, login and employee endpoints

# Functionalities Implemented :
1. Java 11 
2. Spring Boot
3. Spring Security(JWT Authentication and Authorization on employee endpoints)
4. MySQL
5. Spring Boot Kafka


# Setup


# Endpoints

Launch the service and head on to ```http://localhost:8080/swagger-ui/index.html#/``` to view the different endpoints. The three functionalities that I have implemented are as follows:

1. Login : In order to perform any operations on Employee endpoints, you would first need to perform a login. 

These are the steps required to login :
    - Send a POST request to ```localhost:8080/login```
    - The 'email' and 'password' needs to be in a JSON. When this service starts, I insert a dummy user with username 'root' and password 'root'. 
    - After you send 'root' as 'email' and 'password', LoginController returns an empty response with the JwtToken in 'Authorization' header. You will have to copy this header
    and put it in the 'Authorization' header for any subsequent request to any other employee endpoints
    
2. Department : An endpoint to create a department can be found at ```localhost:8080/department/``.      

#Postman

# Employee-Service

As its name suggests, this service is responsible for handling the employees of a company. The application must expose a REST API. It should contain endpoints to:
  - Create a department
    - Id (auto-increment)
    - Name
    
 - Create an employee with the following properties:
   - Uuid (generated automatically)
   - E-mail
   - Full name (first and last name)
   - Birthday (format YYYY-MM-DD)
   - Employee’s department
   
  - Get a specific employee by uuid (response in JSON Object format)
  - Update an employee
  - Delete an employee

Whenever an employee is created, updated or deleted, an event related to this action must be pushed in Kafka. This event will be listened to by the [`event-service`](https://github.com/takeaway/bob-challenge-event-service/).

#### Restrictions

 - The `email field` is unique, i.e. _2 employees cannot have the same email._
