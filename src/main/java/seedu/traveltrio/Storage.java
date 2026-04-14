package seedu.traveltrio;

import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.budget.Budget;
import seedu.traveltrio.model.packing.PackingItem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Handles loading and saving trip data to a local file.
 * File stores all trip, activity and budget details.
 */
public class Storage {

    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    private static final Ui ui = new Ui();

    private final String filePath;

    /**
     * Initializes a Storage object with the specified file path.
     *
     * @param filePath The path of the default file where application data is saved and loaded.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the list of trips and activities from the txt file from the previous session.
     *
     * @return A populated TripList or an empty one if no file exists.
     * @throws TravelTrioException if unable to read file.
     */
    public TripList load() throws TravelTrioException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new TripList();
        }
        return parseFileToTripList(file);
    }

    /**
     * Imports a shared trip from a specified external text file.
     *
     * @param fileName The name or path of the file to import the trip from.
     * @return The first Trip successfully parsed from the shared file.
     * @throws TravelTrioException If the file is not found, or if no valid trip data can be extracted.
     */
    public Trip importTrip(String fileName) throws TravelTrioException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new TravelTrioException("File '" + fileName + "' not found. Make sure it is in the same folder.");
        }

        TripList importedTrips = parseFileToTripList(file);

        if (importedTrips.isEmpty()) {
            throw new TravelTrioException("No valid trip data found in " + fileName);
        }

        return importedTrips.get(0); // Return the first trip found in the shared file
    }

    /**
     * Exports a specific trip's data to a standalone text file for sharing or backup.
     * Automatically appends the ".txt" extension if the user did not provide it.
     *
     * @param trip The Trip object to be exported.
     * @param fileName The name or path of the target export file.
     * @throws TravelTrioException If an I/O error occurs during the file writing process.
     */
    public void exportTrip(Trip trip, String fileName) throws TravelTrioException {
        try {
            File file = new File(fileName);
            // Append .txt if the user forgot it
            if (!fileName.endsWith(".txt")) {
                file = new File(fileName + ".txt");
            }

            FileWriter writer = new FileWriter(file);
            writer.write(trip.toFileFormat());
            writer.close();

        } catch (IOException e) {
            logger.log(Level.FINE, "Failed to export trip. " + e);
            throw new TravelTrioException("Failed to export trip to " + fileName + ".");
        }
    }

    /**
     * Parses the contents of a given file and reconstructs the trip data into a TripList.
     *
     * @param file The File object to read from.
     * @return A TripList containing all the parsed trips, activities, budgets, and packing items.
     * @throws TravelTrioException If the file contains invalid formatting or corrupted data.
     */
    private TripList parseFileToTripList(File file) throws TravelTrioException {
        TripList trips = new TripList();
        try (Scanner fileScanner = new Scanner(file)) {
            Trip currentTrip = null;
            Activity lastActivity = null;
            String currentDate = "";

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("***") || line.startsWith("---")) {
                    continue;
                }

                if (line.startsWith("Trip:")) {
                    currentTrip = loadTripDetails(fileScanner, line, trips);
                } else if (line.startsWith("=== Date:")) {
                    currentDate = loadDates(line);
                } else if (line.startsWith("Title:")) {
                    if (currentTrip == null) {
                        throw new TravelTrioException("Activity found without Trip header.");
                    }
                    lastActivity = loadActivityDetails(fileScanner, line, currentDate, currentTrip);
                } else if (line.contains("Budget set:")) {
                    assert currentTrip != null : "No trip open!";
                    loadBudgetDetails(lastActivity, line, fileScanner, currentTrip);
                } else if (line.startsWith("Packing List")) {
                    continue;
                } else if (line.matches("[01]\\|.*")) {
                    if (currentTrip != null) {
                        String[] parts = line.split("\\|", 2);
                        boolean isPacked = parts[0].equals("1");
                        seedu.traveltrio.model.packing.PackingItem item = new PackingItem(parts[1]);
                        if (isPacked) {
                            item.markPacked();
                        }
                        currentTrip.getPackingList().addItem(item);
                    }
                } else if (!line.startsWith("Total Budget:")) {
                    ui.showError("Line wrongly formatted found. [" + line + "]. Check formatting.");
                }
            }

            for (int i = 0; i < trips.size(); i++) {
                trips.get(i).getBudgets().recalculateTotalExpense();
            }
        } catch (Exception e) {
            logger.log(Level.FINE, "Failed to load trips from " + file.getPath(), e);
            throw new TravelTrioException("Error reading file. File might be corrupted.");
        }
        return trips;
    }

    /**
     * Parses and loads budget details from the file scanner and attaches them to the most recently parsed activity.
     *
     * @param lastActivity The Activity to which this budget belongs.
     * @param line The current string line containing the total budget.
     * @param fileScanner The Scanner reading the file.
     * @param currentTrip The current Trip being populated.
     * @throws TravelTrioException If the numerical parsing fails.
     */
    private static void loadBudgetDetails(Activity lastActivity, String line, Scanner fileScanner, Trip currentTrip)
            throws TravelTrioException {
        assert lastActivity != null : "Storage Error: " +
                "Budget data found without a preceding Activity block.";

        double total = Double.parseDouble(line.split(": ")[1].trim());
        String expenseLine = fileScanner.nextLine(); // Read the next line for Actual Expense
        double spent = Double.parseDouble(expenseLine.split(": ")[1].trim());

        Budget budget = new Budget(total, lastActivity);
        budget.setActualExpense(spent);

        currentTrip.getBudgets().addBudget(lastActivity, budget);
    }

    /**
     * Parses and loads activity details from the file scanner and adds the new activity to the current trip.
     *
     * @param fileScanner The Scanner reading the file.
     * @param titleLine The line containing the activity title.
     * @param currentDate The date associated with this activity.
     * @param currentTrip The current Trip being populated.
     * @return The newly constructed Activity.
     * @throws TravelTrioException If the date or time formats are invalid.
     */
    private static Activity loadActivityDetails(Scanner fileScanner, String titleLine, String currentDate,
                                                Trip currentTrip) throws TravelTrioException {
        String title = titleLine.split(": ")[1].trim();
        String loc = fileScanner.nextLine().split(": ")[1].trim();
        String start = fileScanner.nextLine().split(": ")[1].trim();
        String end = fileScanner.nextLine().split(": ")[1].trim();

        Activity lastActivity = new Activity(title, loc, currentDate, start, end);
        currentTrip.getActivities().add(lastActivity);
        return lastActivity;
    }

    /**
     * Extracts the date string from a formatted date header line in the save file.
     *
     * @param line The string line containing the date header.
     * @return The extracted date string.
     */
    private static String loadDates(String line) {
        String currentDate;
        currentDate = line.replace("===", "").replace("Date:", "").trim();
        return currentDate;
    }

    /**
     * Parses and loads the core details of a trip and adds it to the trip list.
     * Also checks for and applies any saved exchange rates.
     *
     * @param fileScanner The Scanner reading the file.
     * @param line The string line containing the basic trip details.
     * @param trips The TripList to append the new trip to.
     * @return The newly constructed Trip.
     */
    private static Trip loadTripDetails(Scanner fileScanner, String line, TripList trips) {
        Trip currentTrip;
        String[] parts = line.split(" \\| ");
        String name = parts[0].substring(parts[0].indexOf(":") + 1).trim();
        String start = parts[1].substring(parts[1].indexOf(":") + 1).trim();
        String end = parts[2].substring(parts[2].indexOf(":") + 1).replace("|", "").trim();
        currentTrip = new Trip(name, start, end);
        trips.add(currentTrip);

        // Read the next line for budget and exchange rate details
        if (fileScanner.hasNext()) {
            String budgetLine = fileScanner.nextLine().trim();
            if (budgetLine.contains("Exchange Rate:")) {
                String[] budgetParts = budgetLine.split(" \\| ");
                for (String part : budgetParts) {
                    if (part.contains("Exchange Rate:")) {
                        double exchangeRate = Double.parseDouble(part.split(": ")[1].trim());
                        currentTrip.getBudgets().setExchangeRate(exchangeRate);
                        break;
                    }
                }
            }
        }
        return currentTrip;
    }

    /**
     * Saves the current list of trips and all their associated data to the default local file.
     * Creates any necessary parent directories if they do not exist.
     *
     * @param trips The TripList containing the data to be saved.
     * @throws TravelTrioException If an I/O error occurs or the directory is read-only.
     */
    public void save(TripList trips) throws TravelTrioException {
        try {
            File file = new File(filePath);
            File directory = file.getParentFile();

            if (directory != null && !directory.exists()) {
                directory.mkdirs();
            }

            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < trips.size(); i++) {
                // Handles the T, A, and B lines
                writer.write(trips.get(i).toFileFormat());

                if (i < trips.size() - 1) {
                    writer.write("\n\n\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            logger.log(Level.FINE, "Failed to save trips. " + e);
            throw new TravelTrioException("Data not saved. Please check if the folder is read-only.");
        }
    }

}
