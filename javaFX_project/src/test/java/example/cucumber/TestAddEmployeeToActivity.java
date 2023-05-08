package example.cucumber;

import application.*;
import io.cucumber.java.en.*;
import io.cucumber.java.lu.a;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;

// Nellemose - s215469
public class TestAddEmployeeToActivity {

    private Project project;
    private Activity activity;
    private Employee employee;
    private ProjectManager manager;
    private ErrorMessageHolder errorMessage;
    private ProjectManagementApp projectManagementApp;

    public TestAddEmployeeToActivity(ProjectManagementApp projectManagementApp, ErrorMessageHolder errorMessage) {
        this.projectManagementApp = projectManagementApp;
        this.errorMessage = errorMessage;
    }

    @Given("there is an employee with id {string} with less than {int} activities in the time period of the project")
    public void thereIsAnEmployeeWithIdWithLessThanActivitiesInTheTimePeriodOfTheProject(String employeeId, Integer numActivites) {
        employee = new Employee(employeeId);
        projectManagementApp.addEmployee(employee);
        Assert.assertTrue(employee.getNumberOfActivities() < numActivites);

    }

    @Given("there is an acitivity with name {string} in project {string} and id {string}")
    public void thereIsAnAcitivityWithNameInProjectAndId(String activityName, String projectName, String projectId) throws Exception{
        // Write code here that turns the phrase above into concrete actions
        activity = new Activity(activityName);
        project = projectManagementApp.getProjectFromTitle(projectName);

        projectManagementApp.addActivity(activity,project);
        Assert.assertTrue(projectManagementApp.projectContainsActivity(project, activity));
    }

    @When("the project manager adds the employee to the activity")
    public void theProjectManagerAddsTheEmployeeToTheActivity() throws Exception{
        try {
            projectManagementApp.addEmployeeToActivity(employee, activity);
        } catch(Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }

        // Write code here that turns the phrase above into concrete actions

    }

    @Given("that the project manager with id {string} is project manager for the project with title {string} and id {string}")
    public void thatTheProjectManagerWithIdIsProjectManagerForTheProjectWithTitleAndId(String managerId, String projectName, String projectId) throws Exception{
        project = projectManagementApp.getProjectFromTitle(projectName);
        employee = new Employee(managerId);
        projectManagementApp.addEmployee(employee);
        projectManagementApp.assignProjectManager(project, employee);
        manager = projectManagementApp.getManager(project);
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("the employee is added to the acitivity")
    public void theEmployeeIsAddedToTheAcitivity() throws Exception{
        // Write code here that turns the phrase above into concrete actions
        projectManagementApp.addEmployeeToActivity(employee, activity);
        Assert.assertTrue(activity.containsEmployee(employee));
    }

    @Given("that the project manager is logged in")
    public void thatTheProjectManagerWithIdIsLoggedIn() {
        projectManagementApp.employeeLogin(manager);
        Assert.assertTrue(projectManagementApp.loggedIn());
        // Write code here that turns the phrase above into concrete actions
    }

    @When("the employee adds another employee to the activity")
    public void theEmployeeAddsAnotherEmployeeToTheActivity() throws Exception{
        // Write code here that turns the phrase above into concrete actions
        try {
            projectManagementApp.addEmployeeToActivity(employee, activity);
        } catch(Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Given("there is an employee with id {string} {int} or more activities in the time period of the project")
    public void thereIsAnEmployeeWithIdOrMoreActivitiesInTheTimePeriodOfTheProject(String employeeId, Integer numActivities) {
        // Write code here that turns the phrase above into concrete actions
        employee = new Employee(employeeId);
        projectManagementApp.addEmployee(employee);
        employee.setNumActivities(11);
        Assert.assertTrue(employee.getNumberOfActivities() > numActivities);

    }




}
