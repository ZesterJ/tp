package seedu.traveltrio.command;

import seedu.traveltrio.model.Trip;
import seedu.traveltrio.model.TripList;

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
    public String execute() {
        if (name == null || name.isBlank()) {
            return "❌ Trip name cannot be empty.";
        }
        if (startDate == null || endDate == null) {
            return "❌ Start date and end date must be provided.";
        }
        if (startDate.compareTo(endDate) > 0) {
            return "❌ Start date must not be later than end date.";
        }

        assert tripList != null : "tripList should be initialized before calling execute()";

        Trip trip = new Trip(name, startDate, endDate);
        tripList.add(trip);
        return "New trip added:\n" + trip;
    }
}

