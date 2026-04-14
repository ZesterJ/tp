package seedu.traveltrio.model.packing;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of packing items for a trip.
 * Manages the collection of items that need to be packed.
 */
public class PackingList {

    private final List<PackingItem> items;

    /**
     * Constructs an empty PackingList.
     */
    public PackingList() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds a new item to the packing list.
     *
     * @param item the packing item to add
     */
    public void addItem(PackingItem item) {
        items.add(item);
    }

    /**
     * Retrieves the packing item at the specified index.
     *
     * @param index the index of the item to retrieve
     * @return the packing item at the specified index
     */
    public PackingItem get(int index) {
        return items.get(index);
    }

    /**
     * Removes the packing item at the specified index.
     *
     * @param index the index of the item to remove
     */
    public void remove(int index) {
        items.remove(index);
    }

    /**
     * Returns the number of items in the packing list.
     *
     * @return the size of the packing list
     */
    public int size() {
        return items.size();
    }

    /**
     * Checks if the packing list is empty.
     *
     * @return true if the list contains no items, false otherwise
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Returns all items in the packing list.
     *
     * @return the list of packing items
     */
    public List<PackingItem> getItems() {
        return items;
    }

    /**
     * Converts the packing list to a string format suitable for file storage.
     * Each item is serialized on a separate line.
     *
     * @return the serialized string representation of the packing list
     */
    public String toFileFormat() {
        StringBuilder sb = new StringBuilder();
        for (PackingItem item : items) {
            sb.append(item.toFileFormat()).append("\n");
        }
        return sb.toString();
    }
}
