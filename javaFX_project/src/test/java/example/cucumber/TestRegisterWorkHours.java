package example.cucumber;
import application.*;
import io.cucumber.java.en.*;
import org.junit.Assert;

// Fischer - s214411
public class TestRegisterWorkHours {

    private Activity activity;
    private Employee employee;
    private ErrorMessageHolder errorMessage;
    private ProjectManagementApp projectManagementApp;

    // Scenario 1
    public TestRegisterWorkHours(ProjectManagementApp projectManagementApp, ErrorMessageHolder errorMessage) {
        this.projectManagementApp = projectManagementApp;
        this.errorMessage = errorMessage;
    }
    @Given("the employee {string} has {int} registered hours for the month")
    public void theEmployeeHasRegisteredHoursForTheMonth(String id, Integer hours) {
        employee = projectManagementApp.getEmployeeFromId(id);
        Assert.assertTrue(projectManagementApp.getEmployeeMonthlyHours(employee) == hours);
    }
    @When("the employee {string} registers {double} working hours")
    public void theEmployeeRegistersWorkingHours(String id, double hours) throws Exception {
        projectManagementApp.registerHours(hours);
    }
    @Then("the employee has registered {double} daily working hours")
    public void theEmployeeHasRegisteredDailyWorkingHours(Double hours) {
        Assert.assertTrue(projectManagementApp.getEmployeeLatestHours(employee) == hours
                && projectManagementApp.getEmployeeMonthlyHours(employee) == hours);
    }

    // Scenario 2
    @Given("that an employee with id {string} has registered {double} work hours")
    public void thatAnEmployeeWithIdHasRegisteredWorkHours(String id, Double hours) throws Exception {
        projectManagementApp.addEmployee(new Employee(id));
        employee = projectManagementApp.getEmployeeFromId(id);
        projectManagementApp.registerHours(hours);
        Assert.assertTrue(projectManagementApp.getEmployeeLatestHours(employee) == hours);
    }
    @When("changing registered work hours to {double}")
    public void changingRegisteredWorkHoursTo(Double hours) throws Exception {
        projectManagementApp.registerHours(hours);
    }
    @Then("the employees amount of registered work hours is {double}")
    public void theEmployeesAmountOfRegisteredWorkHoursIs(Double hours) {
        Assert.assertTrue(projectManagementApp.getEmployeeLatestHours(employee) == hours);
    }

    // Scenario 3
    @When("the employee with id {string} registers {double} work hours")
    public void theEmployeeWithIdRegistersWorkHours(String id, Double hours) throws Exception {
        projectManagementApp.addEmployee(new Employee(id));
        employee = projectManagementApp.getEmployeeFromId(id);
        try {
            projectManagementApp.registerHours(hours);
        } catch (Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }
    @When("the amount of allowed daily work hours is {double}")
    public void theAmountOfAllowedDailyWorkHoursIs(Double hours) {
        Assert.assertTrue(projectManagementApp.getMaxDailyHours() == hours);
    }

    // Scenario 4
    @Given("is registering {double} work hours")
    public void isRegisteringWorkHours(Double hours) {
        try {
            projectManagementApp.registerHours(hours);
        } catch (Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }
}
