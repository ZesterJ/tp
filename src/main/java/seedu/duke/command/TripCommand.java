package seedu.duke.command;

import seedu.duke.model.TripList;

public abstract class TripCommand {
    protected TripList tripList;

    public abstract String execute();

    public TripCommand(TripList tripList) {
        this.tripList = tripList;
    }

}
