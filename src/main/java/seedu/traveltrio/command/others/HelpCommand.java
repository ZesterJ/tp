package seedu.traveltrio.command.others;

/**
 * Represents a command to display the application's help guide. A HelpCommand object retrieves
 * a comprehensive list of all available commands, their categories, and brief descriptions
 * to assist the user in navigating the CLI.
 */
public class HelpCommand {
    public static final String MESSAGE_USAGE =
            "-------------------- TRAVELTRIO HELP GUIDE --------------------\n"
                    + "Here are the available commands. After entering a command,\n"
                    + "the app will guide you step-by-step through the process.\n\n"

                    + "--- Trip Commands ---\n"
                    + "1. addtrip        : Create a new trip.\n"
                    + "2. listtrip       : View a list of all your planned trips.\n"
                    + "3. opentrip       : Select a specific trip to manage it.\n"
                    + "4. deletetrip     : Choose a trip to delete from your list.\n"
                    + "5. exporttrip     : Export a specific trip to a text file for sharing.\n"
                    + "6. importtrip     : Import a shared trip from a text file.\n\n"

                    + "--- Activity Commands (Requires an open trip) ---\n"
                    + "7. addactivity    : Add an activity (like a flight or tour) to the open trip.\n"
                    + "8. listactivity   : View all activities scheduled for the open trip.\n"
                    + "9. editactivity   : Modify the details of an existing activity.\n"
                    + "10. deleteactivity: Remove an activity from the itinerary.\n"
                    + "11. nextactivity  : Show the next upcoming activity based on current time.\n"
                    + "12. addremark     : Add a custom remark or note to a specific activity.\n\n"

                    + "--- Budget Commands (Requires an open trip) ---\n"
                    + "13. setbudget     : Assign a budget to a specific activity.\n"
                    + "14. setexpense    : Set the actual spending for a specific activity that has a budget.\n"
                    + "15. setcurrency   : Update the exchange rate for converting foreign currency expenses to home"
                            + " currency.\n"
                    + "16. budgetsummary : View a breakdown of expenses and remaining budget.\n"
                    + "17. budgetchart   : View a visual chart of your budget versus actual spending.\n"
                    + "18. listexpense   : compare budget and actual spending for activities, "
                            + "and show total spending.\n"
                    + "19. setdailylimit : set a daily limit for expenses, warn users when limit exceeds.\n\n"

                    + "--- Packing Commands (Requires an open trip) ---\n"
                    + "20. additem       : Add a new item to your packing list.\n"
                    + "21. listitems     : View all items in your packing list.\n"
                    + "22. checkitem     : Mark an item in your packing list as packed.\n"
                    + "23. deleteitem    : Remove an item from your packing list.\n\n"

                    + "--- General Commands ---\n"
                    + "24. help          : Show this help menu.\n"
                    + "25. exit          : Exit the application.\n"
                    + "---------------------------------------------------------------";

    /**
     * Executes the command to retrieve the help menu.
     *
     * @return Formatted string containing the complete user guide and command list.
     */
    public String execute() {
        return MESSAGE_USAGE;
    }
}
