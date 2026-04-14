package seedu.traveltrio.command.finance.budget;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.BudgetList;

/**
 * Represents a command to generate a comprehensive summary of the trip's financial status.
 * Calculates and displays the total trip budget, total expenses, total remaining budget,
 * and provides a detailed, itemized breakdown of budget allocations per activity.
 */
public class BudgetSummaryCommand extends BudgetCommand {

    /**
     * Initializes a BudgetSummaryCommand with the specified budget and activity lists.
     * Passes a null activity to the superclass since this command summarizes the entire trip.
     *
     * @param budgetList The list containing all budgets for the current trip.
     * @param activityList The list containing all scheduled activities for the current trip.
     */
    public BudgetSummaryCommand(BudgetList budgetList, ActivityList activityList) {
        super(budgetList, activityList, null);
    }

    /**
     * Executes the command to compile the budget summary.
     * Builds a formatted string containing the overall financial totals, followed by a
     * line-by-line breakdown of each activity that has an assigned budget.
     *
     * @return A formatted string displaying the overall financial summary and itemized breakdown.
     * @throws TravelTrioException If no budgets have been added to the trip yet.
     */
    @Override
    public String execute() throws TravelTrioException {
        double totalBudget = budgetList.getTotalTripBudget();
        StringBuilder result = new StringBuilder();
        result.append("Total trip budget: $").append(String.format(
                "%.2f", totalBudget)).append("\n");
        result.append("Total expense: $").append(String.format(
                "%.2f", budgetList.getTotalTripExpense())).append("\n");
        result.append("Total remaining budget: $").append(String.format(
                "%.2f", budgetList.getTotalRemainingTripBudget())).append("\n");
        result.append("Exchange rate: 1 Foreign Currency = ").append(String.format(
                "%.2f Home Currency", budgetList.getExchangeRate())).append("\n\n");
        
        if (budgetList.getBudgets().isEmpty()) {
            throw new TravelTrioException("No budgets added yet.");
        }

        result.append("Budget Breakdown:\n");
        for (int i = 0; i < activityList.size(); i++) {
            int activityIndex = i + 1;
            var activity = activityList.get(i);
            var budget = budgetList.getBudget(activity);
            if (budget != null) {
                result.append(activityIndex).append(". ").append(budget.toString()).append("\n");
            }
        }

        return result.toString();
    }
}
