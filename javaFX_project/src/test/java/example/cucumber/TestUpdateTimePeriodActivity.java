package example.cucumber;
import application.*;
import io.cucumber.java.en.*;
import org.junit.Assert;
import application.Employee;
import application.ProjectManagementApp;

import java.util.Calendar;

public class TestUpdateTimePeriodActivity {

    private Employee employee;
    private ErrorMessageHolder errorMessage;
    private ProjectManagementApp projectManagementApp;
    private Project project;
    private Calendar c;
    private Activity activity;

    public TestUpdateTimePeriodActivity(ProjectManagementApp projectManagementApp, ErrorMessageHolder errorMessage) {
        this.projectManagementApp = projectManagementApp;
        this.errorMessage = errorMessage;
    }
    @Given("the activity with name {string} is added to the project {string}")
    public void theActivityWithNameIsAddedToTheProject(String activityName, String projectTitle) throws Exception {
        project = projectManagementApp.getProjectFromTitle(projectTitle);
        projectManagementApp.addActivity(new Activity(activityName), project);
        activity = projectManagementApp.getActivityFromName(activityName);
        Assert.assertTrue(projectManagementApp.projectContainsActivity(project,activity));
    }
    @When("the project manager updates the start date for activity {string} to {int} {int} {int}")
    public void theProjectManagerUpdatesTheStartDateForActivityTo(String activityName, Integer day, Integer month, Integer year) {
        activity = projectManagementApp.getActivityFromName(activityName);
        projectManagementApp.setStartDateActivity(activity, day, month, year);
    }
    @Then("the start date of the activity is set to {int} {int} {int}.")
    public void theStartDateOfTheActivityIsSetTo(Integer day, Integer month, Integer year) {
        c = activity.getStartDate();
        int days = c.get(Calendar.DAY_OF_MONTH);
        int months = c.get(Calendar.MONTH);
        int years = c.get(Calendar.YEAR);
        Assert.assertTrue(day == days && month == months && year == years);
    }

    @When("the employee updates the start date for activity {string} to {int} {int} {int}")
    public void theEmployeeUpdatesTheStartDateForActivityTo(String activityName, Integer day, Integer month, Integer year) {
        activity = projectManagementApp.getActivityFromName(activityName);
        projectManagementApp.setStartDateActivity(activity, day, month, year);
    }
}
