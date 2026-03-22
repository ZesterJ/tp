package seedu.traveltrio.command.trip;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

public class AddTripCommand extends TripCommand {

    private final String name;
    private final String startDate;
    private final String endDate;

    public AddTripCommand(TripList tripList, String name, String startDate, String endDate) {
        super(tripList);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

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

