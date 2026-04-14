package seedu.traveltrio.command.trip;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.TripList;

/**
 * Command to delete a trip from the trip list by index.
 */
public class DeleteTripCommand extends TripCommand {

    private final int index;

    /**
     * Constructs a DeleteTripCommand with the specified trip index.
     *
     * @param tripList the list of trips to delete from
     * @param tripNumber the 1-based index of the trip to delete
     */
    public DeleteTripCommand(TripList tripList, int tripNumber) {
        super(tripList);
        this.index = tripNumber - 1;
    }

    /**
     * Executes the command to delete the trip. Validates that the index is within the bounds
     * of the current trip list before attempting removal.
     *
     * @return Formatted string confirming the successful deletion of the trip.
     * @throws TravelTrioException If the provided trip index is out of bounds.
     */
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
