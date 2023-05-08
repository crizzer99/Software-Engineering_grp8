package application.Demo;

import application.Activity;
import application.Employee;
import application.Project;
import application.ProjectManagementApp;

import java.util.ArrayList;
import java.util.Scanner;

public class Demo {

    //  ## DEMO TO PRESENTATION ##

    // Fischer - s214411
    public void initializeDemo(ProjectManagementApp pimp) throws Exception {
        Employee jaaf = new Employee("jaaf");
        Employee jakj = new Employee("jakj");
        Employee crne= new Employee("crne");
        Employee magn = new Employee("magn");
        Employee admin = new Employee("admin");
        pimp.addEmployee(jaaf);
        pimp.addEmployee(crne);
        pimp.addEmployee(jakj);
        pimp.addEmployee(magn);
        pimp.addEmployee(admin);
        pimp.employeeLogin(pimp.getEmployeeFromId("admin"));
        pimp.createProject("code", null);
        pimp.createProject("demo", null);
        pimp.createProject("rapport", null);
        pimp.addActivity(new Activity("actTime"), pimp.getProjectFromTitle("code"));
        pimp.addActivity(new Activity("checkHours"), pimp.getProjectFromTitle("code"));
        pimp.addActivity(new Activity("table"), pimp.getProjectFromTitle("demo"));
        pimp.addActivity(new Activity("login"), pimp.getProjectFromTitle("code"));
        pimp.addActivity(new Activity("login"), pimp.getProjectFromTitle("demo"));
        pimp.addActivity(new Activity("wbTests"), pimp.getProjectFromTitle("rapport"));
        pimp.addActivity(new Activity("codeCoverage"), pimp.getProjectFromTitle("rapport"));
        pimp.addActivity(new Activity("SOLID"), pimp.getProjectFromTitle("rapport"));
        pimp.setProjectEndDate(pimp.getProjectFromTitle("code"), 8, 5, 2023);
        pimp.setProjectEndDate(pimp.getProjectFromTitle("demo"), 8, 5, 2023);
        pimp.setProjectEndDate(pimp.getProjectFromTitle("rapport"), 8, 5, 2023);
        pimp.assignProjectManager(pimp.getProjectFromTitle("code"), pimp.getEmployeeFromId("jaaf"));
        pimp.assignProjectManager(pimp.getProjectFromTitle("demo"), pimp.getEmployeeFromId("crne"));
        pimp.assignProjectManager(pimp.getProjectFromTitle("rapport"), pimp.getEmployeeFromId("magn"));
    }

    // Fischer - s214411
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Demo demo = new Demo();
        ProjectManagementApp pimp = new ProjectManagementApp();
        System.out.println("Welcome to the Softwarehuset A/S app demo");
        System.out.println("The following features are currently available:");
        System.out.println("1 - Add employee to company");
        System.out.println("2 - Add project to company");
        System.out.println("3 - Add Activity to project");
        System.out.println("4 - Login");
        System.out.println("5 - Logout");
        System.out.println("6 - assign employee to activity (either as the employee or the project manager)");
        System.out.println("7 - assign employee to be manager of project");
        System.out.println("8 - Register work hours for employee");
        System.out.println("9 - set project end date");
        System.out.println("10 - initialize demo data");
        System.out.println("11 - print overview of company activities");
        System.out.println("12 - get weekly report");
        System.out.println("13 - exit program");

        while (true) {
            int i = scanner.nextInt();
            switch (i) {
                case 1:
                    System.out.print("Please write the id of the employee you want to add: ");
                    String id = scanner.next();
                    pimp.addEmployee(new Employee(id));
                    System.out.println("Employee with id " + id + " has been added to the system");
                    break;
                case 2:
                    System.out.print("What is title of the project you wish to add? ");
                    scanner.nextLine();
                    String title = scanner.nextLine();
                    try {
                        pimp.createProject(title, null);
                        System.out.println("The project with title \"" + title + "\" has been added");
                    } catch (AssertionError error) {
                        System.out.println(error.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("please write the name of the activity: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.print("please write the title of the project the activity should be added to: ");
                    title = scanner.nextLine();
                    try {
                        pimp.addActivity(new Activity(name), pimp.getProjectFromTitle(title));
                        System.out.println("the activity with name " + name + " has been added to project with title " +
                                title);
                    } catch (Exception error) {
                        System.out.println("Oops something went wrong: " + error.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("write your employee id:");
                    pimp.employeeLogin(pimp.getEmployeeFromId(scanner.next()));
                    if (pimp.loggedIn()) {
                        System.out.println("SUCCESSFUL LOGIN");
                    } else {
                        System.out.println("Could not find thus employee");
                    }
                    break;
                case 5:
                    pimp.employeeLogout();
                    System.out.println("LOGGED OUT");
                    break;
                case 6:
                    try {
                        System.out.print("please write the id for the employee: ");
                        Employee e = pimp.getEmployeeFromId(scanner.next());
                        System.out.print("What is the name of the activity? ");
                        scanner.nextLine();
                        name = scanner.nextLine();
                        Activity a = pimp.getActivityFromName(name);
                        pimp.enrollExtraActivity(a);
                        System.out.println("the employee with id " + e.getId() + " has been added to the activity " +
                                a.getName());
                    } catch (Exception error) {
                        System.out.println("Oops something went wrong: " + error.getMessage());
                    }
                    break;
                case 7:
                    try {
                        System.out.print("please write the id for the employee: ");
                        Employee e = pimp.getEmployeeFromId(scanner.next());
                        System.out.print("What is the title of the project? ");
                        System.out.print("title: ");
                        scanner.nextLine();
                        title = scanner.nextLine();
                        pimp.assignProjectManager(pimp.getProjectFromTitle(title), e);
                        System.out.println("the employee with id " + e.getId() + " has been promoted to project manager for " +
                                "the project \"title\"");
                    } catch (Exception error) {
                        System.out.println("Oops something went wrong: " + error.getMessage());
                    }
                    break;
                case 8:
                    try {
                        System.out.print("how many hours do you wish to register? ");
                        double hours = scanner.nextDouble();
                        pimp.registerHours(hours);
                        System.out.println("You have registered " + hours + " hours.");
                    } catch (Exception error) {
                        System.out.println("Oops something went wrong: " + error.getMessage());
                    }
                    break;
                case 9:
                    try {
                        System.out.print("What is the title of the project? ");
                        scanner.nextLine();
                        title = scanner.nextLine();
                        Project p = pimp.getProjectFromTitle(title);
                        System.out.print("write the day month and year the project has to end (ex: \"5 10 2023\"): ");
                        int day = scanner.nextInt();
                        int month = scanner.nextInt();
                        int year = scanner.nextInt();
                        pimp.setProjectEndDate(p, day, month, year);
                        System.out.println("The end date has been set");
                    } catch (Exception error) {
                        System.out.println("Oops something went wrong: " + error.getMessage());
                    }
                    break;
                case 10:
                    try {
                        demo.initializeDemo(pimp);
                    } catch (Exception error) {
                        System.out.println("Oops something went wrong: " + error.getMessage());
                    }
                    break;
                case 11:
                    try {
                        pimp.printOverview();
                    } catch (Exception error) {
                        System.out.println("Oops something went wrong: " + error.getMessage());
                    }
                    break;
                case 12:
                    try {
                        pimp.printWeekly();
                    } catch (Exception error) {
                        System.out.println("Oops something went wrong: " + error.getMessage());
                    }
                    break;
                case 13:
                    return;
                default:
                    System.out.println("This doesn't seem to be a valid function, please try again");
            }
        }
    }
}
