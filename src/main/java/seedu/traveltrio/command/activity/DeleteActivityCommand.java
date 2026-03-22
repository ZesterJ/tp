package seedu.traveltrio.command.activity;


import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;

public class DeleteActivityCommand extends ActivityCommand {
    private final int index;

    public DeleteActivityCommand(ActivityList activityList, int index) {
        super(activityList);
        this.index = index;
    }

    public String execute(String tripName) throws TravelTrioException {
        int zeroBasedIndex = index - 1;

        if (zeroBasedIndex < 0 || zeroBasedIndex >= activityList.size()) {
            throw new TravelTrioException("Activity number " + index + " does not exist.");
        }

        assert activityList.isTripOpen() : "Cannot delete an activity if no trip is open.";

        int sizeBefore = activityList.size();
        Activity removedActivity = activityList.remove(zeroBasedIndex);

        assert activityList.size() == sizeBefore - 1 : "Activity list size should decrease by 1.";

        return "Activity deleted:\n\n" + removedActivity.getName() + "\n";
    }
}

