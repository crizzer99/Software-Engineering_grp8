Feature: Employee signing up for an activity
  Description: Whitebox-test for Employee assigns themselves to an activity
  Actors: Employee

  Scenario: 1(true), 2
    Given the employee "jaaf" is logged in
    And the employee is "jaaf" assigned 20 activities
    When the employee wants to join the activity "GUI-handling" in project "coolApp"
    And an employee tries to join the activity "GUI-handling"
    Then the exception "Too many activities assigned." is thrown

  Scenario: 1(false), 3(true), 4
   Given the employee "criz" is logged in
    And the employee "criz" is assigned 1 activity with the name "GUI-Handling" in project "coolApp"
    When an employee tries to join the activity "GUI-handling"
    Then the exception "You are already part of this activity." is thrown

  Scenario: 1(false), 3(false), 5, 6
    Given the employee "magn" is logged in
    And the employee "magn" is assigned no activities
    When the employee wants to join the activity "GUI-handling" in project "coolApp"
    Then an employee enrolls the activity "GUI-handling"
    Then the employee is part of "GUI-handling"