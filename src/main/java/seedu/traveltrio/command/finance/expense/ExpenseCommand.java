package seedu.traveltrio.command.finance.expense;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.BudgetList;

/**
 * Represents an abstract base command for all expense-related operations.
 * An ExpenseCommand object provides common fields required to interact with
 * the trip's budgets and scheduled activities.
 */
public abstract class ExpenseCommand {

    protected BudgetList budgetList;
    protected ActivityList activityList;

    /**
     * Initializes an ExpenseCommand with the specified budget and activity lists.
     *
     * @param budgetList The list containing the budgets for the current trip.
     * @param activityList The list containing the scheduled activities for the current trip.
     */
    public ExpenseCommand(BudgetList budgetList, ActivityList activityList) {
        this.budgetList = budgetList;
        this.activityList = activityList;
    }

    /**
     * Executes the core logic of the specific expense command.
     *
     * @return Formatted string detailing the result of the command execution.
     * @throws TravelTrioException If an error occurs during the command's execution.
     */
    public abstract String execute() throws TravelTrioException;

}
