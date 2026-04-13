package seedu.traveltrio.command.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.trip.Trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditActivityCommandTest {

    private Trip trip;
    private ActivityList activityList;
    private Activity activity;

    @BeforeEach
    public void setUp() throws TravelTrioException {
        trip = new Trip("Japan Trip", "2026-01-01", "2026-01-10");
        trip.setOpen(true);
        activityList = trip.getActivities();
        activity = new Activity("Hiking", "Forest", "2026-01-05", "09:00", "12:00");
        activityList.add(activity);
    }

    @Test
    public void execute_allFieldsUpdated_returnsSuccessMessage() throws TravelTrioException {
        EditActivityCommand cmd = new EditActivityCommand(
                activityList, 1, "Skiing", "Mountain", "2026-01-06", "10:00", "14:00");
        String result = cmd.execute("Japan Trip");

        assertEquals("Activity updated:\n\nSkiing\n Location: Mountain\n Date: 2026-01-06\n Time: 10:00 to 14:00\n",
                result);
    }

    @Test
    public void execute_allFieldsUpdated_activityStateReflectsChanges() throws TravelTrioException {
        EditActivityCommand cmd = new EditActivityCommand(
                activityList, 1, "Skiing", "Mountain", "2026-01-06", "10:00", "14:00");
        cmd.execute("Japan Trip");

        assertEquals("Skiing", activity.getName());
        assertEquals("Mountain", activity.getLocation());
        assertEquals("2026-01-06", activity.getDate());
        assertEquals("10:00", activity.getStart());
        assertEquals("14:00", activity.getEnd());
    }

    @Test
    public void execute_onlyNameProvided_otherFieldsUnchanged() throws TravelTrioException {
        EditActivityCommand cmd = new EditActivityCommand(
                activityList, 1, "Trekking", null, null, null, null);
        cmd.execute("Japan Trip");

        assertEquals("Trekking", activity.getName());
        assertEquals("Forest", activity.getLocation());
        assertEquals("2026-01-05", activity.getDate());
        assertEquals("09:00", activity.getStart());
        assertEquals("12:00", activity.getEnd());
    }

    @Test
    public void execute_blankFieldsProvided_fieldsUnchanged() throws TravelTrioException {
        EditActivityCommand cmd = new EditActivityCommand(
                activityList, 1, "", "  ", "", "", "");
        cmd.execute("Japan Trip");

        assertEquals("Hiking", activity.getName());
        assertEquals("Forest", activity.getLocation());
        assertEquals("2026-01-05", activity.getDate());
        assertEquals("09:00", activity.getStart());
        assertEquals("12:00", activity.getEnd());
    }

    @Test
    public void execute_indexZero_throwsTravelTrioException() {
        EditActivityCommand cmd = new EditActivityCommand(
                activityList, 0, "Skiing", null, null, null, null);
        assertThrows(TravelTrioException.class, () -> cmd.execute("Japan Trip"));
    }

    @Test
    public void execute_indexExceedsListSize_throwsTravelTrioException() {
        EditActivityCommand cmd = new EditActivityCommand(
                activityList, 2, "Skiing", null, null, null, null);
        assertThrows(TravelTrioException.class, () -> cmd.execute("Japan Trip"));
    }

    @Test
    public void execute_dateOutsideTripRange_throwsTravelTrioException() {
        EditActivityCommand cmd = new EditActivityCommand(
                activityList, 1, null, null, "2025-12-31", null, null);
        assertThrows(TravelTrioException.class, () -> cmd.execute("Japan Trip"));
    }

    @Test
    public void run_tripNotOpen_throwsTravelTrioException() throws TravelTrioException {
        trip.setOpen(false);
        EditActivityCommand cmd = new EditActivityCommand(
                activityList, 1, "Skiing", null, null, null, null);
        assertThrows(TravelTrioException.class, () -> cmd.run("Japan Trip"));
    }
}
