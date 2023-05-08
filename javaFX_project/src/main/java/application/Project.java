package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Project {

    private boolean finished = false;
    private String title = "";
    private String id = "";

    private ProjectManager manager;

    private ArrayList<Activity> activities = new ArrayList<>();

    //private Calendar startDate;
    private Calendar endDate;

    public Project(String title, String id) {
        this.title = title;
        this.id = id;
    }

    // Fischer - s214411
    public void assignProjectManager(Employee employee) {
        if (employee != null) {
            manager = new ProjectManager(employee.getId());    
        }
    }

    // Nellemose - s215469
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    // Fischer - s214411
    public boolean hasProjectManager() {
        if (manager == null || manager.getId() == "") {
            return false;
        } else {
            return true;
        }
    }
    
    // Nellemose - s215469
    public ProjectManager getProjectManager() {
        return manager;
    }

    // Fischer - s214411
    public boolean isProjectFinished() {
        return finished;
    }

    // Fischer - s214411
    public void markProjectFinished() {
        finished = true;
    }

    // Nellemose - s215469
    public ArrayList<Activity> getActivities() {
        return activities;
    }

    // Fischer - s214411
    public void setEndDate(int day, int month, int year) {
        endDate = new GregorianCalendar(year,month,day);
    }

    // Fischer - s214411
    public Calendar getEndDate() {
        return endDate;
    }

    // Fischer - s214411
    public String getTitle() {
        return title;
    }

}


