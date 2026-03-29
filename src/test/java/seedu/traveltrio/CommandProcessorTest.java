package seedu.traveltrio;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.traveltrio.model.trip.Trip;
import seedu.traveltrio.model.trip.TripList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandProcessorTest {
    private TripList tripList;
    private Ui ui;
    private Storage storage;
    private CommandProcessor processor;
    private final InputStream systemIn = System.in;

    @BeforeEach
    public void setUp() {
        // Creating new objects before each test
        tripList = new TripList();
        ui = new Ui();
        storage = new Storage("");
        processor = new CommandProcessor(tripList, ui, storage);
    }

    @AfterEach
    public void tearDown() {
        // Reset the keyboard inputs
        System.setIn(systemIn);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        ui = new Ui();
        processor = new CommandProcessor(tripList, ui, storage);
    }

    @Test
    public void process_validTripAdditionInput_successfulTripAddition() {
        provideInput("Europe\n2026-05-01\n2026-05-20\n");
        processor.process("addtrip");

        assertEquals(1, tripList.size());
        assertEquals("Europe", tripList.get(0).getName());
    }

    @Test
    public void process_addTripWithEmptyName_loopsUntilValid() {
        provideInput("\n\nJapan\n2025-05-01\n2025-05-10\n");
        processor.process("addtrip");

        assertEquals(1, tripList.size());
        assertEquals("Japan", tripList.get(0).getName());
    }

    @Test
    public void process_addTripWithInvalidDateOrder_loopsUntilValid() {
        // Start date is AFTER end date
        provideInput("Korea\n2026-12-31\n2026-12-01\n2026-12-01\n2026-12-31\n");
        processor.process("addtrip");

        assertEquals(1, tripList.size());
        assertEquals("Korea", tripList.get(0).getName());
        assertEquals("2026-12-01", tripList.get(0).getStartDate());
    }

    @Test
    public void process_deleteTrip_resetsActiveTrip() {
        tripList.add(new Trip("TripToKill", "2025-01-01", "2025-01-05"));

        // Sequence: Open Trip 1, then Delete Trip 1
        provideInput("1\n1\n");
        processor.process("opentrip");
        processor.process("deletetrip");

        assertEquals(0, tripList.size());
    }

    @Test
    public void process_deleteTripWithInvalidIndex_noTripDeleted() {
        tripList.add(new Trip("China", "2027-01-01", "2027-01-05"));
        provideInput("2\n"); // Invalid index
        processor.process("deletetrip");
        assertEquals(1, tripList.size());
    }

    @Test
    public void process_addValidActivity_successfulAdditionofActivity() {
        Trip trip = new Trip("Trip", "2027-01-01", "2027-01-10");
        tripList.add(trip);

        String input = "1\n" + "Osaka\nCastle\n2027-01-05\n09:00\n12:00\n";
        provideInput(input);

        processor.process("opentrip");
        processor.process("addactivity");

        assertEquals(1, trip.getActivities().size());
        assertEquals("Osaka", trip.getActivities().get(0).getName());
    }

    @Test
    public void process_addActivityDateFallsOutsideTripDates_loopsUntilValid() {
        Trip trip = new Trip("Trip", "2027-01-01", "2027-01-02");
        tripList.add(trip);

        String input = "1\n" + "Hiking\nForest\n2025-01-05\n2027-01-01\n09:00\n12:00\n";
        provideInput(input);

        processor.process("opentrip");
        processor.process("addactivity");

        assertEquals(1, trip.getActivities().size());
        assertEquals("Hiking", trip.getActivities().get(0).getName());
    }

    @Test
    public void process_addActivityThatOverlapsWithExisting_fails() {
        Trip trip = new Trip("Trip", "2027-01-01", "2027-01-10");
        tripList.add(trip);

        String input = "1\n" +
                "Walk\nPark\n2027-01-02\n10:00\n12:00\n" +
                "Cycle\nPark\n2025-01-02\n11:00\n13:00\n";
        provideInput(input);

        processor.process("opentrip");
        processor.process("addactivity"); // Success
        processor.process("addactivity"); // Fails (Overlap)

        assertEquals(1, trip.getActivities().size());
    }

    @Test
    public void process_addBudgetOfNegativeAmount_fails() {
        Trip trip = new Trip("Trip", "2027-01-01", "2027-01-10");
        tripList.add(trip);

        String input = "1\n" + "Act\nLoc\n2027-01-02\n10:00\n11:00\n" + "1\n-100.0\n";
        provideInput(input);

        processor.process("opentrip");
        processor.process("addactivity");
        processor.process("setbudget");

        assertEquals(0, trip.getBudgets().getBudgets().size());
    }
}
