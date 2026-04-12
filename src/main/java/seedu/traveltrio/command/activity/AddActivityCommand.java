package seedu.traveltrio.command.activity;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.trip.Trip;

import java.time.LocalDate;

/**
 * Represents a command to add a new activity to the trip itinerary.
 * Checks for scheduling conflicts before adding the activity.
 */
public class AddActivityCommand extends ActivityCommand {
    private final String name;
    private final String location;
    private final String date;
    private final String start;
    private final String end;


    /**
     * Constructs an AddActivityCommand with the specified activity details.
     *
     * @param activityList the activity list to add the activity to
     * @param name the name of the activity
     * @param location the location of the activity
     * @param date the date of the activity (YYYY-MM-DD format)
     * @param start the start time of the activity (HH:mm format)
     * @param end the end time of the activity (HH:mm format)
     */
    public AddActivityCommand(ActivityList activityList, String name,
                              String location, String date, String start, String end) {
        super(activityList);
        this.name = name;
        this.location = location;
        this.date = date;
        this.start = start;
        this.end = end;
    }


    /**
     * Executes the command to add a new activity to the itinerary.
     * Validates that the activity does not overlap with existing activities.
     *
     * @param tripName the name of the current trip
     * @return a success message with the added activity details
     * @throws TravelTrioException if the activity overlaps with an existing activity
     */
    public String execute(String tripName) throws TravelTrioException {
        validateActivityDateWithinTripRange(date, activityList);
        Activity a = new Activity(name, location, date, start, end);

        String overlapWarning = activityList.add(a);
        if (overlapWarning != null) {
            throw new TravelTrioException(overlapWarning);
        }

        return "Activity added to " + tripName + ":\n\n" + a;
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
