package seedu.traveltrio.command.activity;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;

public class AddRemarkCommand extends ActivityCommand {

    private final int index;
    private final String remark;

    public AddRemarkCommand(ActivityList activityList, int index, String remark) {
        super(activityList);
        this.index = index;
        this.remark = remark;
    }

    @Override
    public String execute(String tripName) throws TravelTrioException {

        if (index < 1 || index > activityList.size()) {
            throw new TravelTrioException("Invalid activity index.");
        }

        Activity activity = activityList.get(index - 1);
        activity.setRemark(remark);

        return "===========================================================\n"
                + "Remark added to activity in " + tripName + ":\n\n"
                + activity.getName() + "\n"
                + "Remark: " + remark + "\n"
                + "===========================================================";
    }
}
