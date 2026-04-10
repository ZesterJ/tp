package seedu.traveltrio.command.finance.budget;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.budget.Budget;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.activity.Activity;

public class BudgetChartCommand extends BudgetCommand {

    private static final int BAR_LENGTH = 20;

    public BudgetChartCommand(BudgetList budgetList, ActivityList activityList) {
        super(budgetList, activityList, null);
    }

    @Override
    public String execute() throws TravelTrioException {
        if (budgetList.isEmpty()) {
            throw new TravelTrioException("No budgets found.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Budget Usage Chart:\n");

        for (var entry : budgetList.getBudgets().entrySet()) {
            Activity activity = entry.getKey();
            Budget budget = entry.getValue();

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
