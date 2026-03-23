package seedu.traveltrio.command.activity;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;

public class ListActivityCommand extends ActivityCommand {

    public ListActivityCommand(ActivityList activityList){
        super(activityList);
    }

    public String execute(String tripName) throws TravelTrioException {
        if (activityList.isEmpty()){
            throw new TravelTrioException("The itinerary is Empty.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Itinerary for ").append(tripName).append(":\n\n");

        for (int i = 0; i < activityList.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(activityList.get(i).formatForList())
                    .append("\n\n");
        }

        return sb.toString();

    }
}
