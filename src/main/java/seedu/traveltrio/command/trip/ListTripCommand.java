package seedu.traveltrio.command.trip;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.TripList;

public class ListTripCommand extends TripCommand {

    public ListTripCommand(TripList tripList) {
        super(tripList);
    }

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

