package seedu.traveltrio.command.others;

public class HelpCommand {
    public static final String MESSAGE_USAGE =
            "-------------------- TRAVELTRIO HELP GUIDE --------------------\n"
                    + "Here are the available commands. After entering a command,\n"
                    + "the app will guide you step-by-step through the process.\n\n"

                    + "--- Trip Commands ---\n"
                    + "1. addtrip       : Start the process to add a new trip.\n"
                    + "2. listtrip      : View a list of all your planned trips.\n"
                    + "3. opentrip      : Select a specific trip to manage its activities.\n"
                    + "4. deletetrip    : Choose a trip to delete from your list.\n\n"

                    + "--- Activity Commands (Requires an open trip) ---\n"
                    + "5. addactivity   : Add a new activity (like a flight or tour) to the open trip.\n"
                    + "6. listactivity  : View all activities scheduled for the open trip.\n"
                    + "7. editactivity  : Modify the details of an existing activity.\n"
                    + "8. deleteactivity: Remove an activity from the itinerary.\n\n"

                    + "--- Budget Commands (Requires an open trip) ---\n"
                    + "9. setbudget     : Assign a budget to a specific activity.\n"
                    + "10. setexpense   : Set the actual spending for a specific activity that has a budget.\n"
                    + "11. setcurrency  : Update the exchange rate for converting foreign currency expenses to home"
                            + " currency.\n"
                    + "12. budgetsummary: View a breakdown of expenses and remaining budget.\n"
                    + "13. listexpense  : compare budget and actual spending for activities, and show total spending.\n"
                    + "14. setdailylimit: set a daily limit for expenses, warn users when limit exceeds.\n\n"

                    + "--- General Commands ---\n"
                    + "15. help         : Show this help menu.\n"
                    + "16. exit         : Exit the application.\n"
                    + "---------------------------------------------------------------";

    public String execute() {
        return MESSAGE_USAGE;
    }
}
