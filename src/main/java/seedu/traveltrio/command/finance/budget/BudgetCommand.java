package seedu.traveltrio.command.finance.budget;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.model.activity.Activity;

/**
 * Represents an abstract base command for all budget-related operations.
 * Provides common fields and a validation wrapper to ensure a trip is open
 * before any budget modifications or retrievals can occur.
 */
public abstract class BudgetCommand {

    protected BudgetList budgetList;
    protected ActivityList activityList;
    protected Activity activity;

    /**
     * Constructs a BudgetCommand with the specified budget list, activity list, and target activity.
     *
     * @param budgetList   The list containing the budgets for the current trip.
     * @param activityList The list containing the activities for the current trip.
     * @param activity     The specific activity to apply the budget command to,
     *                     or null if it applies to the whole trip.
     */
    public BudgetCommand(BudgetList budgetList, ActivityList activityList, Activity activity) {
        this.budgetList = budgetList;
        this.activityList = activityList;
        this.activity = activity;
    }

    /**
     * Executes the command after verifying that a trip is currently open.
     * Acts as a guard rail to prevent budget modifications when no trip context is active.
     *
     * @return A formatted string detailing the result of the command execution to be shown to the user.
     * @throws TravelTrioException If no trip is open, or if an error occurs during the execution of the command.
     */
    public String run() throws TravelTrioException {
        if (!activityList.isTripOpen()) {
            throw new TravelTrioException("Please open a trip before managing budgets.");
        } else {
            return execute();
        }
    }

    public abstract String execute() throws TravelTrioException;
}
