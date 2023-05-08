package example.cucumber.whitebox;
import application.*;
import example.cucumber.ErrorMessageHolder;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.*;
import org.junit.Assert;

public class WBEnrollExtraActivity {
    private Activity activity;
    private Employee employee;
    private Project project;
    private ErrorMessageHolder errorMessage;
    private ProjectManagementApp projectManagementApp;

    public WBEnrollExtraActivity(ProjectManagementApp projectManagementApp, ErrorMessageHolder errorMessage) {
        this.projectManagementApp = projectManagementApp;
        this.errorMessage = errorMessage;
    }

    // Scenario 1
    @Given("the employee is assigned {int} activities")
    public void theEmployeeIsAssignedActivities(Integer numActivities) {

        employee.setNumActivities(numActivities);
        Assert.assertTrue(projectManagementApp.getEmployeeNumActivities(employee) >= numActivities);
    }
    @Given("the employee is {string} assigned {int} activities")
    public void theEmployeeIsAssignedActivities(String id, Integer numActivities) {
        employee = projectManagementApp.getEmployeeFromId(id);
        employee.setNumActivities(numActivities);
        Assert.assertTrue(projectManagementApp.getEmployeeNumActivities(employee) >= numActivities);
    }

    @When("the employee wants to join the activity {string} in project {string}")
    public void theEmployeeWantsToJoinTheActivityInProject(String activityName, String projectTitle) throws Exception {
        activity = new Activity(activityName);
        projectManagementApp.createProject(projectTitle, null);
        project = projectManagementApp.getProjectFromTitle(projectTitle);
        projectManagementApp.addActivity(activity, project);
        Assert.assertTrue(projectManagementApp.hasActivity(activity));
    }
    @Given("an employee tries to join the activity {string}")
    public void anEmployeeTriesToJoinTheActivity(String activityName) {
        try {
            projectManagementApp.enrollExtraActivity(activity);
        } catch (Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }
    @Then("the exception {string} is thrown")
    public void theExceptionIsThrown(String errorMessage) {
        Assert.assertEquals(errorMessage, this.errorMessage.getErrorMessage());
    }

    // Scenario 2
    @Given("the employee {string} is assigned {int} activity with the name {string} in project {string}")
    public void theEmployeeIsAssignedActivityWithTheNameInProject(String id, Integer nActivities, String activityName, String projectTitle) throws Exception {
        employee = projectManagementApp.getEmployeeFromId(id);
        employee.setNumActivities(nActivities);
        Assert.assertTrue(projectManagementApp.getEmployeeNumActivities(employee) >= nActivities);

        activity = new Activity(activityName);
        projectManagementApp.createProject(projectTitle, null);
        project = projectManagementApp.getProjectFromTitle(projectTitle);
        projectManagementApp.addActivity(activity, project);
        projectManagementApp.enrollExtraActivity(activity);
        Assert.assertTrue(employee.containsActivity(activity) && activity.containsEmployee(employee));
    }

    // Scenario 3
    @Given("the employee {string} is assigned no activities")
    public void theEmployeeIsAssignedNoActivities(String id) {
        employee = projectManagementApp.getEmployeeFromId(id);
        Assert.assertTrue(projectManagementApp.getEmployeeNumActivities(employee) == 0);
    }
    @When("an employee enrolls the activity {string}")
    public void anEmployeeEnrollsTheActivity(String activityName) throws Exception {
        projectManagementApp.enrollExtraActivity(activity);
    }
    @Then("the employee is part of {string}")
    public void theEmployeeIsPartOf(String activityName) {
        Assert.assertTrue(projectManagementApp.employeeContainsActivity(employee, activity));
        Assert.assertTrue(projectManagementApp.activityContainsEmployee( activity, employee));

    }
}
