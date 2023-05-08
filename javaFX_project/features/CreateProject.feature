Feature: Creating a new project
  Description: The customer can make a project
  Actors: Employee

  Scenario: Creating a project logged in:
    Given that the employee is logged in
    And the project is called: "Test101"
    When the project is added to the system
    Then the project: "Test101" can be seen in the system

  Scenario: Creating a project not logged in:
    Given that the employee is not logged in
    And the project is called: "Test101"
    When the project is added to the system
    Then the error message "You can only make a project if you are logged in!" is given

  Scenario: Creating a project without a title:
    Given that the employee is logged in
    And the project is not called anything
    When the project is added to the system
    Then the error message "You can only make a project if you have defined its name" is given