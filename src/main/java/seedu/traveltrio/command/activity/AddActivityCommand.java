package seedu.traveltrio.command.activity;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;

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


    public String execute(String tripName) throws TravelTrioException {
        Activity a = new Activity(name, location, date, start, end);

        String overlapWarning = activityList.add(a);
        if (overlapWarning != null) {
            throw new TravelTrioException(overlapWarning);
        }

        return "Activity added to " + tripName + ":\n\n" + a + "\n";
    }
}
