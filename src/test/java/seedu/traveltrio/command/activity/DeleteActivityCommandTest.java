package seedu.traveltrio.command.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.Budget;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.model.trip.Trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteActivityCommandTest {

    private Trip trip;
    private ActivityList activityList;
    private BudgetList budgetList;
    private Activity activity;

    @BeforeEach
    public void setUp() throws TravelTrioException {
        trip = new Trip("Japan Trip", "2026-01-01", "2026-01-10");
        trip.setOpen(true);
        activityList = trip.getActivities();
        budgetList = trip.getBudgets();
        activity = new Activity("Hiking", "Forest", "2026-01-05", "09:00", "12:00");
        activityList.add(activity);
    }

    @Test
    public void execute_validIndex_activityRemovedFromList() throws TravelTrioException {
        DeleteActivityCommand cmd = new DeleteActivityCommand(activityList, budgetList, 1);
        cmd.execute("Japan Trip");

        assertEquals(0, activityList.size());
    }

    @Test
    public void execute_validIndex_returnsSuccessMessage() throws TravelTrioException {
        DeleteActivityCommand cmd = new DeleteActivityCommand(activityList, budgetList, 1);
        String result = cmd.execute("Japan Trip");

        assertEquals("Activity deleted:\n\nHiking\n", result);
    }

    @Test
    public void execute_activityWithBudget_budgetAlsoRemoved() throws TravelTrioException {
        budgetList.addBudget(activity, new Budget(100.0, activity));

        DeleteActivityCommand cmd = new DeleteActivityCommand(activityList, budgetList, 1);
        cmd.execute("Japan Trip");

        assertNull(budgetList.getBudget(activity));
        assertEquals(0, budgetList.getTotalTripBudget(), 0.001);
    }

    @Test
    public void execute_activityWithoutBudget_removedSuccessfully() throws TravelTrioException {
        DeleteActivityCommand cmd = new DeleteActivityCommand(activityList, budgetList, 1);
        cmd.execute("Japan Trip");

        assertEquals(0, activityList.size());
        assertEquals(0, budgetList.getBudgets().size());
    }

    @Test
    public void execute_deleteFirstOfMultipleActivities_remainingActivityPreserved() throws TravelTrioException {
        Activity second = new Activity("Cycling", "Park", "2026-01-06", "13:00", "15:00");
        activityList.add(second);

        DeleteActivityCommand cmd = new DeleteActivityCommand(activityList, budgetList, 1);
        cmd.execute("Japan Trip");

        assertEquals(1, activityList.size());
        assertEquals("Cycling", activityList.get(0).getName());
    }

    @Test
    public void execute_indexZero_throwsTravelTrioException() {
        DeleteActivityCommand cmd = new DeleteActivityCommand(activityList, budgetList, 0);
        assertThrows(TravelTrioException.class, () -> cmd.execute("Japan Trip"));
    }

    @Test
    public void execute_indexExceedsListSize_throwsTravelTrioException() {
        DeleteActivityCommand cmd = new DeleteActivityCommand(activityList, budgetList, 2);
        assertThrows(TravelTrioException.class, () -> cmd.execute("Japan Trip"));
    }

    @Test
    public void run_tripNotOpen_throwsTravelTrioException() throws TravelTrioException {
        trip.setOpen(false);
        DeleteActivityCommand cmd = new DeleteActivityCommand(activityList, budgetList, 1);
        assertThrows(TravelTrioException.class, () -> cmd.run("Japan Trip"));
    }
}
