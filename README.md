1. Clone the project and setup in the local environment.

2. You can find the scripts folder under src/main/resources folder and you can execute the script on Database.

3. Application runs on port 8070 which is defined in application-dev.

4. Implemented swagger configuration in the project for documentation. Swaggger URL would be like http://localhost:8070/healthcare/swagger-ui.html

5. http://localhost:8070/healthcare/enrollee/add ==> This API creates a new Enrollee

6. http://localhost:8070/healthcare/enrollee/modify ==> This API updated an existing Enrollee

7. http://localhost:8070/healthcare/enrollee/addDependentToEnrollee?dependentId=3&enrolleeId=6 ==> This API adds dependent to Enrollee

8. http://localhost:8070/healthcare/enrollee/all ==> This API gets all enrollees.

9. http://localhost:8070/healthcare/enrollee/delete/6  ==> This API deletes enrollee

10. http://localhost:8070/healthcare/enrollee/delete/dependent/3  ==> This API deletes dependent from enrollee

11. http://localhost:8070/healthcare/dependent/add  ==> This API creates a new Dependent.

12. http://localhost:8070/healthcare/dependent/modify  ==> This API modifies an existing Dependent.

13. http://localhost:8070/healthcare/dependent/all ==> This API gets all dependents.

14. You can play now!!!.
