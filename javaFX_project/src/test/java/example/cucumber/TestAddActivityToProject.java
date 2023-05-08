package example.cucumber;
import application.*;
import io.cucumber.java.en.*;
import org.junit.Assert;

import java.util.ArrayList;

// Nellemose - s215469
public class TestAddActivityToProject {

    private Project project;
    private Activity activity;
    private Employee employee;
    private ProjectManager manager;
    private ErrorMessageHolder errorMessage;
    private ProjectManagementApp projectManagementApp;

    public TestAddActivityToProject(ProjectManagementApp projectManagementApp, ErrorMessageHolder errorMessage) {
        this.projectManagementApp = projectManagementApp;
        this.errorMessage = errorMessage;
    }

    @Given("the employee {string} is logged in")
    public void theEmployeeIsLoggedIn(String id) {
        employee = new Employee(id);
        projectManagementApp.addEmployee(employee);
        projectManagementApp.employeeLogin(employee);
        Assert.assertTrue(projectManagementApp.loggedIn() == true);
    }
    @Given("the project with title {string} and id {string}")
    public void theProjectWithTitleAndId(String title, String id) throws Exception {
        projectManagementApp.createProject(title, null);
        project = projectManagementApp.getProjectFromTitle(title);
        Assert.assertTrue(projectManagementApp.getProjectFromTitle(title) != null);
    }

    @Given("that the project manager with id {string} has been chosen")
    public void thatTheProjectManagerHasBeenChosen(String id) throws Exception {
        employee = new Employee(id);
        projectManagementApp.addEmployee(employee);
        projectManagementApp.assignProjectManager(project, employee);
        manager = projectManagementApp.getManager(project);
        Assert.assertTrue(projectManagementApp.getManagerId(manager).equals(id));

    }
    @Given("the project has not been marked as finished")
    public void theProjectHasNotBeenMarkedAsFinished() {
        Assert.assertFalse(projectManagementApp.checkProjectStatus(project));
    }
    @When("the project manager adds the activity with name {string} to the project")
    public void theProjectManagerAddsTheActivityToTheProject(String name) throws Exception {
        activity = new Activity(name);
        projectManagementApp.addActivity(activity, project);
    }
    @Then("the activity is added to the project")
    public void theActivityIsAddedToTheProject() {
        Assert.assertTrue(projectManagementApp.projectContainsActivity(project, activity));
    }

    @Given("that the project manager has not been chosen")
    public void thatTheProjectManagerHasNotBeenChosen() {
        Assert.assertTrue(projectManagementApp.getManager(project) == null);
    }
    @When("the employee adds the activity with name {string} to the project {string}")
    public void theEmployeeAddsTheActivityWithNameToTheProject(String name, String projectTitle) throws Exception {
        activity = new Activity(name);
        project = projectManagementApp.getProjectFromTitle(projectTitle);
        projectManagementApp.addActivity(activity, project);
        try {
            projectManagementApp.addActivity(activity, project);
        } catch (Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the activity with name {string} is added to the project")
    public void theActivityWithNameIsNotAddedToTheProject(String name) {
        activity = projectManagementApp.getActivityFromName(name);
        Assert.assertTrue(projectManagementApp.projectContainsActivity(project, activity));
    }
    @Then("the error message {string} is given")
    public void theErrorMessageIsGiven(String errorMessage) {
        Assert.assertEquals(errorMessage, this.errorMessage.getErrorMessage());
    }

    @Given("the project has been marked finished")
    public void theProjectHasBeenMarkedFinished() {
        projectManagementApp.markProjectFinished(project);
        Assert.assertTrue(projectManagementApp.checkProjectStatus(project));
    }
    @When("the project manager adds the activity to the project")
    public void theProjectManagerAddsTheActivityToTheProject() {
        try {
            projectManagementApp.addActivity(activity, project);
        } catch (Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }
}
