 Feature: Employee signing up for an activity
   Description: Employee assigns themselves to an activity
   Actors: Employee

   Scenario: Assigning oneself to an activity
     Given the employee "jaaf" is logged in
     And the employee "jaaf" is assigned less than 20 activities
     When an employee assigns themselves to activity "GUI-handling" in project "coolApp"
     Then the employee is part of the activity "GUI-handling"

   Scenario: The employee is part of too many activities
     Given the employee "jaaf" is logged in
     And the employee "jaaf" is assigned 20 activities
     When an employee assigns themselves to activity "GUI-handling" in project "coolApp"
     Then the error message "Too many activities assigned." is given

   Scenario: The employee is already assigned an activity
     Given the employee "jaaf" is logged in
     And the employee "jaaf" is assigned the activity "GUI-handling" in project "coolApp"
     When the employee signs up for the activity "GUI-handling"
     Then the error message "You are already part of this activity." is given