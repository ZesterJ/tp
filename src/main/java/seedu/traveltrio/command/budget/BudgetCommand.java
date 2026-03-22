package seedu.traveltrio.command.budget;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.model.activity.Activity;

public abstract class BudgetCommand {
    protected BudgetList budgetList;
    protected ActivityList activityList;
    protected Activity activity;

    public BudgetCommand(BudgetList budgetList, ActivityList activityList, Activity activity) {
        this.budgetList = budgetList;
        this.activityList = activityList;
        this.activity = activity;
    }

    public String run() throws TravelTrioException {
        if (!activityList.isTripOpen()) {
            throw new TravelTrioException("Please open a trip before managing budgets.");
        } else {
            return execute();
        }
    }

    public abstract String execute() throws TravelTrioException;
}
