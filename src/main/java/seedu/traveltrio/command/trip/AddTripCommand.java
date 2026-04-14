package seedu.traveltrio.command.trip;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

/**
 * Command to add a new trip to the trip list.
 * Validates that the trip name is non-empty, dates are provided,
 * and the start date is before or equal to the end date.
 */
public class AddTripCommand extends TripCommand {

    private final String name;
    private final String startDate;
    private final String endDate;

    /**
     * Constructs an AddTripCommand with the specified trip details.
     *
     * @param tripList the list of trips to add to
     * @param name the name of the trip
     * @param startDate the start date of the trip
     * @param endDate the end date of the trip
     */
    public AddTripCommand(TripList tripList, String name, String startDate, String endDate) {
        super(tripList);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Executes the command to add the new trip. Validates that the trip name is not empty
     * and that the start date is chronologically before or equal to the end date.
     *
     * @return Formatted string confirming the successful addition of the new trip.
     * @throws TravelTrioException If the trip name is empty, dates are missing,
     *                             or the start date is after the end date.
     */
    @Override
    public String execute() throws TravelTrioException {
        if (name == null || name.isBlank()) {
            throw new TravelTrioException("Trip name cannot be empty.");
        }
        if (startDate == null || endDate == null) {
            throw new TravelTrioException("Start date and end date must be provided.");
        }
        if (startDate.compareTo(endDate) > 0) {
            throw new TravelTrioException("Start date must not be later than end date.");
        }

        assert tripList != null : "tripList should be initialized before calling execute()";

        Trip trip = new Trip(name, startDate, endDate);
        tripList.add(trip);
        return "New trip added:\n" + trip;
    }
}

