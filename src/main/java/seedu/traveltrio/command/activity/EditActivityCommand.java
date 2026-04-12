package seedu.traveltrio.command.activity;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.trip.Trip;

import java.time.LocalDate;

/**
 * Represents a command to edit an existing activity in the itinerary.
 * Allows partial updates to activity fields.
 */
public class EditActivityCommand extends ActivityCommand {
    private final int index;
    private final String name;
    private final String location;
    private final String date;
    private final String start;
    private final String end;


    /**
     * Constructs an EditActivityCommand with the specified activity details.
     * Null or blank fields will not be updated.
     *
     * @param activityList the activity list containing the activity to edit
     * @param index the 1-based index of the activity to edit
     * @param name the new name for the activity, or null to keep unchanged
     * @param location the new location for the activity, or null to keep unchanged
     * @param date the new date for the activity, or null to keep unchanged
     * @param start the new start time for the activity, or null to keep unchanged
     * @param end the new end time for the activity, or null to keep unchanged
     */
    public EditActivityCommand(ActivityList activityList, int index, String name, String location, String date,
                               String start, String end) {
        super(activityList);
        this.index = index;
        this.name = name;
        this.location = location;
        this.date = date;
        this.start = start;
        this.end = end;
    }


    /**
     * Executes the command to edit an existing activity.
     * Only updates fields that are provided (non-null and non-blank).
     *
     * @param tripName the name of the current trip
     * @return a success message with the updated activity details
     * @throws TravelTrioException if the specified index is invalid
     */
    public String execute(String tripName) throws TravelTrioException {
        int zeroBasedIndex = index - 1;

        if (zeroBasedIndex < 0 || zeroBasedIndex >= activityList.size()) {
            throw new TravelTrioException("Activity number " + index + " does not exist.");
        }

        assert activityList != null : "ActivityList must be initialized before editing.";
        assert !activityList.isEmpty() : "Cannot edit an activity in an empty list.";

        Activity activity = activityList.get(zeroBasedIndex);

        assert activity != null : "The activity to be edited should not be null.";

        if (name != null && !name.isBlank()) {
            activity.setName(name);
        }
        if (location != null && !location.isBlank()) {
            activity.setLocation(location);
        }
        if (date != null && !date.isBlank()) {
            validateActivityDateWithinTripRange(date, activityList);
            activity.setDate(date);
        }
        if (start != null && !start.isBlank()) {
            activity.setStart(start);
        }
        if (end != null && !end.isBlank()) {
            activity.setEnd(end);
        }

        if (name != null) {
            assert !activity.getName().isBlank() : "Activity name should not be blank after edit.";
        }
        return "Activity updated:\n\n" + activity + "\n";
    }

    /**
     * Validates that the given activity date falls within the trip's start and end dates.
     *
     * @param date the activity date to validate (YYYY-MM-DD format)
     * @param activityList the activity list containing the trip reference
     * @throws TravelTrioException if the date is outside the trip's date range
     */
    private void validateActivityDateWithinTripRange(String date, ActivityList activityList) throws TravelTrioException {
        if (date == null || date.isBlank()) {
            return;
        }
        Trip trip = activityList.getTrip();
        if (trip != null) {
            LocalDate activityDate = LocalDate.parse(date);
            LocalDate tripStartDate = LocalDate.parse(trip.getStartDate());
            LocalDate tripEndDate = LocalDate.parse(trip.getEndDate());

            if (activityDate.isBefore(tripStartDate) || activityDate.isAfter(tripEndDate)) {
                throw new TravelTrioException("Invalid activity date: " + date
                        + ". Activity date must be within the trip period ("
                        + trip.getStartDate() + " to " + trip.getEndDate() + ").");
            }
        }
    }
}

