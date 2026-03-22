package seedu.traveltrio.command.trip;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.TripList;

public abstract class TripCommand {

    protected TripList tripList;

    public TripCommand(TripList tripList) {
        this.tripList = tripList;
    }
    
    public abstract String execute() throws TravelTrioException;

}

