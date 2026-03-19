package seedu.traveltrio.model;

import java.util.ArrayList;

public class TripList {
    private final ArrayList<Trip> trips;

    public TripList() {
        trips = new ArrayList<>();
    }

    public void add(Trip t) {
        trips.add(t);
    }

    public Trip remove(int index) {
        return trips.remove(index);
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
        for (Trip t : trips) {
            if (t.getName().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTrips.add(t);
            }
        }
        return matchingTrips;
    }
}

