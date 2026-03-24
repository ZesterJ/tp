package seedu.traveltrio;

import seedu.traveltrio.command.activity.AddActivityCommand;
import seedu.traveltrio.command.activity.DeleteActivityCommand;
import seedu.traveltrio.command.activity.EditActivityCommand;
import seedu.traveltrio.command.activity.ListActivityCommand;
import seedu.traveltrio.command.finance.budget.AddBudgetCommand;
import seedu.traveltrio.command.finance.budget.BudgetSummaryCommand;
import seedu.traveltrio.command.finance.expense.ListExpenseCommand;
import seedu.traveltrio.command.finance.expense.SetExpenseCommand;
import seedu.traveltrio.command.trip.AddTripCommand;
import seedu.traveltrio.command.trip.DeleteTripCommand;
import seedu.traveltrio.command.trip.ListTripCommand;
import seedu.traveltrio.command.trip.OpenTripCommand;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.command.others.HelpCommand;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

import java.util.logging.Logger;
import java.util.logging.Level;

public class CommandProcessor {
    private static final Logger logger = Logger.getLogger(CommandProcessor.class.getName());

    private final TripList tripList;
    private final Ui ui;
    private Trip openTrip = null;

    public CommandProcessor(TripList tripList, Ui ui) {
        this.tripList = tripList;
        this.ui = ui;
    }

    public void process(String command) {
        logger.log(Level.INFO, "Processing command: {0}", command);
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
            case "addbudget":
                handleAddBudget();
                break;
            case "budgetsummary":
                handleBudgetSummary();
                break;
            case "setexpense":
                handleSetExpense();
                break;
            case "listexpense":
                handleListExpense();
                break;
            case "help":
                handleHelp();
                break;
            default:
                handleUnknownCommand();
            }
        } catch (TravelTrioException e) {
            ui.showError(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            ui.showError("That number is not in the list. Please check the list and try again.");
        } catch (Exception e) {
            ui.showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleUnknownCommand() {
        ui.showError("Unknown command.\n"
                + "Available commands: addtrip, listtrip, opentrip, deletetrip,\n"
                + "addactivity, listactivity, editactivity, deleteactivity,\n"
                + "addbudget, setexpense, budgetsummary, help, exit");
    }

    private void handleBudgetSummary() throws TravelTrioException {
        ensureTripOpen();
        ui.showMessageWithDivider(new BudgetSummaryCommand(openTrip.getBudgets(),
                openTrip.getActivities()).execute());
    }

    private void handleAddBudget() throws TravelTrioException {
        ensureTripOpen();
        if (openTrip.getActivities().isEmpty()) {
            throw new TravelTrioException("No activities found. Please add an activity before setting a budget.");
        }
        printActivityList();
        int budgetActivityIdx = ui.promptInt("Enter the number of the activity to add a budget for. ");
        double budgetAmount = ui.promptDouble("Enter budget amount ($)");
        ui.showMessage(new AddBudgetCommand(openTrip.getBudgets(),
                openTrip.getActivities(), openTrip.getActivities().get(budgetActivityIdx - 1), budgetAmount)
                .execute());
    }

    private void handleSetExpense() throws TravelTrioException {
        ensureTripOpen();
        if (openTrip.getActivities().isEmpty()) {
            throw new TravelTrioException("Activity list is empty! No activity to set expense for now...");
        }
        printActivityList();
        ActivityList activities = openTrip.getActivities();
        int activityIdx;
        while (true) {
            String input = ui.promptField("Enter the activity number to set actual spending "
                    + "(or type 'exit' to cancel): ");

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
                ui.showError("Please enter a valid activity number, or type 'exit' to cancel.");
            }
        }

        double actualAmount = ui.promptDouble("Enter amount ($)");
        String successMessage = new SetExpenseCommand(
                openTrip.getBudgets(),
                openTrip.getActivities(),
                openTrip.getActivities().get(activityIdx - 1),
                actualAmount
        ).execute();

        ui.showMessage(successMessage);
    }

    private void handleListExpense() throws TravelTrioException {
        ensureTripOpen();
        if (openTrip.getActivities().isEmpty()){
            ui.showMessage("You have not create any activities for your trip. No expenses to list.");
            return;
        }
        if (openTrip.getBudgets().isEmpty()){
            ui.showMessage("You have not create any budget for your activities yet.");
            return;
        }
        String resultString = new ListExpenseCommand(openTrip).execute();

        ui.showMessageWithDivider(resultString);
    }

    private void handleDeleteActivity() throws TravelTrioException {
        ensureTripOpen();
        printActivityList();
        int actIdx = ui.promptInt("Enter the number of the activity to delete");
        ui.showMessage(new DeleteActivityCommand(openTrip.getActivities(), actIdx)
                .execute(openTrip.getName()));
    }

    private void handleEditActivity() throws TravelTrioException {
        ensureTripOpen();
        printActivityList();
        int activityIdx = ui.promptInt("Enter the number of the activity to edit");
        ui.showMessage("Leave any field blank to keep current values.");
        String newTitle = ui.promptField("New Title");
        String newLocation = ui.promptField("New Location");
        String newDate = ui.promptField("New Date (YYYY-MM-DD)");
        String newStartTime = ui.promptField("New Start Time (HH:MM)");
        String newEndTime = ui.promptField("New End Time (HH:MM)");
        ui.showMessageWithDivider(new EditActivityCommand(openTrip.getActivities(),
                activityIdx, newTitle, newLocation, newDate, newStartTime, newEndTime)
                .execute(openTrip.getName()));
    }

    private void handleListActivity() throws TravelTrioException {
        ensureTripOpen();
        printActivityList();
    }

    private void printActivityList() throws TravelTrioException {
        ui.showMessageWithDivider(new ListActivityCommand(openTrip.getActivities()).execute(openTrip.getName()));
    }

    private void handleAddActivity() throws TravelTrioException {
        ensureTripOpen();

        String tripStartDate = openTrip.getStartDate();
        String tripEndDate = openTrip.getEndDate();

        String title = ui.promptField("Activity Title");
        String location = ui.promptField("Location");
        String date = ui.promptField("Date (YYYY-MM-DD)");

        if (date.compareTo(tripStartDate) < 0 || date.compareTo(tripEndDate) > 0) {
            throw new TravelTrioException("Activity date (" + date + ") " + "is outside of your trip dates. " +
                    "Your trip is from " + tripStartDate + " to " + tripEndDate + ".");
        }

        String startTime = ui.promptField("Start Time (HH:MM)");
        String endTime = ui.promptField("End Time (HH:MM)");

        ui.showMessageWithDivider(new AddActivityCommand(openTrip.getActivities(),
                title, location, date, startTime, endTime)
                .execute(openTrip.getName()));
    }

    private void handleDeleteTrip() throws TravelTrioException {
        printTripList();
        int tripIdx = ui.promptInt("Enter the number of the trip to delete");
        ui.showMessage(new DeleteTripCommand(tripList, tripIdx).execute());
        // If the open trip is the one deleted, reset opentrip
        if (openTrip != null && !tripList.contains(openTrip)) {
            openTrip = null;
            ui.showMessage("The active trip was deleted. Use 'opentrip' to open a trip.");
        }
    }

    private void handleOpenTrip() throws TravelTrioException {
        if (tripList.isEmpty()) {
            throw new TravelTrioException("No trips found. Use 'addtrip' to add one!");
        }

        printTripList();
        int idx = ui.promptInt("Enter the number of the trip to open");
        assert idx > 0 : "UI should have validated that idx is positive";
        openTrip = tripList.get(idx - 1);
        assert openTrip != null;
        logger.log(Level.INFO, "Trip opened: {0}", openTrip.getName());
        ui.showMessage(new OpenTripCommand(tripList, idx).execute());
    }

    private void printTripList() throws TravelTrioException {
        ui.showMessageWithDivider(new ListTripCommand(tripList).execute());
    }

    private void handleAddTrip() throws TravelTrioException {
        logger.log(Level.INFO, "Entering handleAddTrip()");
        String name = ui.promptField("Trip Name");
        String start = ui.promptField("Start Date (YYYY-MM-DD)");
        String end = ui.promptField("End Date (YYYY-MM-DD)");

        assert !name.isEmpty() && !start.isEmpty() && !end.isEmpty() : "UI returned empty fields";

        logger.log(Level.INFO, "Successfully added trip: {0}", name);

        ui.showMessageWithDivider(new AddTripCommand(tripList, name, start, end).execute());
    }

    private void handleHelp() {
        ui.showMessage(new HelpCommand().execute());
    }

    private void ensureTripOpen() throws TravelTrioException {
        if (this.openTrip == null) {
            throw new TravelTrioException("You need to open a trip first. (Use 'opentrip')");
        }
        assert openTrip != null : "openTrip should not be null after check";
    }
}
