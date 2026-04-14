package seedu.traveltrio.command.trip;

import seedu.traveltrio.Storage;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

/**
 * Command to import a trip from a file.
 */
public class ImportTripCommand extends TripCommand{

    private final String fileName;
    private final Storage storage;

    /**
     * Constructs an ImportTripCommand with the specified file name and storage.
     *
     * @param tripList the list of trips to add to
     * @param fileName the name of the file to import from
     * @param storage the storage handler for importing the trip
     */
    public ImportTripCommand(TripList tripList, String fileName, Storage storage) {
        super(tripList);
        this.fileName = fileName;
        this.storage = storage;
    }

    /**
     * Executes the command to import the trip from the text file.
     * Adds the successfully parsed trip into the application's active trip list.
     *
     * @return Formatted string confirming the successful import of the new trip.
     * @throws TravelTrioException If the file cannot be found, read, or if the internal data format is invalid.
     */
    @Override
    public String execute() throws TravelTrioException {
        Trip importedTrip = storage.importTrip(fileName);
        tripList.add(importedTrip);
        return "Successfully imported new trip: \n" + importedTrip;
    }
}
