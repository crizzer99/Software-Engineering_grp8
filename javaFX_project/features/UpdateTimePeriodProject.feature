 Feature: Update a project finish date
   Description: The project manager updates the timetable for a project
   Actors: Project manager, employee

   Scenario: The project manager moves the finish date for a project
     Given the employee "1001" is logged in
     And the project with title "greeting" and id "2201"
     And that the project manager with id "1001" has been chosen
     And the project "greeting" hasn't been marked as finished
     When the project manager sets the finish date for the project to 9 5 2023
     Then the finish date is updated to 9 5 2023

   Scenario: An employee moves the finish date for a project when the project manager hasn't been chosen
     Given the employee "jaaf" is logged in
     And the project with title "moving" and id "22002"
     And that the project manager hasn't been chosen for project "moving"
     And the project "moving" hasn't been marked as finished
     When the employee with id "jaaf" updates the finish date for the project to 10 2 2024
     Then the finish date is updated to 10 2 2024

   Scenario: An employee moves the finish date for a project when the project manager has been chosen
     Given the employee "criz" is logged in
     And the project with title "calculating" and id "22003"
     And that the project manager with id "jaaf" has been chosen
     And the project "calculating" hasn't been marked as finished
     When the employee with id "criz" updates the finish date for the project to 10 5 2023
     Then the error message "A project manager has been chosen, ask them to change the dates of the project." is given

   Scenario: The project manager moves the finish date for a project when the project has been marked as finished
     Given the employee "luff" is logged in
     And the project with title "eating" and id "22004"
     And that the project manager with id "luff" has been chosen
     And the project "eating" has been marked as finished
     When the project manager sets the finish date for the project to 15 5 2023
     Then the error message "This project has been marked as finished." is given