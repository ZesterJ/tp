package seedu.traveltrio.command;

import seedu.traveltrio.model.Activity;
import seedu.traveltrio.model.ActivityList;

public class AddActivityCommand extends ActivityCommand{
    private final String name;
    private final String location;
    private final String date;
    private final String start;
    private final String end;


    public AddActivityCommand(ActivityList activityList, String name,
                              String location, String date, String start, String end) {
        super(activityList);
        this.name = name;
        this.location = location;
        this.date = date;
        this.start = start;
        this.end = end;
    }


    public String execute(String tripName) {
        //add checks for whether a trip is open later.

        Activity a = new Activity(name, location, date, start, end);
        activityList.add(a);

        return "Activity added to " + tripName + ":\n\n" + a + "\n";
    }

}
