package seedu.duke.command;

import seedu.duke.model.TripList;

public abstract class TripCommand {
    protected TripList tripList;

    public TripCommand(TripList tripList) {
        this.tripList = tripList;
    }

    public abstract String execute();

}
