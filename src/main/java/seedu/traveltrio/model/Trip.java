package seedu.traveltrio.model;

public class Trip {
    private String name;
    private String startDate;
    private String endDate;
    private final ActivityList activities;

    public Trip(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activities = new ActivityList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ActivityList getActivities() {
        return activities;
    }

    @Override
    public String toString() {
        return name + " (" + startDate + " to " + endDate + ")";
    }

    public String formatForList() {
        String result = name + "\n";
        result += "   📅 Start: " + startDate + "\n";
        result += "   📅 End:   " + endDate;
        return result;
    }
}

