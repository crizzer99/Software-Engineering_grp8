package example.cucumber.whitebox;
import example.cucumber.ErrorMessageHolder;

import application.*;
import io.cucumber.java.en.*;
import io.cucumber.java.lu.a;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;

// Nellemose - s215469
public class WBRegisterHours {

    // private Project project;
    // private Activity activity;
    private Employee employee;
    private ErrorMessageHolder errorMessage;
    private ProjectManagementApp projectManagementApp;

    public WBRegisterHours(ProjectManagementApp projectManagementApp, ErrorMessageHolder errorMessage) {
        this.projectManagementApp = projectManagementApp;
        this.errorMessage = errorMessage;
    }


    @Given("the employee {string} has {int} hours registered today")
    public void theEmployeeHasHoursRegisteredToday(String employeeId, Integer int1) throws Exception{
        // Write code here that turns the phrase above into concrete actions
        employee = projectManagementApp.getEmployeeFromId(employeeId);
        projectManagementApp.registerHours(int1);

    }

    @When("the employee registers {int} hours")
    public void theEmployeeRegistersHours(Integer int1) throws Exception{
        // Write code here that turns the phrase above into concrete actions
        try {
            projectManagementApp.registerHours(int1);
        } catch(Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the hours {int} are registered")
    public void theHoursAreRegistered(int int1) {
        Assert.assertTrue(employee.getLatestRegisteredHours() >= int1);
    }

    @Given("the employee is on vacation")
    public void theEmployeeIsOnVacation() {
        // Write code here that turns the phrase above into concrete actions
        boolean vacation = true;
        projectManagementApp.setVacationStatus(vacation);
    }
    
}
