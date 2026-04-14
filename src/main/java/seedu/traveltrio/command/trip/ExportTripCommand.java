package seedu.traveltrio.command.trip;

import seedu.traveltrio.Storage;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

/**
 * Command to export a trip to a file.
 */
public class ExportTripCommand extends TripCommand{

    private final int index;
    private final String fileName;
    private final Storage storage;

    /**
     * Constructs an ExportTripCommand with the specified trip index, file name, and storage.
     *
     * @param tripList the list of trips to export from
     * @param tripNumber the 1-based index of the trip to export
     * @param fileName the name of the file to export to
     * @param storage the storage handler for exporting the trip
     */
    public ExportTripCommand(TripList tripList, int tripNumber, String fileName, Storage storage) {
        super(tripList);
        this.index = tripNumber - 1;
        this.fileName = fileName;
        this.storage = storage;
    }

    /**
     * Executes the command to export the trip to a text file. Validates that the specified
     * trip index is within the bounds of the trip list before attempting the export.
     *
     * @return Formatted string confirming the successful export of the trip.
     * @throws TravelTrioException If the provided trip index is out of bounds
     *                             or if an error occurs during file writing.
     */
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
