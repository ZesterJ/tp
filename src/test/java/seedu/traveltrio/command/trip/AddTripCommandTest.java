package seedu.traveltrio.command.trip;

import org.junit.jupiter.api.Test;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.TripList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddTripCommandTest {

    @Test
    public void execute_validTripDetails_success() throws TravelTrioException {
        TripList tripList = new TripList();
        AddTripCommand command = new AddTripCommand(tripList, "Japan Trip", "2026-05-01", "2026-05-10");
        String result = command.execute();

        assertEquals("New trip added:\nJapan Trip (2026-05-01 to 2026-05-10)", result);
        assertEquals(1, tripList.size());
        assertEquals("Japan Trip", tripList.get(0).getName());
    }

    @Test
    public void execute_emptyName_throwsException() {
        TripList tripList = new TripList();
        AddTripCommand command = new AddTripCommand(tripList, "", "2026-05-01", "2026-05-10");

        TravelTrioException exception = assertThrows(TravelTrioException.class, () -> command.execute());
        assertEquals("Trip name cannot be empty.", exception.getMessage());
    }

    @Test
    public void execute_nullName_throwsException() {
        TripList tripList = new TripList();
        AddTripCommand command = new AddTripCommand(tripList, null, "2026-05-01", "2026-05-10");

        TravelTrioException exception = assertThrows(TravelTrioException.class, () -> command.execute());
        assertEquals("Trip name cannot be empty.", exception.getMessage());
    }

    @Test
    public void execute_nullStartDate_throwsException() {
        TripList tripList = new TripList();
        AddTripCommand command = new AddTripCommand(tripList, "Japan Trip", null, "2026-05-10");

        TravelTrioException exception = assertThrows(TravelTrioException.class, () -> command.execute());
        assertEquals("Start date and end date must be provided.", exception.getMessage());
    }

    @Test
    public void execute_nullEndDate_throwsException() {
        TripList tripList = new TripList();
        AddTripCommand command = new AddTripCommand(tripList, "Japan Trip", "2026-05-01", null);

        TravelTrioException exception = assertThrows(TravelTrioException.class, () -> command.execute());
        assertEquals("Start date and end date must be provided.", exception.getMessage());
    }

    @Test
    public void execute_startDateAfterEndDate_throwsException() {
        TripList tripList = new TripList();
        AddTripCommand command = new AddTripCommand(tripList, "Japan Trip", "2026-05-10", "2026-05-01");

        TravelTrioException exception = assertThrows(TravelTrioException.class, () -> command.execute());
        assertEquals("Start date must not be later than end date.", exception.getMessage());
    }
}
