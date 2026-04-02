package seedu.traveltrio.command.finance.budget;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.budget.Budget;

public class SetBudgetCommand extends BudgetCommand {
    private final double totalBudget;

    public SetBudgetCommand(BudgetList budgetList, ActivityList activityList, Activity activity, double totalBudget) {
        super(budgetList, activityList , activity);
        this.totalBudget = totalBudget;
    }

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
        return "Added budget for " + activity.getName() + ": $" + String.format("%.2f", totalBudget);
    }
    
}
