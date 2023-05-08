package application;
import io.cucumber.java.en_old.Ac;

import java.util.ArrayList;
import java.util.Calendar;

public class Employee {

    private String id;
    private double[][] hours = new double[13][32];
    private double monthlyHours;
    private double latestRegisteredHours;
    private ArrayList<Activity> activities = new ArrayList<>();
    private int numActivities = 0;

    public Employee(String id) {
        this.id = id;
    }

    // Nellemose - s215469
    public void registerHours(double hours, int day, int month) {
        this.hours[month][day] += hours;
        monthlyHours += hours;
        latestRegisteredHours = hours;
    }

    // Fischer - s214411
    public void enrollExtraActivity(Activity activity) {
        activity.addEmployee(this);
        activities.add(activity);
    }

    // Fischer - s214411
    public boolean containsActivity(Activity activity) {
        return activities.contains(activity);
    }

    // Fischer - s214411
    public int getNumberOfActivities() {
        return numActivities;
    }

    // Fischer - s214411
    public String getId() {
        return id;
    }

    // Fischer - s214411
    public double getLatestRegisteredHours() {
        return latestRegisteredHours;
    }

    // Fischer - s214411
    public void setNumActivities(int activities) {
        numActivities = activities;
    }

    // Fischer - s214411
    public double getHours(int day, int month) {
        return hours[month][day];
    }

    // Fischer - s214411
    public double getMonthlyHours() {
        return monthlyHours;
    }
}
