package seedu.duke.command;
import seedu.duke.model.Trip;
import seedu.duke.model.TripList;

public class OpenTripCommand extends TripCommand {
    private final int index;

    public OpenTripCommand(TripList tripList, int tripNumber) {
        super(tripList);
        this.index = tripNumber - 1;
    }

    @Override
    public String execute() {
        if (index < 0 || index >= tripList.size()) {
            return "Invalid trip index.";
        }
        Trip tripToOpen = tripList.get(index);
        ActivityCommand.setTripOpen(true);
        return "Opened trip: " + tripToOpen.getDestination();
    }
    
}
