package seedu.duke.model;

import java.util.ArrayList;

public class TripList {
    private final ArrayList<Trip> trips;

    public TripList() {
        this.trips = new ArrayList<>();
    }

    public void add(Trip trip) {
        trips.add(trip);
    }

    public void remove(Trip trip) {
        trips.remove(trip);
    }

    public Trip get(int index) {
        return trips.get(index);
    }

    public int size() {
        return trips.size();
    }

    public boolean isEmpty() {
        return trips.isEmpty();
    }

    public ArrayList<Trip> findTrips(String keyword) {
        ArrayList<Trip> matchingTrips = new ArrayList<>();
        for (Trip t: trips){
            if (t.getDestination().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTrips.add(t);
            }
        }
        return matchingTrips;
    }
}
