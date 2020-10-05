# Functionalities Implemented :

1. Authentication and Authorization on employee endpoints
2. Endpoints for adding department, CRUD operations for Employees, and an endpoint to login 
3. Swagger documentation on department, login and employee endpoints
4. Unit and Integration testing for different scenarios

# Tech and Libraries :
1. Java 11 
2. Spring Boot
3. Spring Security(JWT Authentication and Authorization on employee endpoints)
4. MySQL
5. Spring Boot Kafka


# Setup

The test cases use an embedded `h2` database(please check application-test.properties).

1. Go to project root

2. Execute ```docker-compose up```. MySQL, Zookeeper and Kafka are now live

3. If you wish to build a jar by executing the tests, execute ```mvn clean package -Dspring.profiles.active=test```. All the exceptions and errors that you will see while building is because I am logging all of them.

4. Run the application by executing ``` mvn spring-boot:run```


# Endpoints

Launch the service and head on to ```http://localhost:8080/swagger-ui/index.html#/``` to view the different endpoints. The three functionalities that I have implemented are as follows:

### 1.  Login :
 
In order to perform any operations on Employee endpoints, you would first need to perform a login. 

These are the steps required to login :

    - Send a POST request to ```localhost:8080/login```
    
    - The 'email' and 'password' needs to be in a JSON. When you launch this Spring Boot application, I insert a dummy user with username 'root' and password 'root' on startup.
    
    - After you send 'root' as 'email' and 'password', LoginController returns an empty response with the JwtToken in 'Authorization' header. You will have to copy this header
    and put it in the 'Authorization' header for any subsequent request to any other employee endpoints
    
### 2. Department : 

An endpoint to create a department can be found at ```localhost:8080/department/```. You are NOT required to pass a JWT token to access this endpoint.
Moreover, I have added the following validation check : a department needs to have a unique name, i.e two departments with the same name cannot exists.

### 2. Employee : 

We have four endpoints for Employee. In order to access any endpoint, you will need to pass the Jwt Bearer token that you receive when you login. This token needs to be added to the "Authorization" header.
The four endpoints are :

1. Creating an employee : ```localhost:8080/employee/```. HTTP POST .

2. Get Employee : ```localhost:9090/employee/{employeeUuid}```. HTTP GET

3. Delete Employee : ```localhost:9090/employee/{employeeUuid}```. HTTP DELETE

4. Update Employee : ```localhost:9090/employee/{employeeUuid}```. HTTP PUT. Note that we allow an employee to update all of it's fields, including the department. If a field is not present in the JSON request, it will not be updated. For example,
if 'name' is not passed in the JSON request and 'birthday' is passed, then only birthday is updated, the employee field 'name' is not updated.
Moreover, if you wish to change the employee email to an email that already exists in the database, an appropriate error message will be returned. Similarly,
if you pass a departmentId that does not exist in the database, an HTTP 404 with error message will be returned.

# Kafka

A topic called 'employee_events' is automatically created with 1 partition and 1 replication factor.

# Other ideas

We could have also modelled the 'events-service' as a Type II Dimension. This would have allowed us to track the entire history of employees in one table.
