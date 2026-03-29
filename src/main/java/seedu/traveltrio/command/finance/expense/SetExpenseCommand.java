package seedu.traveltrio.command.finance.expense;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.budget.BudgetList;

public class SetExpenseCommand extends ExpenseCommand{
    private final Activity activity;
    private final double amount;
    public SetExpenseCommand(BudgetList budgetList, ActivityList activityList,
                             Activity activity, double amount){
        super(budgetList, activityList);
        this.activity = activity;
        this.amount = amount;
    }

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
