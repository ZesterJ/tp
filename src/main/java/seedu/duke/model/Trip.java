package seedu.duke.model;

public class Trip {
    private String destination;
    private String startDate;
    private String endDate;
    private final ActivityList activities;

    public Trip(String destination, String startDate, String endDate) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activities = new ActivityList();
    }

    public String getDestination() {
        return destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public ActivityList getActivities() {
        return activities;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return destination + " (" + startDate + " to " + endDate + ")";
    }

    public String formatForList() {
        String result = destination + "\n";
        result += "   📅 Start: " + startDate + "\n";
        result += "   📅 End:   " + endDate;
        return result;
    }
}
