package seedu.traveltrio.command.packing;

import seedu.traveltrio.model.packing.PackingList;
import seedu.traveltrio.model.packing.PackingItem;

/**
 * Represents a command to mark a packing list item as packed.
 */
public class CheckItemCommand {

    private PackingList list;
    private int index;

    /**
     * Constructs a CheckItemCommand with the specified packing list and item index.
     *
     * @param list the packing list containing the item
     * @param index the 1-based index of the item to mark as packed
     */
    public CheckItemCommand(PackingList list, int index) {
        this.list = list;
        this.index = index;
    }

    /**
     * Executes the command to mark an item as packed.
     * Validates the index before marking the item.
     *
     * @return a success message indicating the item that was marked as packed
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public String execute() {
        if (index < 1 || index > list.size()) {
            throw new IndexOutOfBoundsException("Invalid item index: " + index);
        }
        PackingItem item = list.get(index - 1);
        item.markPacked();
        return "Marked as packed: " + item.getName();
    }
}
