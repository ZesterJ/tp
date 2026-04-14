package seedu.traveltrio.command.finance.expense;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.budget.BudgetList;

/**
 * Represents a command to log the actual expense incurred for a specific activity. A SetExpenseCommand
 * object updates the financial records, automatically converts foreign currencies using the trip's exchange rate,
 * and provides a warning if the spending approaches the budget limit.
 */
public class SetExpenseCommand extends ExpenseCommand{

    private final Activity activity;
    private final double amount;

    /**
     * Initializes a SetExpenseCommand with the target activity and the expense amount.
     * Automatically converts the provided amount to the home currency if the expense was made in a foreign currency.
     *
     * @param budgetList The list containing the financial data and exchange rates for the current trip.
     * @param activityList The list containing the scheduled activities for the current trip.
     * @param activity The specific activity the expense is tied to.
     * @param amount The monetary amount spent.
     * @param isForeignCurrency True if the amount is in a foreign currency, false if it is in the home currency.
     */
    public SetExpenseCommand(BudgetList budgetList, ActivityList activityList,
                             Activity activity, double amount, boolean isForeignCurrency) {
        super(budgetList, activityList);
        this.activity = activity;

        if (isForeignCurrency) {
            this.amount = amount * budgetList.getExchangeRate();
        } else {
            this.amount = amount;
        }

    }

    /**
     * Executes the command to record the expense for the designated activity.
     * Calculates the percentage of the budget used and appends a warning message to the output
     * if the logged expense reaches or exceeds 90% of the allocated budget.
     *
     * @return Formatted string confirming the logged expense, along with a warning if nearing the budget limit.
     * @throws TravelTrioException If the expense violates daily limits, is negative, or if the activity has no budget.
     */
    @Override
    public String execute() throws TravelTrioException {
        budgetList.setExpense(activity, amount);
        double activityBudget = budgetList.getBudget(activity).getActivityBudget();
        double percentageSpent = (activityBudget == 0) ? 0 : (amount / activityBudget) * 100;
        String result = "Expense set for activity: " + activity.getName() +
                ". \nActual spending: $" + String.format("%.2f", amount);
        if (percentageSpent >= 90) {
            result += "\n(Warning: You have spent " + String.format("%.1f", percentageSpent) + "% of the budget!)";
        }
        return result;
    }
}
