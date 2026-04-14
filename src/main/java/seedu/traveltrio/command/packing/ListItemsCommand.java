package seedu.traveltrio.command.packing;

import seedu.traveltrio.model.packing.PackingList;

/**
 * Represents a command to list all items in the packing list.
 */
public class ListItemsCommand {

    private PackingList list;

    /**
     * Constructs a ListItemCommand with the specified packing list.
     *
     * @param list the packing list to display
     */
    public ListItemsCommand(PackingList list) {
        this.list = list;
    }

    /**
     * Executes the command to display all items in the packing list.
     * Shows each item with its packed/unpacked status.
     *
     * @return a formatted string displaying all packing items, or a message if the list is empty
     */
    public String execute() {
        if (list.isEmpty()) {
            return "Packing list is empty.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Packing List:\n");

        for (int i = 0; i < list.size(); i++) {
            sb.append(i + 1).append(". ")
                    .append(list.get(i).toString())
                    .append("\n");
        }

        return sb.toString();
    }
}
