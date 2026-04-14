package seedu.traveltrio.command.packing;

import seedu.traveltrio.model.packing.PackingList;

/**
 * Represents a command to delete an item from the packing list.
 */
public class DeleteItemCommand {

    private PackingList list;
    private int index;

    /**
     * Constructs a DeleteItemCommand with the specified packing list and item index.
     *
     * @param list the packing list to remove the item from
     * @param index the 1-based index of the item to remove
     */
    public DeleteItemCommand(PackingList list, int index) {
        this.list = list;
        this.index = index;
    }

    /**
     * Executes the command to delete an item from the packing list.
     * Validates the index before removing the item.
     *
     * @return a success message indicating the item that was removed
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public String execute() {
        if (index < 1 || index > list.size()) {
            throw new IndexOutOfBoundsException("Invalid item index: " + index);
        }
        String name = list.get(index - 1).getName();
        list.remove(index - 1);
        return "Removed item: " + name;
    }
}
