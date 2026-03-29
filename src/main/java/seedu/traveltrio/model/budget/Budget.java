package seedu.traveltrio.model.budget;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;

public class Budget {
    private double activityBudget;
    private double actualExpense;
    private final Activity activity;

    public Budget(double activityBudget, Activity activity) throws TravelTrioException {
        if (activityBudget < 0) {
            throw new TravelTrioException("Activity budget cannot be negative.");
        }
        this.activityBudget = activityBudget;
        this.actualExpense = 0;
        this.activity = activity;
    }

    public double getActivityBudget() {
        return activityBudget;
    }

    public double getActualExpense() {
        return actualExpense;
    }

    public double getRemainingBudget() {
        return activityBudget - actualExpense;
    }

    public void setActualExpense(double amount) throws TravelTrioException {
        if (amount < 0) {
            throw new TravelTrioException("Expense amount cannot be negative.");
        }
        if (amount > getRemainingBudget()) {
            throw new TravelTrioException("Expense exceeds remaining budget by " 
                    + String.format("%.2f", amount - getRemainingBudget()) 
                    + ". You must adjust the activity budget or reduce the expense amount.");
        }
        actualExpense = amount;
    }

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
