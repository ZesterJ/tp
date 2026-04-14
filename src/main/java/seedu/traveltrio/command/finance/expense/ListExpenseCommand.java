package seedu.traveltrio.command.finance.expense;

import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.budget.Budget;
import seedu.traveltrio.model.trip.Trip;

/**
 * Represents a command to display a chronologically sorted list of all expenses incurred during the trip.
 * A ListExpenseCommand object compiles all activities, sorts them by date,
 * and outputs a formatted table comparing the expenses against the trip's daily limit and overall total.
 * Activities without an assigned budget are shown with a dash in the expense column.
 */
public class ListExpenseCommand extends ExpenseCommand {

    protected final String tripName;

    /**
     * Initializes a ListExpenseCommand with the currently opened trip.
     *
     * @param openTrip The active trip whose expenses are to be listed.
     */
    public ListExpenseCommand(Trip openTrip) {
        super(openTrip.getBudgets(), openTrip.getActivities());
        this.tripName = openTrip.getName();
    }

    /**
     * Executes the command to generate the expense comparison table.
     * Iterates the activity list in its existing chronological order, displaying actual expenses
     * for budgeted activities and a dash for unbudgeted ones.
     *
     * @return Formatted string containing the chronological table of expenses.
     */
    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder();
        sb.append("Expense Comparison for ").append(tripName).append(":\n");
        if (budgetList.hasDailyLimitSet()) {
            sb.append("(Daily limit: ").append(
                    String.format("$%.2f)\n\n", budgetList.getDailySpendingLimit())
            );
        } else {
            sb.append("(Daily Limit: Not set)\n\n");
        }

        String rowFormat = "%-10s | %-18s | %10s\n";
        String divider = "----------------------------------------------------------------------\n";

        sb.append(String.format(rowFormat, "Date", "Activity", "Actual expense"));
        sb.append(divider);

        double totalActual = 0;
        String previousDate = null;

        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i);
            Budget budget = budgetList.getBudget(activity);

            String currentDate = activity.getDate() != null ? activity.getDate() : "---";
            String displayDate = currentDate.equals(previousDate) ? "" : currentDate;

            String expenseDisplay;
            if (budget != null) {
                double actual = budget.getActualExpense();
                totalActual += actual;
                expenseDisplay = String.format("$%.2f", actual);
            } else {
                expenseDisplay = "-";
            }

            sb.append(String.format(rowFormat, displayDate, activity.getName(), expenseDisplay));
            previousDate = currentDate;
        }

        sb.append("\n");
        sb.append(divider);
        sb.append("Total expense: ").append(String.format("$%.2f", totalActual));

        return sb.toString();
    }

}
