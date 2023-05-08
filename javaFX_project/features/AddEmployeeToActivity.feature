Feature: Add employee to activity
    Description: The project manager adds an employee to an activity in a project
    Actors: Project manager

    Scenario: Add an employee to an acitivty
        Given the employee "23" is logged in
        And the project with title "greeting" and id "2201"
        And the project "greeting" hasn't been marked as finished
        And that the project manager with id "23" is project manager for the project with title "greeting" and id "2201"
        And that the project manager is logged in
        And there is an acitivity with name "gigachad" in project "greeting" and id "2201"
        And there is an employee with id "24" with less than 10 activities in the time period of the project
        When the project manager adds the employee to the activity
        Then the employee is added to the acitivity


    Scenario: Add an employee to an activity when the project manager hasn't been chosen
        Given the employee "24" is logged in
        And the project with title "greeting" and id "2201"
        And that the project manager has not been chosen
        And the project "greeting" hasn't been marked as finished
        And there is an employee with id "24" with less than 10 activities in the time period of the project
        And that the employee is logged in
        And there is an acitivity with name "gigachad" in project "greeting" and id "2201"
        When the project manager adds the employee to the activity
        Then the error message "Project manager has not been chosen yet." is given

    Scenario: Add an employee to an acitivity when actor is not manager
        Given the employee "23" is logged in
        And the project with title "greeting" and id "2201"
        And that the project manager with id "23" is project manager for the project with title "greeting" and id "2201"
        And the project "greeting" hasn't been marked as finished
        And there is an employee with id "24" with less than 10 activities in the time period of the project
        And that the employee is logged in
        And there is an acitivity with name "gigachad" in project "greeting" and id "2201"
        When the employee adds another employee to the activity
        Then the error message "You are not the project manager." is given

    Scenario: Add an employee to an activity when the employee has 10 or more activities in the time period
        Given the employee "23" is logged in
        And the project with title "greeting" and id "2201"
        And the project "greeting" hasn't been marked as finished
        And that the project manager with id "23" is project manager for the project with title "greeting" and id "2201"
        And that the project manager is logged in
        And there is an acitivity with name "gigachad" in project "greeting" and id "2201"
        And there is an employee with id "24" 10 or more activities in the time period of the project
        When the project manager adds the employee to the activity
        Then the error message "Chosen employee has too many activities in the given period." is given
