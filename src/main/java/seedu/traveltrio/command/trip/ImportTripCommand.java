package seedu.traveltrio.command.trip;

import seedu.traveltrio.Storage;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

public class ImportTripCommand extends TripCommand{
    private final String fileName;
    private final Storage storage;

    public ImportTripCommand(TripList tripList, String fileName, Storage storage) {
        super(tripList);
        this.fileName = fileName;
        this.storage = storage;
    }

    @Override
    public String execute() throws TravelTrioException {
        Trip importedTrip = storage.importTrip(fileName);
        tripList.add(importedTrip);
        return "Successfully imported new trip: \n" + importedTrip;
    }
}
