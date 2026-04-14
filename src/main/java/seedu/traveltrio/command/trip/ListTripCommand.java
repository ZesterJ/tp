package seedu.traveltrio.command.trip;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.TripList;

/**
 * Command to list all trips in the trip list.
 * Displays each trip with its name, start date, end date, and total spent (if budget is set).
 */
public class ListTripCommand extends TripCommand {

    /**
     * Constructs a ListTripCommand.
     *
     * @param tripList the list of trips to display
     */
    public ListTripCommand(TripList tripList) {
        super(tripList);
    }

    /**
     * Executes the command to list all trips. Iterates through the trip list and constructs
     * a formatted, enumerated string of all available trips.
     *
     * @return Formatted string containing the enumerated list of all trips.
     * @throws TravelTrioException If the trip list is completely empty.
     */
    @Override
    public String execute() throws TravelTrioException {
        if (tripList.isEmpty()) {
            throw new TravelTrioException("No trips found. Use 'addtrip' to add one!");
        }

        StringBuilder sb = new StringBuilder("Trips:\n");
        for (int i = 0; i < tripList.size(); i++) {
            sb.append(i + 1).append(". ").append(tripList.get(i).formatForList());
            if (i < tripList.size() - 1) {
                sb.append("\n\n");
            }
        }
        return sb.toString();
    }
}

