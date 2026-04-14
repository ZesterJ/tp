package seedu.traveltrio.model.trip;

import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.Budget;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.model.packing.PackingList;

/**
 * Represents a trip with a name, start date, end date, and associated activities,
 * budgets, and packing list. A trip can be opened for editing, with only one trip
 * open at a time.
 */
public class Trip {

    public static final String TRIP_DIVIDER_LINE =
            "***************************************************************************";
    public static final String ACTIVITY_DIVIDER_LINE =
            "---------------------------------------------------------------------------";
    private String name;
    private String startDate;
    private String endDate;
    private final ActivityList activities;
    private final BudgetList budgets;
    private final PackingList packingList;
    private boolean isOpen;

    /**
     * Constructs a new Trip with the specified details.
     *
     * @param name the name of the trip
     * @param startDate the start date of the trip
     * @param endDate the end date of the trip
     */
    public Trip(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activities = new ActivityList(this);
        this.budgets = new BudgetList();
        this.packingList = new PackingList();
        this.isOpen = false;
    }

    /**
     * Returns a formatted string representation of the trip for file export.
     * Includes trip details, all activities with their budgets, and the packing list.
     *
     * @return the formatted trip details as a string
     */
    public String toFileFormat() {
        StringBuilder sb = new StringBuilder();
        double totalBudget = budgets.getTotalTripBudget();
        double remainingBudget = budgets.getTotalRemainingTripBudget();
        double exchangeRate = budgets.getExchangeRate();

        // Add trip details
        sb.append(TRIP_DIVIDER_LINE + "\n");
        sb.append(String.format("Trip: %s | From: %s | To: %s | \nTotal Budget: %.2f | Remaining Budget: %.2f "
                + "| Exchange Rate: %.2f \n", name, startDate, endDate, totalBudget, remainingBudget, exchangeRate));
        sb.append(TRIP_DIVIDER_LINE + "\n");

        String lastDate = "";

        for (int i = 0; i < activities.size(); i++) {
            Activity act = activities.get(i);
            String currentDate = act.getDate();

            // Day header if the date has changed
            if (!currentDate.equals(lastDate)) {
                sb.append("\n=== Date: ").append(currentDate).append(" ===\n");
                sb.append(ACTIVITY_DIVIDER_LINE + "\n");
                lastDate = currentDate;
            }

            // Add activity details
            sb.append("Title: ").append(act.getName()).append("\n");
            sb.append("    Location: ").append(act.getLocation()).append("\n");
            sb.append("    Start Time: ").append(act.getStart()).append("\n");
            sb.append("    End Time:   ").append(act.getEnd()).append("\n");

            // Add budget details
            Budget b = budgets.getBudget(act);
            if (b != null) {
                sb.append(String.format("      Budget set: %.2f\n", b.getActivityBudget()));
                sb.append(String.format("      Actual Expense: %.2f\n", b.getActualExpense()));
            }
            sb.append(ACTIVITY_DIVIDER_LINE + "\n");
        }

        sb.append("\nPacking List:\n");
        sb.append(ACTIVITY_DIVIDER_LINE + "\n");
        sb.append(packingList.toFileFormat());
        sb.append(ACTIVITY_DIVIDER_LINE + "\n");


        return sb.toString();
    }

    /**
     * Gets the name of the trip.
     *
     * @return the trip name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the trip.
     *
     * @param name the new trip name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the start date of the trip.
     *
     * @return the start date
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the trip.
     *
     * @param startDate the new start date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the trip.
     *
     * @return the end date
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the trip.
     *
     * @param endDate the new end date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the list of activities for this trip.
     *
     * @return the activity list
     */
    public ActivityList getActivities() {
        return activities;
    }

    /**
     * Gets the list of budgets for this trip.
     *
     * @return the budget list
     */
    public BudgetList getBudgets() {
        return budgets;
    }

    /**
     * Gets the packing list for this trip.
     *
     * @return the packing list
     */
    public PackingList getPackingList() {
        return packingList;
    }

    /**
     * Checks if this trip is currently open for editing.
     *
     * @return true if the trip is open, false otherwise
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Sets the open status of this trip.
     *
     * @param open true to open the trip, false to close it
     */
    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        return name + " (" + startDate + " to " + endDate + ")";
    }

    /**
     * Returns a formatted string representation of the trip for display in a list.
     * Includes the trip name, start date, end date, and total spent if a budget is set.
     *
     * @return the formatted trip details for list display
     */
    public String formatForList() {
        boolean hasSetBudget = !budgets.getBudgets().isEmpty();
        String totalSpentText = "           (Total Spent: " + String.format("$%.2f", budgets.getTotalTripExpense())+")";

        String result = name + (hasSetBudget ? totalSpentText : "")+ "\n";
        result += "   Start: " + startDate + "\n";
        result += "   End:   " + endDate;
        return result;
    }
}

