package seedu.traveltrio.command.activity;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;


public abstract class ActivityCommand {
    protected ActivityList activityList;

    public ActivityCommand(ActivityList activityList) {
        this.activityList = activityList;
    }

    public String run(String tripName) throws TravelTrioException {
        if (!activityList.isTripOpen()) {
            throw new TravelTrioException("Please open a trip before managing activities.");
        } else {
            return execute(tripName);
        }
    }

    public abstract String execute(String tripName) throws TravelTrioException;
}
