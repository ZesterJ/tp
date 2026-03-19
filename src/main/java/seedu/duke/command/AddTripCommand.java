package seedu.duke.command;

import seedu.duke.model.TripList;
import seedu.duke.model.Trip;

public class AddTripCommand extends TripCommand {
    private final String destination;
    private final String startDate;
    private final String endDate;

    public AddTripCommand(TripList tripList, String destination, String startDate, String endDate) {
        super(tripList);
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String execute() {
        if (destination == null || destination.isBlank()) {
            return "❌ Trip destination cannot be empty.";
        }
        if (startDate == null || endDate == null) {
            return "❌ Start date and end date must be provided.";
        }
        if (startDate.compareTo(endDate) > 0) {
            return "❌ Start date must not be later than end date.";
        }

        Trip trip = new Trip(destination, startDate, endDate);
        tripList.add(trip);
        return "New trip added:\n" + trip;
    }
}
