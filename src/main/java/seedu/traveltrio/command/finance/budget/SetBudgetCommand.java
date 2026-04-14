package seedu.traveltrio.command.finance.budget;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.budget.Budget;

/**
 * Represents a command to set or modify the budget for a specific activity. A <code>SetBudgetCommand</code>
 * object assigns a monetary value to an activity, or removes the budget entirely if the value is set to zero.
 */
public class SetBudgetCommand extends BudgetCommand {

    private final double totalBudget;

    /**
     * Initializes a SetBudgetCommand with the specified budget list, activity list, target activity,
     * and budget amount.
     *
     * @param budgetList The list containing all budgets for the current trip.
     * @param activityList The list containing all scheduled activities for the current trip.
     * @param activity The specific activity to assign the budget to.
     * @param totalBudget The monetary amount to set as the budget.
     * @param isForeign True if the amount is in foreign currency, false if in home currency.
     */
    public SetBudgetCommand(BudgetList budgetList, ActivityList activityList,
                Activity activity, double totalBudget, boolean isForeign) {
        super(budgetList, activityList , activity);
        double adjustedBudget = totalBudget;
        if (isForeign) {
            adjustedBudget *= budgetList.getExchangeRate();
        }
        if (Math.abs(adjustedBudget) < 0.0001) {
            this.totalBudget = 0;
        } else {
            this.totalBudget = adjustedBudget;
        }
    }

    /**
     * Executes the command to set the budget for the specified activity. If the budget amount is zero,
     * any existing budget for the activity is removed from the trip.
     *
     * @return Formatted string confirming the budget addition or removal.
     * @throws TravelTrioException If no activity is selected or if the provided budget amount is negative.
     */
    @Override
    public String execute() throws TravelTrioException {
        
        if (activity == null) {
            throw new TravelTrioException("Please select an activity to add a budget for.");
        }
        if (totalBudget < 0) {
            throw new TravelTrioException("Total budget cannot be negative.");
        }
        if (totalBudget == 0) {
            budgetList.removeBudget(activity);
            return "Removed budget for " + activity.getName() + " as the total budget is set to $0.00.";
        }
        Budget newBudget = new Budget(totalBudget, activity);
        budgetList.addBudget(activity, newBudget);
        return "Added budget for " + activity.getName() + ": $" + String.format("%.4f", totalBudget);
    }
    
}
