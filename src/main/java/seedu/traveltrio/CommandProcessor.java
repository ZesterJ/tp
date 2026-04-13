package seedu.traveltrio;

import seedu.traveltrio.command.activity.AddRemarkCommand;
import seedu.traveltrio.command.activity.AddActivityCommand;
import seedu.traveltrio.command.activity.DeleteActivityCommand;
import seedu.traveltrio.command.activity.EditActivityCommand;
import seedu.traveltrio.command.activity.ListActivityCommand;
import seedu.traveltrio.command.activity.NextActivityCommand;
import seedu.traveltrio.command.finance.budget.SetBudgetCommand;
import seedu.traveltrio.command.finance.budget.BudgetSummaryCommand;
import seedu.traveltrio.command.finance.budget.BudgetChartCommand;
import seedu.traveltrio.command.finance.budget.SetCurrencyCommand;
import seedu.traveltrio.command.finance.expense.ListExpenseCommand;
import seedu.traveltrio.command.finance.expense.SetDailyLimitCommand;
import seedu.traveltrio.command.finance.expense.SetExpenseCommand;
import seedu.traveltrio.command.trip.AddTripCommand;
import seedu.traveltrio.command.trip.DeleteTripCommand;
import seedu.traveltrio.command.trip.ListTripCommand;
import seedu.traveltrio.command.trip.OpenTripCommand;
import seedu.traveltrio.command.trip.ImportTripCommand;
import seedu.traveltrio.command.trip.ExportTripCommand;
import seedu.traveltrio.command.packing.AddItemCommand;
import seedu.traveltrio.command.packing.CheckItemCommand;
import seedu.traveltrio.command.packing.DeleteItemCommand;
import seedu.traveltrio.command.packing.ListItemsCommand;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.command.others.HelpCommand;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

import java.util.logging.Logger;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

/**
 * Handles the logic for routing user commands to their respective actions.
 * Maintains the state of the currently opened trip.
 */
public class CommandProcessor {

    private static final Logger logger = Logger.getLogger(CommandProcessor.class.getName());

    private final TripList tripList;
    private final Ui ui;
    private final Storage storage;
    private Trip openTrip = null;

    /**
     * Constructs a CommandProcessor with a shared trip list and UI.
     *
     * @param tripList The global list of trips.
     * @param ui       User interface for interaction.
     */
    public CommandProcessor(TripList tripList, Ui ui, Storage storage) {
        this.tripList = tripList;
        this.ui = ui;
        this.storage = storage;
    }

    /**
     * Main entry point for command execution.
     * Routes command string to the specific handler methods.
     *
     * @param command The raw command entered by user.
     */
    public void process(String command) {
        logger.log(Level.FINE, "Processing command: {0}", command);
        try {
            switch (command) {
            case "addtrip":
                handleAddTrip();
                break;
            case "listtrip":
                printTripList();
                break;
            case "opentrip":
                handleOpenTrip();
                break;
            case "deletetrip":
                handleDeleteTrip();
                break;
            case "exporttrip":
                handleExportTrip();
                break;
            case "importtrip":
                handleImportTrip();
                break;
            case "addactivity":
                handleAddActivity();
                break;
            case "listactivity":
                handleListActivity();
                break;
            case "editactivity":
                handleEditActivity();
                break;
            case "deleteactivity":
                handleDeleteActivity();
                break;
            case "nextactivity":
                handleNextActivity();
                break;
            case "setbudget":
                handleSetBudget();
                break;
            case "setcurrency":
                handleSetCurrency();
                break;
            case "budgetsummary":
                handleBudgetSummary();
                break;
            case "budgetchart":
                handleBudgetChart();
                break;
            case "setexpense":
                handleSetExpense();
                break;
            case "listexpense":
                handleListExpense();
                break;
            case "setdailylimit":
                handleSetDailyLimit();
                break;
            case "additem":
                handleAddItem();
                break;
            case "listitems":
                handleListItems();
                break;
            case "checkitem":
                handleCheckItem();
                break;
            case "deleteitem":
                handleDeleteItem();
                break;
            case "help":
                handleHelp();
                break;
            case "addremark":
                handleAddRemark();
                break;
            default:
                handleUnknownCommand();
            }
        } catch (TravelTrioException e) {
            ui.showError(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            ui.showError("That index is not in the list. Please check the list and try again.");
        } catch (Exception e) {
            ui.showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Displays an error message listing all valid commands when an unknown command is entered.
     */
    private void handleUnknownCommand() {
        ui.showError("Unknown command.\n" +
                "Commands: \n" +
                "addtrip, listtrip, opentrip, deletetrip, exporttrip, importtrip, \n" +
                "addactivity, listactivity, editactivity, deleteactivity, nextactivity, addremark, \n" +
                "setbudget, setexpense, setcurrency, setdailylimit, budgetsummary, listexpense, budgetchart, \n" +
                "additem, listitems, checkitem, deleteitem, \n" +
                "exit");
    }

    /**
     * Handles the generation and display of the budget summary for the open trip.
     *
     * @throws TravelTrioException If no trip is open.
     */
    private void handleBudgetSummary() throws TravelTrioException {
        ensureTripOpen();
        ui.showMessageWithDivider(new BudgetSummaryCommand(openTrip.getBudgets(),
                openTrip.getActivities()).run());
    }

    /**
     * Guides the user through setting a budget for a specific activity.
     *
     * @throws TravelTrioException If no trip is open or the activity list is empty.
     */
    private void handleSetBudget() throws TravelTrioException {
        ensureTripOpen();
        if (openTrip.getActivities().isEmpty()) {
            throw new TravelTrioException("No activities found. Please add an activity before setting a budget.");
        }
        printActivityList();
        int budgetActivityIdx = ui.promptInt("Enter the index of the activity to add a budget for");
        double budgetAmount = ui.promptDouble("Enter budget amount ($)");
        ui.showMessage(new SetBudgetCommand(openTrip.getBudgets(),
                openTrip.getActivities(), openTrip.getActivities().get(budgetActivityIdx - 1), budgetAmount)
                .run());
    }

    /**
     * Handles the generation and display of the visual budget chart.
     *
     * @throws TravelTrioException If no trip is open.
     */
    private void handleBudgetChart() throws TravelTrioException {
        ensureTripOpen();

        ui.showMessageWithDivider(new BudgetChartCommand(openTrip.getBudgets(), openTrip.getActivities()).execute());
    }

    /**
     * Guides the user through updating the currency exchange rate for the trip.
     *
     * @throws TravelTrioException If no trip is open.
     */
    private void handleSetCurrency() throws TravelTrioException {
        ensureTripOpen();
        double exchangeRate = ui.promptDouble("Current exchange rate is 1 Foreign Currency = " 
                + openTrip.getBudgets().getExchangeRate() 
                + " Home Currency.\nEnter the exchange rate (1 Foreign Currency = ? Home Currency)");
        ui.showMessage(new SetCurrencyCommand(openTrip.getBudgets(),
                openTrip.getActivities(), null, exchangeRate).run());
    }

    /**
     * Guides the user through setting an actual expense for a specific activity.
     *
     * @throws TravelTrioException If no trip is open or no activities exist.
     */
    private void handleSetExpense() throws TravelTrioException {
        ensureTripOpen();
        if (openTrip.getActivities().isEmpty() || openTrip.getBudgets().isEmpty()) {
            throw new TravelTrioException("Activity list is empty! No activity to set expense for now...");
        }
        printActivityList();
        ActivityList activities = openTrip.getActivities();
        int activityIdx;
        while (true) {
            String input = ui.promptField("Enter the activity index to set actual spending "
                    + "(or type 'exit' to cancel)");

            if (input.equalsIgnoreCase("exit")) {
                ui.showMessage("Set expense cancelled.");
                return;
            }

            try {
                activityIdx = Integer.parseInt(input);
                if (activityIdx < 1 || activityIdx > activities.size()) {
                    ui.showError("Selected activity index is out of range. Please enter a number from 1 to "
                            + activities.size() + ".");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                ui.showError("Please enter a valid activity index, or type 'exit' to cancel.");
            }

        }

        int currencyChoice = ui.promptInt("Is the amount in foreign currency? (1 for Yes, 0 for No)");
        if (currencyChoice != 0 && currencyChoice != 1) {
            throw new TravelTrioException("Invalid choice for currency. Please enter 1 for Yes or 0 for No.");
        }
        
        boolean isForeignCurrency = currencyChoice == 1;
        double actualAmount = ui.promptDouble("Enter amount spent ($)");
        String successMessage = new SetExpenseCommand(
                openTrip.getBudgets(),
                openTrip.getActivities(),
                openTrip.getActivities().get(activityIdx - 1),
                actualAmount,
                isForeignCurrency
        ).execute();

        ui.showMessage(successMessage);
    }

    /**
     * Returns the name of the currently opened trip.
     *
     * @return The open trip name, or null if no trip is currently open.
     */
    public String getOpenTripName() {
        return openTrip == null ? null : openTrip.getName();
    }

    /**
     * Handles listing the chronological breakdown of all logged expenses.
     *
     * @throws TravelTrioException If no trip is open.
     */
    private void handleListExpense() throws TravelTrioException {
        ensureTripOpen();
        if (openTrip.getActivities().isEmpty()) {
            ui.showMessage("You have not create any activities for your trip. No expenses to list.");
            return;
        }
        if (openTrip.getBudgets().isEmpty()) {
            ui.showMessage("You have not create any budget for your activities yet.");
            return;
        }
        String resultString = new ListExpenseCommand(openTrip).execute();

        ui.showMessageWithDivider(resultString);
    }

    /**
     * Guides the user through deleting a specific activity from the itinerary.
     *
     * @throws TravelTrioException If no trip is open.
     */
    private void handleDeleteActivity() throws TravelTrioException {
        ensureTripOpen();
        printActivityList();
        int actIdx = ui.promptInt("Enter the index of the activity to delete");
        ui.showMessage(new DeleteActivityCommand(openTrip.getActivities(), openTrip.getBudgets(), actIdx)
                .run(openTrip.getName()));
    }

    /**
     * Guides the user through modifying the details of an existing activity.
     *
     * @throws TravelTrioException If no trip is open.
     */
    private void handleEditActivity() throws TravelTrioException {
        ensureTripOpen();
        printActivityList();
        int activityIdx = ui.promptInt("Enter the index of the activity to edit");
        ui.showMessage("Leave any field blank to keep current values.");
        String newTitle = ui.promptOptionalField("New Title");
        String newLocation = ui.promptOptionalField("New Location");
        String newDate = ui.promptOptionalField("New Date (YYYY-MM-DD)");
        String newStartTime = ui.promptOptionalField("New Start Time (HH:MM)");
        String newEndTime = ui.promptOptionalField("New End Time (HH:MM)");
        ui.showMessageWithDivider(new EditActivityCommand(openTrip.getActivities(),
                activityIdx, newTitle, newLocation, newDate, newStartTime, newEndTime)
                .run(openTrip.getName()));
    }

    /**
     * Handles displaying all activities for the currently open trip.
     *
     * @throws TravelTrioException If no trip is open.
     */
    private void handleListActivity() throws TravelTrioException {
        ensureTripOpen();
        printActivityList();
    }

    /**
     * Helper method to format and print the activity list.
     *
     * @throws TravelTrioException If an error occurs during formatting.
     */
    private void printActivityList() throws TravelTrioException {
        String tripStartDate = openTrip.getStartDate();
        String tripEndDate = openTrip.getEndDate();
        ui.showMessage(new ListActivityCommand(openTrip.getActivities(), tripStartDate, tripEndDate)
                .run(openTrip.getName()));
    }

    /**
     * Guides user to input details to add an activity to a certain trip.
     * Checks that user input is valid.
     * Validates that date is within the trip date.
     *
     * @throws TravelTrioException if date is not within the trip dates
     */
    private void handleAddActivity() throws TravelTrioException {
        ensureTripOpen();

        String tripStartDate = openTrip.getStartDate();
        String tripEndDate = openTrip.getEndDate();

        String title = ui.promptField("Activity Title");
        String location = ui.promptField("Location");
        String date;

        while (true) {
            date = ui.promptDate("Date");
            if (date.compareTo(tripStartDate) < 0 || date.compareTo(tripEndDate) > 0) {
                ui.showError("Activity date is outside your trip dates ("
                        + tripStartDate + " to " + tripEndDate + "). Please enter a valid date.");
            } else {
                break;
            }
        }

        String startTime;
        String endTime;

        startTime = ui.promptTime("Start Time");
        endTime = ui.promptTime("End Time");

        LocalTime st = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime et = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));

        if (!et.isAfter(st)) {
            String confirm = ui.promptField("End time is before start time. Does this activity cross midnight? (y/n)");
            if (!confirm.equalsIgnoreCase("y")) {
                throw new TravelTrioException("End time must be after start time.");
            }
        }

        ui.showMessageWithDivider(new AddActivityCommand(openTrip.getActivities(),
                title, location, date, startTime, endTime)
                .run(openTrip.getName()));
    }

    /**
     * Handles displaying the next upcoming activity based on the current system time.
     *
     * @throws TravelTrioException If no trip is open.
     */
    private void handleNextActivity() throws TravelTrioException {
        ensureTripOpen();
        ui.showMessageWithDivider(new NextActivityCommand(openTrip.getActivities()).execute(openTrip.getName()));
    }

    /**
     * Guides the user through deleting a trip from the application.
     *
     * @throws TravelTrioException If an error occurs during deletion.
     */
    private void handleDeleteTrip() throws TravelTrioException {
        printTripList();
        int tripIdx = ui.promptInt("Enter the index of the trip to delete");
        ui.showMessage(new DeleteTripCommand(tripList, tripIdx).execute());
        // If the open trip is the one deleted, reset opentrip
        if (openTrip != null && !tripList.contains(openTrip)) {
            openTrip = null;
            ui.showMessage("The active trip was deleted. Use 'opentrip' to open a trip.");
        }
    }

    /**
     * Sets a specific trip as the active 'open' trip for activity and budget management.
     *
     * @throws TravelTrioException if no trips exist in the list.
     */
    private void handleOpenTrip() throws TravelTrioException {
        if (tripList.isEmpty()) {
            throw new TravelTrioException("No trips found. Use 'addtrip' to add one!");
        }

        printTripList();
        int idx = ui.promptInt("Enter the index of the trip to open");
        assert idx > 0 : "UI should have validated that idx is positive";
        openTrip = tripList.get(idx - 1);
        assert openTrip != null;
        logger.log(Level.FINE, "Trip opened: {0}", openTrip.getName());
        ui.showMessage(new OpenTripCommand(tripList, idx).execute());
    }

    /**
     * Helper method to format and print all available trips.
     *
     * @throws TravelTrioException If an error occurs during listing.
     */
    private void printTripList() throws TravelTrioException {
        ui.showMessageWithDivider(new ListTripCommand(tripList).execute());
    }

    /**
     * Guides the user through creating and adding a new trip.
     *
     * @throws TravelTrioException If an error occurs during trip creation.
     */
    private void handleAddTrip() throws TravelTrioException {
        logger.log(Level.FINE, "Entering handleAddTrip()");
        String name = ui.promptField("Trip Name");
        String start;
        String end;

        while (true) {
            start = ui.promptDate("Start Date");
            end = ui.promptDate("End Date");

            if (start.compareTo(end) > 0) {
                ui.showError("Start date cannot be later than the end date. Let's try those dates again.");
            } else {
                break; // Dates are valid and logical!
            }
        }

        assert !name.isEmpty() && !start.isEmpty() && !end.isEmpty() : "UI returned empty fields";

        logger.log(Level.FINE, "Successfully added trip: {0}", name);

        ui.showMessageWithDivider(new AddTripCommand(tripList, name, start, end).execute());
    }

    /**
     * Guides the user through importing a shared trip from a text file.
     *
     * @throws TravelTrioException If the file cannot be read or parsed.
     */
    private void handleImportTrip() throws TravelTrioException {
        String fileName = ui.promptField("Enter the file name to import (e.g., SharedTrip.txt)");
        ui.showMessageWithDivider(new ImportTripCommand(tripList, fileName, storage).execute());
    }

    /**
     * Guides the user through exporting a specific trip to a standalone text file.
     *
     * @throws TravelTrioException If no trips are available to export or file writing fails.
     */
    private void handleExportTrip() throws TravelTrioException {
        if (tripList.isEmpty()) {
            throw new TravelTrioException("No trips available to export.");
        }
        printTripList();
        int tripIdx = ui.promptInt("Enter the index of the trip to export");
        String fileName = ui.promptField("Enter the file name to save as (e.g., JapanTrip.txt)");

        ui.showMessageWithDivider(new ExportTripCommand(tripList, tripIdx, fileName, storage).execute());
    }

    /**
     * Prompts the user to enter a daily spending limit for the currently opened trip,
     * applies the new limit, and displays the result message.
     *
     * @throws TravelTrioException If no trip is currently open or if the entered
     *                             daily limit is invalid.
     */
    private void handleSetDailyLimit() throws TravelTrioException {
        ensureTripOpen();
        double amount = ui.promptDouble("Enter daily spending limit to set ($)");
        String message = new SetDailyLimitCommand(openTrip.getBudgets(), amount, 
                openTrip.getStartDate(), openTrip.getEndDate()).execute();
        ui.showMessage(message);
    }

    /**
     * Displays the centralized help guide.
     */
    private void handleHelp() {
        ui.showMessage(new HelpCommand().execute());
    }

    /**
     * Validates that a trip is currently open before allowing trip-specific commands to execute.
     *
     * @throws TravelTrioException If <code>openTrip</code> is null.
     */
    private void ensureTripOpen() throws TravelTrioException {
        if (this.openTrip == null) {
            throw new TravelTrioException("You need to open a trip first. (Use 'opentrip')");
        }
        assert openTrip != null : "openTrip should not be null after check";
    }

    /**
     * Guides the user through appending a custom remark to a specific activity.
     *
     * @throws TravelTrioException If no trip is open or the activity list is empty.
     */
    private void handleAddRemark() throws TravelTrioException{
        ensureTripOpen();
        if (openTrip.getActivities().isEmpty()) {
            throw new TravelTrioException("No activities found. Please add an activity before adding a remark.");
        }

        printActivityList();

        int index = ui.promptInt("Enter the index of the activity to add a remark for");
        String remark = ui.promptField("Enter the remark to add");

        String result = new AddRemarkCommand(
            openTrip.getActivities(),
            index,
            remark
        ).run(openTrip.getName());

        ui.showMessageWithDivider(result);
    }

    /**
     * Guides the user through adding a new item to the packing list.
     *
     * @throws TravelTrioException If no trip is open.
     */
    private void handleAddItem() throws TravelTrioException {
        ensureTripOpen();
        String name = ui.promptField("Item name");
        ui.showMessage(new AddItemCommand(openTrip.getPackingList(), name).execute());
    }

    /**
     * Handles displaying all items currently in the packing list.
     *
     * @throws TravelTrioException If no trip is open.
     */
    private void handleListItems() throws TravelTrioException {
        ensureTripOpen();
        ui.showMessage(new ListItemsCommand(openTrip.getPackingList()).execute());
    }

    /**
     * Guides the user through marking a specific packing item as packed.
     *
     * @throws TravelTrioException If no trip is open.
     */
    private void handleCheckItem() throws TravelTrioException {
        ensureTripOpen();
        int idx = ui.promptInt("Enter item index to mark as packed");
        ui.showMessage(new CheckItemCommand(openTrip.getPackingList(), idx).execute());
    }

    /**
     * Guides the user through deleting a specific item from the packing list.
     *
     * @throws TravelTrioException If no trip is open.
     */
    private void handleDeleteItem() throws TravelTrioException {
        ensureTripOpen();
        int idx = ui.promptInt("Enter item index to delete");
        ui.showMessage(new DeleteItemCommand(openTrip.getPackingList(), idx).execute());
    }
}

