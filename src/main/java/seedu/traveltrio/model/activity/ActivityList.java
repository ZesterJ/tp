package seedu.traveltrio.model.activity;

import java.util.ArrayList;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;

/**
 * Represents a collection of activities for a trip.
 * Maintains activities in sorted order by date and time, and checks for scheduling conflicts.
 */
public class ActivityList {

    private final ArrayList<Activity> activities;
    private Trip trip;

    /**
     * Constructs an ActivityList associated with the specified trip.
     *
     * @param trip the trip that this activity list belongs to
     */
    public ActivityList(Trip trip) {
        activities = new ArrayList<>();
        this.trip = trip;
    }

    /**
     * Adds an activity to the list after checking for time conflicts.
     * Activities are inserted in sorted order by date and time.
     *
     * @param a the activity to add
     * @return null if successful, or an error message if there is a conflict
     * @throws TravelTrioException if the activity overlaps with an existing activity
     */
    public String add(Activity a) throws TravelTrioException {
        assert a != null : "Activity to add should not be null";

        for (Activity existing : activities) {
            if (a.overlapsWith(existing)) {
                throw new TravelTrioException("Warning: This activity overlaps with an existing activity:\n\n"
                        + existing.formatForDisplay() + "\n\n"
                        + "Please edit the existing activity or choose a different time.");
            }
        }
        // find position in sorted activity list to insert new activity
        int insertIdx = activities.size();
        for (int i = 0; i < activities.size(); i ++) {
            Activity current = activities.get(i);
            if (isEarlier(a, current)) {
                insertIdx = i;
                break;
            }
        }
        activities.add(insertIdx, a);
        return null;
    }

    /**
     * Compares two activities to determine if the first one is earlier than the second.
     * Compares by date first, then by start time if dates are equal.
     *
     * @param a the first activity
     * @param b the second activity
     * @return true if activity a is earlier than activity b, false otherwise
     */
    private boolean isEarlier(Activity a, Activity b){
        if (a.getDate() == null) {
            return false;
        }
        if (b.getDate() == null) {
            return true;
        }
        int dateCmp = a.getDate().compareTo(b.getDate());
        if (dateCmp != 0) {
            return dateCmp < 0;
        }
        if (a.getLocalStart() == null) {
            return false;
        }
        if (b.getLocalStart() == null) {
            return true;
        }
        return a.getLocalStart().isBefore(b.getLocalStart());
    }

    /**
     * Removes the activity at the specified index.
     *
     * @param index the index of the activity to remove
     * @return the removed activity
     */
    public Activity remove(int index) {
        return activities.remove(index);
    }

    /**
     * Retrieves the activity at the specified index.
     *
     * @param index the index of the activity to retrieve
     * @return the activity at the specified index
     */
    public Activity get(int index) {
        return activities.get(index);
    }

    /**
     * Returns the number of activities in the list.
     *
     * @return the size of the activity list
     */
    public int size() {
        return activities.size();
    }

    /**
     * Checks if the activity list is empty.
     *
     * @return true if the list contains no activities, false otherwise
     */
    public boolean isEmpty() {
        return activities.isEmpty();
    }

    /**
     * Checks if the associated trip is currently open.
     *
     * @return true if the trip is open, false otherwise
     */
    public boolean isTripOpen() {
        return trip.isOpen();
    }

    /**
     * Returns all activities in the list.
     *
     * @return the list of all activities
     */
    public ArrayList<Activity> getAll() {
        return activities;
    }

    /**
     * Finds activities whose names contain the specified keyword.
     * Search is case-insensitive.
     *
     * @param keyword the keyword to search for
     * @return a list of activities matching the keyword
     */
    public ArrayList<Activity> findActivities(String keyword) {
        ArrayList<Activity> matchingActivities = new ArrayList<>();
        for (Activity a: activities){
            if (a.getName().toLowerCase().contains(keyword.toLowerCase())) {
                matchingActivities.add(a);
            }
        }
        return matchingActivities;
    }

    /**
     * Returns the trip associated with this activity list.
     *
     * @return the trip
     */
    public Trip getTrip() {
        return trip;
    }
}
