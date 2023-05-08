//Kjellberg - 224809
package example.cucumber;
import application.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

public class TestProject {

    private ProjectManagementApp projectManagementApp;
    private ErrorMessageHolder errorMessage;
    private Project project;
    private Employee testEmployee;
    private String projectTitle;

    public TestProject(ProjectManagementApp projectManagementApp, ErrorMessageHolder errorMessage) {
        this.projectManagementApp = projectManagementApp;
        this.errorMessage = errorMessage;
    }

    @Before
    public void testEmployee() {
        testEmployee = new Employee("jako1234");
        projectManagementApp.addEmployee(testEmployee);
    }

    @Given("that the employee is logged in")
    public void thatTheEmployeeIsLoggedIn() {
        projectManagementApp.employeeLogin(testEmployee);
        Assert.assertTrue(projectManagementApp.loggedIn() == true);
    }

    @Given("the project is called: {string}")
    public void theProjectIsCalled(String projectName) {
        projectTitle = projectName;
        project = projectManagementApp.getProjectFromTitle(projectName);
    }

    @When("the project is added to the system")
    public void theProjectIsAddedToTheSystem() throws Exception {
        try {
            projectManagementApp.createProject(projectTitle, null);
        } catch (AssertionError e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @When("the project is added to the system with a test employee")
    public void theProjectIsAddedToTheSystemWithTestEmployee() throws Exception {
        Employee newEmployee = new Employee("name123");
        try {
            projectManagementApp.createProject(projectTitle, newEmployee);
        } catch (Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
        Project project = projectManagementApp.getProjectFromTitle(projectTitle);
        Assert.assertTrue(project.hasProjectManager());
    }

    @Then("the project: {string} can be seen in the system")
    public void theProjectCanBeSeenInTheSystem(String projectName) {
        Assert.assertTrue(projectManagementApp.getProjectFromTitle(projectName) != null);
    }

    @Given("that the employee is not logged in")
    public void thatTheEmployeeIsNotLoggedIn() {
        projectManagementApp.employeeLogout();
        Assert.assertTrue(projectManagementApp.loggedIn() == false);
    }

    @Given("the project is not called anything")
    public void theProjectIsNotCalledAnything() throws Exception {
        try {
            projectManagementApp.createProject(null,null);
        } catch (AssertionError e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Given("that these projects are in the system")
    public void thatTheseProjectsAreInTheSystem(io.cucumber.datatable.DataTable dataTable) throws Exception {
        projectManagementApp.employeeLogin(testEmployee);
        List<Map<String, String>> tableData = dataTable.asMaps();

        for (Map<String, String> projectData : tableData) {
            String projectName = projectData.get("Project Name");
            String projectManagerId = projectData.get("Project Manager");
            Employee tempEmployee = null;
            if (projectManagerId != null) {
                tempEmployee = new Employee(projectManagerId);
            }
            projectManagementApp.createProject(projectName, tempEmployee);
        }
    }

    @Given("the project has no project leader")
    public void theProjectHasNoProjectLeader() {
        Assert.assertTrue(project.hasProjectManager() == false);
    }

    @When("the user registers as project leader") 
    public void theUserRegistersAsProjectLeader() throws Exception {
        try {
            projectManagementApp.assignProjectManager(project, testEmployee);
        } catch (Exception e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project manager has been chosen")
    public void theProjectManagerHasBeenChosen() {
        Assert.assertTrue(project.hasProjectManager() == true);
    }
}
