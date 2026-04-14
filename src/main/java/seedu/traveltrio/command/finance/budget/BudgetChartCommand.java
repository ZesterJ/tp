package seedu.traveltrio.command.finance.budget;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.budget.Budget;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.activity.Activity;

/**
 * Represents a command to generate a visual, text-based chart of budget usage.
 * Displays a progress bar for each activity showing the percentage of the allocated budget that has been spent.
 */
public class BudgetChartCommand extends BudgetCommand {

    private static final int BAR_LENGTH = 20;

    /**
     * Constructs a BudgetChartCommand with the specified budget and activity lists.
     * Passes a null activity to the superclass since this command applies to the entire trip,
     * not a single specific activity.
     *
     * @param budgetList   The list containing all budgets for the current trip.
     * @param activityList The list containing all scheduled activities for the current trip.
     */
    public BudgetChartCommand(BudgetList budgetList, ActivityList activityList) {
        super(budgetList, activityList, null);
    }

    /**
     * Executes the command to build and return the ASCII budget usage chart.
     * Iterates through all activities, calculates the percentage of the budget spent,
     * and formats a 10-segment visual bar representing the usage.
     *
     * @return A formatted string containing the overall budget usage chart.
     * @throws TravelTrioException If no budgets have been set in the current trip.
     */
    @Override
    public String execute() throws TravelTrioException {
        if (budgetList.isEmpty()) {
            throw new TravelTrioException("No budgets found.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Budget Usage Chart:\n");

        for (Activity activity : activityList.getAll()) {
            Budget budget = budgetList.getBudget(activity);

            if (budget == null) {
                continue; // skip activities without budget
            }

            double total = budget.getActivityBudget();
            double spent = budget.getActualExpense();

            int percentage = (total == 0) ? 0 : (int) ((spent / total) * 100);

            int filled = percentage / 10;

            String bar = "["
                    + "#".repeat(filled)
                    + "-".repeat(10 - filled)
                    + "]";

            sb.append(String.format("%-10s %s %d%%\n",
                    activity.getName(),
                    bar,
                    percentage));
        }

        return sb.toString();
    }
}
