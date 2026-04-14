package seedu.traveltrio.model.budget;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;

/**
 * Represents a financial budget assigned to a specific activity. A Budget object
 * tracks the allocated amount, the actual expense incurred, and calculates the remaining balance.
 */
public class Budget {

    private final double activityBudget;
    private double actualExpense;
    private final Activity activity;

    /**
     * Initializes a Budget with the specified allocated amount and target activity.
     * The actual expense is initialized to zero.
     *
     * @param activityBudget The monetary amount allocated for the activity.
     * @param activity The specific activity this budget applies to.
     * @throws TravelTrioException If the provided activity budget is negative.
     */
    public Budget(double activityBudget, Activity activity) throws TravelTrioException {
        if (activityBudget < 0) {
            throw new TravelTrioException("Activity budget cannot be negative.");
        }
        this.activityBudget = activityBudget;
        this.actualExpense = 0;
        this.activity = activity;
    }

    /**
     * Returns the total allocated budget for this activity.
     *
     * @return The activity budget.
     */
    public double getActivityBudget() {
        return activityBudget;
    }

    /**
     * Returns the total actual expense incurred for this activity so far.
     *
     * @return The actual expense.
     */
    public double getActualExpense() {
        return actualExpense;
    }

    /**
     * Calculates and returns the remaining budget for this activity.
     *
     * @return The difference between the allocated budget and the actual expense.
     */
    public double getRemainingBudget() {
        return activityBudget - actualExpense;
    }

    /**
     * Sets the actual expense incurred for this activity.
     *
     * @param amount The monetary amount spent.
     * @throws TravelTrioException If the amount is negative, or if the expense exceeds the allocated activity budget.
     */
    public void setActualExpense(double amount) throws TravelTrioException {
        if (amount < 0) {
            throw new TravelTrioException("Expense amount cannot be negative.");
        }
        if (amount > getActivityBudget()) {
            throw new TravelTrioException("Expense exceeds activity budget by " 
                    + String.format("%.2f", amount - getActivityBudget()) 
                    + ". You must adjust the activity budget or reduce the expense amount.");
        }
        actualExpense = amount;
    }

    /**
     * Returns a formatted string representation of the budget details.
     * Includes a warning message if the actual expense reaches or exceeds 90% of the allocated budget.
     *
     * @return A formatted string containing the activity name, financial breakdown, and potential warnings.
     */
    @Override
    public String toString() {
        String result = String.format(
                "%s\n   Total: $%.2f | Spent: $%.2f | Remaining: $%.2f",
                activity.getName(), activityBudget, actualExpense, getRemainingBudget());

        double percentageSpent = (activityBudget == 0) ? 0 : (actualExpense / activityBudget) * 100;
        if (percentageSpent >= 90) {
            result += "\n   (Warning: You have spent " + String.format("%.1f", percentageSpent) + "% of the budget!)";
        }
        return result;
    }
    
}
