package seedu.duke.command;

import seedu.duke.model.ActivityList;

public abstract class ActivityCommand {

    protected ActivityList activityList;

    private static boolean isTripOpen = false;

    public ActivityCommand(ActivityList activityList) {
        this.activityList = activityList;

        if (!isTripOpen) {
            throw new IllegalStateException("No trip is currently open. Please open a trip to manage activities.");
        }
    }

    public static void setTripOpen(boolean isOpen) {
        isTripOpen = isOpen;
    }

    public abstract String execute(String tripName);

}
