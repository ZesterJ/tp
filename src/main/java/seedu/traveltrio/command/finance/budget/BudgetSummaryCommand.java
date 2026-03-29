package seedu.traveltrio.command.finance.budget;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.BudgetList;

public class BudgetSummaryCommand extends BudgetCommand {

    public BudgetSummaryCommand(BudgetList budgetList, ActivityList activityList) {
        super(budgetList, activityList, null);
    }

    @Override
    public String execute() throws TravelTrioException {
        double totalBudget = budgetList.getTotalTripBudget();
        StringBuilder result = new StringBuilder();
        result.append("Total trip budget: $").append(String.format(
                "%.2f", totalBudget)).append("\n");
        result.append("Total expense: $").append(String.format(
                "%.2f", budgetList.getTotalTripExpense())).append("\n");
        result.append("Total remaining budget: $").append(String.format(
                "%.2f", budgetList.getTotalRemainingTripBudget())).append("\n\n");
        
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
