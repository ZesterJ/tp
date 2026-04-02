package seedu.traveltrio.command.trip;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.TripList;

public class DeleteTripCommand extends TripCommand {
    private final int index;

    public DeleteTripCommand(TripList tripList, int tripNumber) {
        super(tripList);
        this.index = tripNumber - 1;
    }

    @Override
    public String execute() throws TravelTrioException {
        if (index < 0 || index >= tripList.size()) {
            throw new TravelTrioException("Invalid trip index.");
        }
        String removedTrip = tripList.get(index).toString();
        tripList.remove(index);
        assert tripList.size() >= 0 : "Trip list size should not be negative after deletion.";

        return "Deleted trip: " + removedTrip;
    }
    
}
