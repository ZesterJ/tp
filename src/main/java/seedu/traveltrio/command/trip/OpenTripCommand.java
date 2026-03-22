package seedu.traveltrio.command.trip;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

public class OpenTripCommand extends TripCommand {
    private final int index;

    public OpenTripCommand(TripList tripList, int tripNumber) {
        super(tripList);
        this.index = tripNumber - 1;
    }

    @Override
    public String execute() throws TravelTrioException {
        if (index < 0 || index >= tripList.size()) {
            throw new TravelTrioException("Invalid trip index.");
        }
        Trip tripToOpen = tripList.get(index);
        tripToOpen.setOpen(true);
        return "Opened trip: " + tripToOpen.toString();
    }
    
}
