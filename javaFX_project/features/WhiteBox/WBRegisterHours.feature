Feature: Employee registers hours
    Description: The employee registers hours spent 

    Scenario: Input set A
        Given the employee "jaaf" is logged in
        And the employee "jaaf" has 16 hours registered today
        When the employee registers 2 hours
        Then the exception "Too many hours registered. Must be 16 hours or lower." is thrown
   
    Scenario: Input set B
        Given the employee "jaaf" is logged in
        And the employee "jaaf" has 0 hours registered today
        When the employee registers 8 hours
        Then the hours 8 are registered

    Scenario: Input set C
        Given the employee "jaaf" is logged in
        And the employee "jaaf" has 0 hours registered today
        And the employee is on vacation
        When the employee registers 2 hours
        Then the exception "You are supposed to be on vacation, please e-mail the project manager if you still want to add hours." is thrown

    Scenario: Input set D
        Given the employee "jaaf" is logged in
        And the employee "jaaf" has 4 hours registered today
        When the employee registers 2 hours
        Then the hours 2 are registered

