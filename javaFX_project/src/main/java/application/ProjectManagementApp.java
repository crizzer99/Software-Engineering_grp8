package application;

import java.util.*;
import java.util.Calendar;
import org.junit.Assert;

public class ProjectManagementApp {

    private boolean loggedIn = false;
    private DateServer dateServer = new DateServer();
    private Employee currentLogIn = null;
    private ArrayList<Employee> employees = new ArrayList<Employee>();
    private ArrayList<Activity> activities = new ArrayList<Activity>();
    private ArrayList<Project> projects = new ArrayList<Project>();
    private static int idNumber = 0;
    private final double maxDailyHours = 16;
    private boolean vacationStatus;

    // Kjellberg - s224809
    public boolean loggedIn() {
        return loggedIn;
    }

    // Kjellberg - s224809
    public void employeeLogin(Employee employee) {
        loggedIn = false;
        for (Employee i : employees) {
            if (i.getId() == employee.getId()) {
                loggedIn = true;
                currentLogIn = employee;
            }
        }
    }

    // Kjellberg - s224809
    public void employeeLogout() {
        loggedIn = false;
        currentLogIn = null;
    }

    // Fischer - s214411
    public void registerHours(double hours) throws Exception {
        int day = dateServer.getDate().get(Calendar.DAY_OF_MONTH);
        int month = dateServer.getDate().get(Calendar.MONTH);
        if(vacationStatus) {                                                                                                                //  1
            throw new Exception("You are supposed to be on vacation, please e-mail the project manager if you still want to add hours.");   //  2
        } else if(hours % 0.5 != 0 || hours < 0) {                                                                                          //  3
            throw new Exception("Wrong input format. Please type something like \"10.5\".");                                                //  4
        } else if (currentLogIn.getHours(day,month) + hours> maxDailyHours) {                                                               //  5
            throw new Exception("Too many hours registered. Must be 16 hours or lower.");                                                   //  6
        } else if (currentLogIn.getHours(day, month) > 0.0) {                                                                               //  7  
            currentLogIn.registerHours(-getEmployeeLatestHours(currentLogIn), day, month);                                                  //  8
            currentLogIn.registerHours(hours, day, month);                                                                                  //  9
        } else {
            currentLogIn.registerHours(hours, day, month);                                                                                  // 10
        }
    }
    public double getEmployeeMonthlyHours(Employee employee) {
        return employee.getMonthlyHours();
    }

    // Fischer - s214411
    public void enrollExtraActivity(Activity activity) throws Exception {
        // Pre-condition: Checks to see that employee is logged in and activity exists in the system
        assert loggedIn : "You need to be logged in.";
        assert activities.contains(activity) : "This activity isn't part of the system, please give a proper activity";
        if (currentLogIn.getNumberOfActivities() >= 20) {                       // 1
            throw new Exception("Too many activities assigned.");               // 2
        } else if (currentLogIn.containsActivity(activity)) {                   // 3
            throw new Exception("You are already part of this activity.");      // 4
        } else {
            activity.addEmployee(currentLogIn);                                 // 5
            currentLogIn.enrollExtraActivity(activity);                         // 6
        }
        // Post-condition: checks to see if employee and activity have been listed properly
        assert activity.containsEmployee(currentLogIn) && currentLogIn.containsActivity(activity);
    }

    // Fischer - s214411
    public boolean employeeContainsActivity(Employee employee, Activity activity) {
        return employee.containsActivity(activity);
    }

    // Fischer - s214411
    public boolean activityContainsEmployee(Activity activity, Employee employee) {
        return activity.containsEmployee(employee);
    }

    // Nellemose - s215469
    public void addActivity(Activity activity, Project project) throws Exception {
        if (checkProjectStatus(project)) {
            throw new Exception("This project has been marked as finished.");
        } else {
            project.addActivity(activity);
            activity.setProject(project);
            activities.add(activity);
        }

    }

    // Nellemose - s215469
    public void addEmployeeToActivity(Employee employee, Activity activity) throws Exception {
        if (!loggedIn()) {
            throw new Exception("You need to be logged in.");
        } else if (!activity.getProject().hasProjectManager()) {
            throw new Exception("Project manager has not been chosen yet.");
        } else if (currentLogIn.getId() != activity.getProject().getProjectManager().getId()) {
            throw new Exception("You are not the project manager.");
        } else if (employee.getNumberOfActivities() >= 10) {
            throw new Exception("Chosen employee has too many activities in the given period.");
        } else {
            activity.addEmployee(employee);
        }
    }

    //Kjellberg - s224809
    public void assignProjectManager(Project project, Employee employee) throws Exception {
        if (!project.hasProjectManager()) {
            project.assignProjectManager(employee);
        } else {
            throw new Exception("This project already has a manager");
        }
    }

    // Fischer - s214411
    public void setStartDateActivity(Activity activity, int day, int month, int year) {
        Calendar c = new GregorianCalendar(year, month, day);
        activity.setStartDate(c);
    }

    //Kjellberg - s224809
    public void createProject(String projectName, Employee employee) throws Exception {
        assert loggedIn == true : "You can only make a project if you are logged in!";
        assert projectName != null : "You can only make a project if you have defined its name";

        int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
        idNumber = idNumber + 1;
        String projectId = String.format("%02d%03d", year, idNumber);
        Project project = new Project(projectName, projectId);
        if (employee != null) {
            project.assignProjectManager(employee);
        }
        projects.add(project);

        assert getProjectFromTitle(projectName) != null;
    }

    //Kjellberg - 224809
    public Project getProjectFromTitle(String title) {
        for (Project p : projects) {
            if (p.getTitle().equals(title)) {
                return p;
            }
        }
        return null;
    }

    // Fischer - s214411
    public boolean projectContainsActivity(Project project, Activity activity) {
        return project.getActivities().contains(activity);
    }

    // Nellemose - s215469
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }


    // Fischer - s214411
    public Employee getEmployeeFromId(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }
        return null;
    }

    // Fischer - s214411
    public Activity getActivityFromName(String name) {
        for (Activity a : activities) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    // Fischer - s214411
    public double getEmployeeLatestHours(Employee employee) {
        return employee.getLatestRegisteredHours();
    }

    // Fischer - s214411
    public boolean hasActivity(Activity activity) {
        return activities.contains(activity);
    }

    // Fischer - s214411
    public int getEmployeeNumActivities(Employee employee) {
        return employee.getNumberOfActivities();
    }

    // Fischer - s214411
    public String getManagerId(ProjectManager manager) {
        return manager.getId();
    }

    // Fischer - s214411
    public boolean checkProjectStatus(Project project) {
        return project.isProjectFinished();
    }

    // Fischer - s214411
    public ProjectManager getManager(Project project) {
        return project.getProjectManager();
    }

    // Fischer - s214411
    public void markProjectFinished(Project project) {
        project.markProjectFinished();
    }

    // Fischer - s214411
    public double getMaxDailyHours() {
        return maxDailyHours;
    }

    // Fischer - s214411
    public void setProjectEndDate(Project p, int days, int months, int years) throws Exception {
        if (currentLogIn == null) {
            throw new Exception("you have to be logged in");
        } else if (p.isProjectFinished()) {
            throw new Exception("This project has been marked as finished.");
        } else if (p.hasProjectManager() && !currentLogIn.getId().equals(p.getProjectManager().getId())) {
            throw new Exception("A project manager has been chosen, ask them to change the dates of the project.");
        } else {
            p.setEndDate(days, months, years);
        }

    }

    // Nellemose - s215469
    public void setVacationStatus(Boolean vacation) {
        vacationStatus = vacation;
    }

    //  ## DEMO TO PRESENTATION ##

    // Fischer - s214411
    public void printOverview() {
        int n = 0;
        if (employees.size() > projects.size() && employees.size() > activities.size()) {
            n = employees.size();
        } else if (projects.size() > activities.size()) {
            n = projects.size();
        } else {
            n = activities.size();
        }
        System.out.printf("----------------------------------------------------------%n");
        System.out.printf("           Current status of SoftwareHuset A/S            %n");
        System.out.printf("----------------------------------------------------------%n");
        System.out.printf("| %-16s | %-16s | %-16s |%n", "Projects", "Activities", "Employees");
        System.out.printf("----------------------------------------------------------%n");
        for (int i = 0; i < n; i++) {
            String title = "";
            String name = "";
            String id = "";
            if (i < projects.size()) {
                title = projects.get(i).getTitle();
            }
            if (i < activities.size()) {
                name = activities.get(i).getName();
            }
            if (i < employees.size()) {
                id = employees.get(i).getId();
            }
            System.out.printf("| %-16s | %-16s | %-16s |%n", title, name, id);

        }

    }

    // Fischer - s214411
    public void printWeekly() {
        int n = projects.size();

        System.out.printf("----------------------------------------------------------------------------------%n");
        System.out.printf("                    Current status of projects and activities                     %n");
        System.out.printf("----------------------------------------------------------------------------------%n");
        System.out.printf("| %-16s | %-8s | %-10s | %-16s | %-16s |%n", "Projects", "Manager", "End date", "Activities", "hours remaining");
        System.out.printf("----------------------------------------------------------------------------------%n");

        for (int i = 0; i < n; i++) {
            Project p = projects.get(i);
            String title = "";
            String name = "";
            String manager = "";
            ArrayList<Activity> a = p.getActivities();
            for (int j = 0; j < a.size(); j++) {
                name = a.get(j).getName();
                title = p.getTitle();
                String date = "";
                if (j == 0) {
                    Calendar c = p.getEndDate();
                    if (c != null) {
                        date = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.YEAR);
                    } else {
                        date = "tbd";
                    }
                    if (p.getProjectManager() != null) {
                        manager = p.getProjectManager().getId();
                    } else {
                        manager = "tbd";
                    }
                    System.out.printf("| %-16s | %-8s | %-10s | %-16s | %-16s |%n", title, manager, date, name, "0");
                } else {
                    System.out.printf("| %-16s | %-8s | %-10s | %-16s | %-16s |%n", "", "", "", name, "0");
                }
            }
            System.out.printf("| %-16s | %-8s | %-10s | %-16s | %-16s |%n", "", "", "", "", "");
        }

    }

}