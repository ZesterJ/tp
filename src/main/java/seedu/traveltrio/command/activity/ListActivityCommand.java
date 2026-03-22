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

        String listToPrint = "Itinerary for " + tripName + ":\n\n";
        for (int i = 0; i < activityList.size(); i++){
            listToPrint += ((i+1) + ". " + activityList.get(i).formatForList() + "\n\n");
        }
        return listToPrint;

    }
}
