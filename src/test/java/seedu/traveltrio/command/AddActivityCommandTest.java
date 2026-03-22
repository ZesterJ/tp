package seedu.traveltrio.command;

import org.junit.jupiter.api.Test;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.command.activity.AddActivityCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddActivityCommandTest {

    //positive
    @Test
    void execute_activityListAndTripName_successfulMessage() throws TravelTrioException {
        Trip trip = new Trip("Winter trip", "1 Oct", " Dec");
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
                 Time: 08:00 to 14:00
                """, message);

    }

    @Test
    void execute_onlyActivityNameAndTripName_successfulMessage() throws TravelTrioException {
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
                 Time: ---
                """, message);

    }
}

