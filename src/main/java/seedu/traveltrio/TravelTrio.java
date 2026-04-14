package seedu.traveltrio;

import seedu.traveltrio.model.trip.TripList;

/**
 * Main entry point for the TravelTrio application.
 * This class initializes core components and runs the main command loop.
 */
public class TravelTrio {

    private static final String DATA_FILE_PATH = "./data/traveltrio.txt";
    private static final Storage storage = new Storage(DATA_FILE_PATH);
    private static final Ui ui = new Ui();
    private static TripList tripList;

    /**
     * Main method that drives the application. Starts by loading previous data
     * Displays a welcome message upon entering and enters a loop to read user
     * inputs until user exits.
     *
     * @param args Command line arguments passed at execution.
     */
    public static void main(String[] args) throws TravelTrioException {
        assert DATA_FILE_PATH != null : "Configuration Error: DATA_FILE_PATH is null.";

        ui.showWelcomeMessage();

        try {
            tripList = storage.load();
        } catch (TravelTrioException e) {
            ui.showError("Could not load data: " + e.getMessage());
            tripList = new TripList(); // Fallback to avoid NullPointerException
        }

        if (tripList.isEmpty()) {
            ui.showMessage("No previous data found. Starting a new journey!");
        } else {
            ui.showMessage("Loaded " + tripList.size() + " trips from memory.");
        }

        CommandProcessor processor = new CommandProcessor(tripList, ui, storage);

        while (true) {
            try {
                String command = ui.readCommand(processor.getOpenTripName());
                if (command.equals("exit")) {
                    handleExit();
                    break;
                }
                processor.process(command);
                storage.save(tripList);
            } catch (TravelTrioException e) {
                if (e.getMessage().equals("End of input reached.")) {
                    ui.showMessage("Input ended. Saving and exiting...");
                    handleExit();
                    break;
                }
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Something went wrong: " + e.getMessage());
            }
        }

    }

    /**
     * Performs cleanup and final data persistence before the application shuts down.
     *
     * @throws TravelTrioException If an error occurs while saving data to the local file.
     */
    private static void handleExit() throws TravelTrioException {
        System.out.println();
        ui.showMessage("Saving your travels...");
        storage.save(tripList);
        ui.showMessage("Goodbye! Happy Travels!");
    }
}
