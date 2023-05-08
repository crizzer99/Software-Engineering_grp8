 Feature: Register working hours
   Description: An employee of the company registers working hours
   Actors: Employees

   Scenario: Register hours for an activity
     Given the employee "jaaf" is logged in
     And the employee "jaaf" has 0 registered hours for the month
     When the employee "jaaf" registers 10.5 working hours
     Then the employee has registered 10.5 daily working hours

   Scenario: Editting amount of registered work hours
     Given the employee "mabn" is logged in
     And that an employee with id "mabn" has registered 5.0 work hours
     When changing registered work hours to 10.0
     Then the employees amount of registered work hours is 10.0

   Scenario: Registering too many work hours
     Given the employee "criz" is logged in
     When the employee with id "criz" registers 18.0 work hours
     And the amount of allowed daily work hours is 16.0
     Then the error message "Too many hours registered. Must be 16 hours or lower." is given

   Scenario: Registering hours with wrong format
     Given the employee "kjel" is logged in
     And is registering 8.33 work hours
     Then the error message "Wrong input format. Please type something like \"10.5\"." is given