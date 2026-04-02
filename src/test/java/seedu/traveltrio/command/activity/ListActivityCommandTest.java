package seedu.traveltrio.command.activity;

import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.trip.Trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListActivityCommandTest {

    @Test
    void execute_emptyActivityList_throwsException() {
        Trip trip = new Trip("Winter trip", "2026-12-01", "2026-12-30");
        ActivityList activityList = new ActivityList(trip);
        ListActivityCommand command = new ListActivityCommand(activityList, trip.getStartDate(), trip.getEndDate());

        TravelTrioException exception = assertThrows(TravelTrioException.class,
                () -> command.execute("Winter trip")
        );

        assertEquals("The itinerary is Empty.", exception.getMessage());
    }

    @Test
    void execute_nonEmptyActivityList_returnsItineraryList() throws TravelTrioException {
        Trip trip = new Trip("Japan Trip", "2026-06-01", "2026-06-15");
        ActivityList activityList = new ActivityList(trip);
        Activity activity1 = new Activity("Diving", "Okinawa",
                "2026-06-10", "09:00", "15:00");
        Activity activity2 = new Activity("Hiking", "Fuji Mountain",
                "2026-06-13", "13:00", "14:30");

        activityList.add(activity1);
        activityList.add(activity2);

        ListActivityCommand command = new ListActivityCommand(activityList, trip.getStartDate(), trip.getEndDate());
        String result = command.execute("Japan Trip");

        String header = String.format("%-3s | %-25s | %-15s | %-12s | %-18s",
                "No", "Activity", "Location", "Date", "Time");
        String expected = "Itinerary for [Japan Trip] (2026-06-01 -> 2026-06-15):\n"
                + header + "\n"
                + "-".repeat(header.length()) + "\n"
                + activity1.formatForTableRow(1) + "\n"
                + activity2.formatForTableRow(2) + "\n";

        assertEquals(expected, result);

    }
}

