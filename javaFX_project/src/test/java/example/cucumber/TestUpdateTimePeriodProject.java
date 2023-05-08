package example.cucumber;
import application.*;
import io.cucumber.java.en.*;
import org.junit.Assert;

import java.util.Calendar;

// Fischer - s214411
public class TestUpdateTimePeriodProject {
    private Employee employee;
    private ErrorMessageHolder errorMessage;
    private ProjectManagementApp projectManagementApp;
    private Calendar c;

    private Project project;

    public TestUpdateTimePeriodProject(ProjectManagementApp projectManagementApp, ErrorMessageHolder errorMessage) {
        this.projectManagementApp = projectManagementApp;
        this.errorMessage = errorMessage;
    }
    @Given("the project {string} hasn't been marked as finished")
    public void theProjectHasnTBeenMarkedAsFinished(String title) {
        project = projectManagementApp.getProjectFromTitle(title);
        Assert.assertFalse(projectManagementApp.checkProjectStatus(project));
    }
    @When("the project manager sets the finish date for the project to {int} {int} {int}")
    public void theProjectManagerSetsTheFinishDateForTheProjectTo(Integer day, Integer month, Integer year) throws Exception {
        String managerId = projectManagementApp.getManager(project).getId();
        employee = projectManagementApp.getEmployeeFromId(managerId);
        try {
            projectManagementApp.setProjectEndDate(project, day, month, year);
        } catch (Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }
    @Then("the finish date is updated to {int} {int} {int}")
    public void theFinishDateIsUpdatedTo(Integer day, Integer month, Integer year) {
        c = project.getEndDate();
        int days = c.get(Calendar.DAY_OF_MONTH);
        int months = c.get(Calendar.MONTH);
        int years = c.get(Calendar.YEAR);
        Assert.assertTrue(day == days && month == months && years == year);
    }

    @Given("that the project manager hasn't been chosen for project {string}")
    public void thatTheProjectManagerHasnTBeenChosenForProject(String title) {
        project = projectManagementApp.getProjectFromTitle(title);
        Assert.assertFalse(project.hasProjectManager());
    }
    @When("the employee with id {string} updates the finish date for the project to {int} {int} {int}")
    public void theEmployeeWithIdUpdatesTheFinishDateForTheProjectTo(String id , Integer day, Integer month, Integer year) throws Exception {
        employee = new Employee(id);
        projectManagementApp.addEmployee(employee);
        projectManagementApp.employeeLogin(employee);
        try {
            projectManagementApp.setProjectEndDate(project, day, month, year);
        } catch (Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }

    }

    @Given("the project {string} has been marked as finished")
    public void theProjectHasBeenMarkedAsFinished(String title) {
        project = projectManagementApp.getProjectFromTitle(title);
        projectManagementApp.markProjectFinished(project);
        Assert.assertTrue(projectManagementApp.checkProjectStatus(project));
    }

}
