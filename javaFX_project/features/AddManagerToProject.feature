Feature: Adding project leader to project
  Description: The project shall have a leader
  Actors: Employee

Background: The system has these projects registered:
  Given that these projects are in the system
  | Project Name   | Project Manager    |
  | Work things 01 |                    |
  | Work things 02 | jzks               |

Scenario: Adding project leader to project with no project leader:
   Given the project is called: "Work things 01"
   And the project has no project leader
   When the user registers as project leader
   Then the project manager has been chosen

Scenario: Adding project leader to project with a project leader:
   Given the project is called: "Work things 02" 
   And the project manager has been chosen
   When the user registers as project leader
   Then the error message "This project already has a manager" is given