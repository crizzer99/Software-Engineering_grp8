Feature: WB for creating project (Already exists)
  Description: Whitebox-test for Employee assigns themselves to an activity
  Actors: Employee

  Scenario: Input set A
    Given that the employee is not logged in
    When the project is added to the system
    Then the error message "You can only make a project if you are logged in!" is given
  
  Scenario: Input set B
    Given that the employee is logged in
    And the project is not called anything
    When the project is added to the system
    Then the error message "You can only make a project if you have defined its name" is given
  
  Scenario: Input set C
    Given that the employee is logged in
    And the project is called: "Test"
    When the project is added to the system
    Then the project: "Test" can be seen in the system

  Scenario: Input set D
    Given that the employee is logged in
    And the project is called: "Test1"
    When the project is added to the system with a test employee
    Then the project: "Test1" can be seen in the system
    