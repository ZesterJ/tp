package seedu.traveltrio.command.finance.expense;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.budget.BudgetList;

public class SetDailyLimitCommand {
    private final BudgetList budgetList;
    private final double amount;

    public SetDailyLimitCommand(BudgetList budgetList, double amount) {
        this.budgetList = budgetList;
        this.amount = amount;
    }

    public String execute() throws TravelTrioException {
        budgetList.setDailySpendingLimit(amount);
        return String.format("Daily spending limit has been set to $%.2f.", amount);
    }
}
