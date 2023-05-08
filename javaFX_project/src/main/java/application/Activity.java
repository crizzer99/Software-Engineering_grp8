package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Activity {
    private int expectedWorkTime;
    private Calendar startDate;
    private Calendar endDate;
    private String name;
    private ArrayList<Employee> employees = new ArrayList<>();
    private Project project;

    public Activity(String name) {
        this.name = name;
    }

    // Fischer - s214411
    public void setStartDate(Calendar c) {
        startDate = c;
    }

    // Fischer - s214411
    public Calendar getStartDate() {
        return startDate;
    }

    // Fischer - s214411
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    // Fischer - s214411
    public String getName() {
        return name;
    }

    // Nellemose - s215469
    public void setProject(Project project) {
        this.project = project;
    }

    // Nellemose - s215469
    public Project getProject() {
        return project;
    }

    // Nellemose - s215469
    public boolean containsEmployee(Employee employee) {
        return employees.contains(employee);
    }
}
