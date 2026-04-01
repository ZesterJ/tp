package seedu.traveltrio.command.activity;

import org.junit.jupiter.api.Test;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.trip.Trip;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddActivityCommandTest {

    //positive
    @Test
    public void execute_activityListAndTripName_successfulMessage() throws TravelTrioException {
        Trip trip = new Trip("Winter trip", "2026-05-01", "2026-07-30");
        ActivityList activityList = new ActivityList(trip);
        String tripName = "Japan Trip";
        AddActivityCommand addCommand = new AddActivityCommand(activityList,"Hiking at Mount Fuji",
                "Mount Fuji","2026-06-28","08:00", "14:00");
        String message = addCommand.execute(tripName);
        assertEquals("""
                Activity added to Japan Trip:
                
                Hiking at Mount Fuji
                 Location: Mount Fuji
                 Date: 2026-06-28
                 Time: 08:00 to 14:00""", message);

    }

    @Test
    public void execute_onlyActivityNameAndTripName_successfulMessage() throws TravelTrioException {
        Trip trip = new Trip("Winter trip", "1 Oct", " Dec");
        ActivityList activityList = new ActivityList(trip);
        String tripName = "Japan Trip";
        AddActivityCommand addCommand = new AddActivityCommand(activityList,"Hiking at Mount Fuji",
                null, null, null, null);
        String message = addCommand.execute(tripName);
        assertEquals("""
                Activity added to Japan Trip:
                
                Hiking at Mount Fuji
                 Location: ---
                 Date: ---
                 Time: ---""", message);

    }
}

