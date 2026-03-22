package seedu.traveltrio.model.activity;

import java.util.ArrayList;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.trip.Trip;

public class ActivityList {
    private final ArrayList<Activity> activities;
    private Trip trip;

    public ActivityList(Trip trip) {
        activities = new ArrayList<>();
        this.trip = trip;
    }

    public String add(Activity a) throws TravelTrioException {
        assert a != null : "Activity to add should not be null";

        for(Activity existing : activities){
            if (a.overlapsWith(existing)) {
                throw new TravelTrioException("Warning: This activity overlaps with an existing activity:\n\n"
                        + existing.formatForList() + "\n\n"
                        + "Please edit the existing activity or choose a different time.");
            }
        }

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

    public Activity remove(int index) {
        return activities.remove(index);
    }

    public Activity get(int index) {
        return activities.get(index);
    }

    public int size() {
        return activities.size();
    }

    public boolean isEmpty() {
        return activities.isEmpty();
    }

    public boolean isTripOpen() {
        return trip.isOpen();
    }
    
    public ArrayList<Activity> findActivities(String keyword) {
        ArrayList<Activity> matchingActivities = new ArrayList<>();
        for (Activity a: activities){
            if (a.getName().toLowerCase().contains(keyword.toLowerCase())) {
                matchingActivities.add(a);
            }
        }
        return matchingActivities;
    }
}
