package seedu.traveltrio.command.packing;

import seedu.traveltrio.model.packing.PackingList;
import seedu.traveltrio.model.packing.PackingItem;

/**
 * Represents a command to add a new item to the packing list.
 */
public class AddItemCommand {

    private PackingList list;
    private String itemName;

    /**
     * Constructs an AddItemCommand with the specified packing list and item name.
     *
     * @param list the packing list to add the item to
     * @param itemName the name of the item to add
     */
    public AddItemCommand(PackingList list, String itemName) {
        this.list = list;
        this.itemName = itemName;
    }

    /**
     * Executes the command to add a new item to the packing list.
     * Validates the item name before adding.
     *
     * @return a success message indicating the item that was added
     * @throws IllegalArgumentException if the item name is empty or null
     */
    public String execute() {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }
        String trimmedName = itemName.trim();
        PackingItem item = new PackingItem(trimmedName);
        list.addItem(item);
        return "Added item: " + trimmedName;
    }
}
