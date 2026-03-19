package seedu.duke.command;

import seedu.duke.model.TripList;

public class ListTripCommand extends TripCommand {

    public ListTripCommand(TripList tripList) {
        super(tripList);
    }

    @Override
    public String execute() {
        if (tripList.isEmpty()) {
            return "No trips found.";
        }
        StringBuilder sb = new StringBuilder("Here are your trips:\n");
        for (int i = 0; i < tripList.size(); i++) {
            sb.append(i + 1).append(". ").append(tripList.get(i).formatForList());
            if (i < tripList.size() - 1) {
                sb.append("\n\n");
            }
        }
        return sb.toString();
    }
}
