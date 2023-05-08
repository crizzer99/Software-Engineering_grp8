package example.cucumber;
import application.*;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.*;
import org.junit.Assert;

// Fischer - s214411
public class TestEnlistActivity {
    private Activity activity;
    private Employee employee;
    private ErrorMessageHolder errorMessage;
    private Project project;
    private ProjectManagementApp projectManagementApp;

    public TestEnlistActivity(ProjectManagementApp projectManagementApp, ErrorMessageHolder errorMessage) {
        this.projectManagementApp = projectManagementApp;
        this.errorMessage = errorMessage;
    }
    //Scenario 1
    @Given("an employee with id {string}")
    public void anEmployeeWithId(String id) {
        employee = new Employee(id);
        projectManagementApp.addEmployee(employee);
    }
    @Given("the employee is logged in")
    public void theEmployeeIsLoggedIn() {
        projectManagementApp.employeeLogin(employee);
        Assert.assertTrue(projectManagementApp.loggedIn());
    }
    @Given("the employee {string} is assigned less than {int} activities")
    public void theEmployeeIsAssignedLessThanActivities(String id, Integer numActivities) {
        employee = projectManagementApp.getEmployeeFromId(id);
        Assert.assertTrue(numActivities > employee.getNumberOfActivities());
    }
    @When("an employee assigns themselves to activity {string} in project {string}")
    public void anEmployeeAssignsThemselvesToActivityInProject(String activityName, String projectTitle) throws Exception {
        activity = new Activity(activityName);
        projectManagementApp.createProject(projectTitle, null);
        project = projectManagementApp.getProjectFromTitle(projectTitle);
        projectManagementApp.addActivity(activity, project);
        try {
            projectManagementApp.enrollExtraActivity(activity);
        } catch (Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }
    @When("an employee assigns themselves to activity {string}")
    public void anEmployeeAssignsThemselvesToActivity(String activityName) throws Exception {

    }
    @Then("the employee is part of the activity {string}")
    public void theEmployeeIsPartOfTheActivity(String string) {
        Assert.assertTrue(projectManagementApp.employeeContainsActivity(employee, activity));
        Assert.assertTrue(projectManagementApp.activityContainsEmployee(activity,employee));
    }

    //Scenario 2
    @Given("the employee {string} is assigned {int} activities")
    public void theEmployeeIsAssignedActivities(String id, Integer numActivities) {
        employee = projectManagementApp.getEmployeeFromId(id);
        employee.setNumActivities(numActivities);
        Assert.assertTrue(projectManagementApp.getEmployeeNumActivities(employee) >= numActivities);
    }

    //Scenario 3
    @Given("the employee {string} is assigned the activity {string} in project {string}")
    public void theEmployeeIsAssignedTheActivityInProject(String id, String activityName, String projectTitle) throws Exception {
        employee = projectManagementApp.getEmployeeFromId(id);
        activity = new Activity(activityName);
        projectManagementApp.createProject(projectTitle, null);
        project = projectManagementApp.getProjectFromTitle(projectTitle);
        projectManagementApp.addActivity(activity, project);
        projectManagementApp.enrollExtraActivity(activity);
        Assert.assertTrue(projectManagementApp.activityContainsEmployee(activity,employee));
    }
    @When("the employee signs up for the activity {string}")
    public void theEmployeeSignsUpForTheActivity(String string) throws Exception {
        try {
            projectManagementApp.enrollExtraActivity(activity);
        } catch (Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }
}
