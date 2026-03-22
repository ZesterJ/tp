package seedu.traveltrio.command.budget;

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
        result.append("Total trip budget: $").append(String.format("%.2f", totalBudget)).append("\n\n");
        
        if (budgetList.getBudgets().isEmpty()) {
            throw new TravelTrioException("No budgets added yet.");
        } else {
            result.append("Budget Breakdown:\n");
            var budgets = budgetList.getBudgets();
            int index = 1;
            for (var entry : budgets.entrySet()) {
                var activity = entry.getKey();
                var budget = entry.getValue();
                result.append(index).append(". ").append(activity.getName()).append("\n");
                result.append("   Total: $").append(String.format("%.2f", budget.getTotalBudget()));
                result.append(" | Spent: $").append(String.format("%.2f", budget.getAmountSpent()));
                result.append(" | Remaining: $").append(String.format("%.2f",
                        budget.getRemainingBudget())).append("\n");
                index++;
            }
        }
        
        return result.toString();
    }
}
