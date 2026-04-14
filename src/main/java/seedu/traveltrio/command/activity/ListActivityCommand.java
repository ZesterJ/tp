package seedu.traveltrio.command.activity;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.ActivityList;

/**
 * Represents a command to list all activities in the trip itinerary.
 * Displays activities in a formatted table with columns for activity details and remarks.
 */
public class ListActivityCommand extends ActivityCommand {
    private final String tripStartDate;
    private final String tripEndDate;

    /**
     * Constructs a ListActivityCommand with the specified activity list and trip dates.
     *
     * @param activityList the activity list to display
     * @param tripStartDate the start date of the trip for display purposes
     * @param tripEndDate the end date of the trip for display purposes
     */
    public ListActivityCommand(ActivityList activityList, String tripStartDate, String tripEndDate){
        super(activityList);
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;

    }

    /**
     * Executes the command to display all activities in a formatted table.
     * Activities are sorted by date and time.
     *
     * @param tripName the name of the current trip
     * @return a formatted string displaying all activities in a table format
     * @throws TravelTrioException if the activity list is empty
     */
    public String execute(String tripName) throws TravelTrioException {
        if (activityList.isEmpty()) {
            throw new TravelTrioException("The itinerary is Empty.");
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Itinerary for [").append(tripName).append("] (")
                .append(tripStartDate).append(" -> ").append(tripEndDate).append("):\n");

        String header = String.format("%-3s | %-20s | %-15s | %-12s | %-18s | %-20s",
                "No", "Activity", "Location", "Date", "Time", "Remark");
        int dividerLength = header.length();

        sb.append(header).append("\n");
        sb.append("-".repeat(dividerLength)).append("\n");

        for (int i = 0; i < activityList.size(); i++) {
            sb.append(activityList.get(i).formatForTableRow(i + 1)).append("\n");
        }
        return sb.toString();

    }
}
