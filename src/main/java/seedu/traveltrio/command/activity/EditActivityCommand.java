package seedu.traveltrio.command.activity;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;

public class EditActivityCommand extends ActivityCommand{
    private final int index;
    private final String name;
    private final String location;
    private final String date;
    private final String start;
    private final String end;


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


    public String execute(String tripName) throws TravelTrioException {
        int zeroBasedIndex = index - 1;

        if (zeroBasedIndex < 0 || zeroBasedIndex >= activityList.size()) {
            throw new TravelTrioException("Activity number " + index + " does not exist.");
        }

        assert activityList != null : "ActivityList must be initialized before editing.";
        assert !activityList.isEmpty() : "Cannot edit an activity in an empty list.";

        Activity activity = activityList.get(zeroBasedIndex);

        assert activity != null : "The activity to be edited should not be null.";

        if (name != null) {
            activity.setName(name);
        }
        if (location != null) {
            activity.setLocation(location);
        }
        if (date != null) {
            activity.setDate(date);
        }
        if (start != null) {
            activity.setStart(start);
        }
        if (end != null) {
            activity.setEnd(end);
        }

        if (name != null) {
            assert !activity.getName().isBlank() : "Activity name should not be blank after edit.";
        }
        
        return "Activity updated:\n\n" + activity + "\n";
    }

}

