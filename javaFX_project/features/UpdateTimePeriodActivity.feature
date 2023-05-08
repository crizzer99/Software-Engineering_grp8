 Feature: Update a timeperiod for activities in a project
   Description: The project manager updates the timetable for an activity
   Actors: Project manager, employee

   Scenario: The project manager moves the start and/or finish date for an activity in a project.
     Given the employee "1001" is logged in
     And the project with title "greeting" and id "2201"
     And that the project manager with id "1001" has been chosen
     And the activity with name "waving" is added to the project "greeting"
     And the project "greeting" hasn't been marked as finished
     When the project manager updates the start date for activity "waving" to 9 5 2023
     Then the start date of the activity is set to 9 5 2023.

   Scenario: An employee moves the start and/or finish date for an activity in a project, when the project manager hasn't been chosen
     Given the employee "jaaf" is logged in
     And the project with title "eating" and id "22002"
     And that the project manager has not been chosen
     And the activity with name "meat" is added to the project "eating"
     And the project "eating" hasn't been marked as finished
     When the employee updates the start date for activity "meat" to 9 5 2023
     Then the start date of the activity is set to 9 5 2023.

 #  Scenario: The project manager moves the start date for the activity
 #    Given that the project manager has been chosen
 #    And the activity hasn't been marked as finished
 #    When the employee updates the start and finish dates for the activity
 #    Then the error message "A project manager has been chosen, ask them to change the dates of the activity." is given

#   Scenario: The project manager moves the start and finish date for an activity when the activity has been marked as finished
#     Given that the project manager has been chosen
#     And the activity has been marked as finished
#     When the project manager updates the start and finish dates for the activity
#     Then the error message "This activity has been marked as finished." is given