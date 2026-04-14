package seedu.traveltrio.command.trip;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

/**
 * Command to open a trip for editing.
 * Ensures only one trip is open at a time by closing any previously open trip.
 */
public class OpenTripCommand extends TripCommand {

    private final int index;

    /**
     * Constructs an OpenTripCommand with the specified trip index.
     *
     * @param tripList the list of trips to select from
     * @param tripNumber the 1-based index of the trip to open
     */
    public OpenTripCommand(TripList tripList, int tripNumber) {
        super(tripList);
        this.index = tripNumber - 1;
    }

    /**
     * Executes the command to open the selected trip. Iterates through the trip list to ensure
     * no other trips remain open, then sets the target trip's status to active.
     *
     * @return Formatted string confirming the successful opening of the target trip.
     * @throws TravelTrioException If the provided trip index is out of bounds.
     */
    @Override
    public String execute() throws TravelTrioException {
        if (index < 0 || index >= tripList.size()) {
            throw new TravelTrioException("Invalid trip index.");
        }
        Trip tripToOpen = tripList.get(index);

        // Close any currently open trip
        for (int i = 0; i < tripList.size(); i++) {
            Trip trip = tripList.get(i);
            if (trip.isOpen()) {
                trip.setOpen(false);
            }
        }

        tripToOpen.setOpen(true);
        return "Opened trip: " + tripToOpen.toString();
    }

}
