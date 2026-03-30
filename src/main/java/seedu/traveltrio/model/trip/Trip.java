package seedu.traveltrio.model.trip;

import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.Budget;
import seedu.traveltrio.model.budget.BudgetList;

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
    private boolean isOpen;

    public Trip(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activities = new ActivityList(this);
        this.budgets = new BudgetList();
        this.isOpen = false;
    }

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
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ActivityList getActivities() {
        return activities;
    }

    public BudgetList getBudgets() {
        return budgets;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        return name + " (" + startDate + " to " + endDate + ")";
    }

    public String formatForList() {
        boolean hasSetBudget = !budgets.getBudgets().isEmpty();
        String totalSpentText = "           (Total Spent: " + String.format("$%.2f", budgets.getTotalTripExpense())+")";

        String result = name + (hasSetBudget ? totalSpentText : "")+ "\n";
        result += "   Start: " + startDate + "\n";
        result += "   End:   " + endDate;
        return result;
    }
}

