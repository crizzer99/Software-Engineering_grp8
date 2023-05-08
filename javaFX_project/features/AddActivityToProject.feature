Feature: Add activity to project
  Description: The project manager adds an activity to the project
  Actors: Project manager, employee

  Scenario: The project manager adds an activity to a project
    Given the employee "jaaf" is logged in
    And the project with title "startup" and id "23001"
    And that the project manager with id "jaaf" has been chosen
    And the project has not been marked as finished
    When the project manager adds the activity with name "seeEmployees" to the project
    Then the activity is added to the project

  Scenario: An employee adds an activity to a project when the project manager has not been chosen
    Given the employee "heej" is logged in
    And the project with title "addFeature" and id "23002"
    And that the project manager has not been chosen
    And the project has not been marked as finished
    When the employee adds the activity with name "seeHours" to the project "addFeature"
   Then the activity is added to the project

  Scenario: An employee adds an activity to a project when the project manager has been chosen
    Given the employee "chne" is logged in
    And the project with title "startup" and id "23001"
    And that the project manager with id "chne" has been chosen
    And the project has not been marked as finished
    When the employee adds the activity with name "seeHours" to the project "startup"
    Then the activity with name "seeHours" is added to the project

  Scenario: The project manager adds an activity to a project that has been marked as finished
    Given the employee "chne" is logged in
    And the project with title "startup" and id "23001"
    And that the project manager with id "chne" has been chosen
    And the project has been marked finished
    When the project manager adds the activity to the project
    Then the error message "This project has been marked as finished." is given