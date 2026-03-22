package seedu.traveltrio.model.budget;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;

public class Budget {
    private double totalBudget;
    private double amountSpent;
    private final Activity activity;

    public Budget(double totalBudget, Activity activity) throws TravelTrioException {
        if (totalBudget < 0) {
            throw new TravelTrioException("Total budget cannot be negative.");
        }
        this.totalBudget = totalBudget;
        this.amountSpent = 0;
        this.activity = activity;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public double getRemainingBudget() {
        return totalBudget - amountSpent;
    }

    public void addExpense(double amount) throws TravelTrioException {
        if (amount < 0) {
            throw new TravelTrioException("Expense amount cannot be negative.");
        }
        if (amount > getRemainingBudget()) {
            throw new TravelTrioException("Expense exceeds remaining budget.");
        }
        amountSpent = amount;
    }

    @Override
    public String toString() {
        return String.format("Total Budget for %s: $%.2f, Amount Spent: $%.2f, Remaining: $%.2f",
                activity.getName(), totalBudget, amountSpent, getRemainingBudget());
    }
    
}
