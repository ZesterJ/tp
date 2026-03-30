package seedu.traveltrio.command.trip;

import seedu.traveltrio.Storage;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

public class ExportTripCommand extends TripCommand{
    private final int index;
    private final String fileName;
    private final Storage storage;

    public ExportTripCommand(TripList tripList, int tripNumber, String fileName, Storage storage) {
        super(tripList);
        this.index = tripNumber - 1;
        this.fileName = fileName;
        this.storage = storage;
    }

    @Override
    public String execute() throws TravelTrioException {
        if (index < 0 || index >= tripList.size()) {
            throw new TravelTrioException("Invalid trip index.");
        }
        Trip tripToExport = tripList.get(index);

        storage.exportTrip(tripToExport, fileName);

        return "Successfully exported '" + tripToExport.getName() + "' to " + fileName;
    }
}
