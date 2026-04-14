package seedu.traveltrio.model.packing;

/**
 * Represents an item in the packing list with a name and packed status.
 */
public class PackingItem {

    private String name;
    private boolean isPacked;

    /**
     * Constructs a PackingItem with the specified name.
     * New items are initially marked as unpacked.
     *
     * @param name the name of the packing item
     */
    public PackingItem(String name) {
        this.name = name;
        this.isPacked = false;
    }

    /**
     * Returns the name of this packing item.
     *
     * @return the item name
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if this item is marked as packed.
     *
     * @return true if the item is packed, false otherwise
     */
    public boolean isPacked() {
        return isPacked;
    }

    /**
     * Marks this item as packed.
     */
    public void markPacked() {
        this.isPacked = true;
    }

    /**
     * Marks this item as unpacked.
     */
    public void markUnpacked() {
        this.isPacked = false;
    }

    /**
     * Converts this item to a string format suitable for file storage.
     * Format: "1|name" for packed items, "0|name" for unpacked items.
     *
     * @return the serialized string representation
     */
    public String toFileFormat() {
        return (isPacked ? "1" : "0") + "|" + name;
    }

    /**
     * Returns a string representation of the packing item for display to the user.
     * Includes a checkbox indicator showing its packed status.
     *
     * @return Formatted string representing the packing item.
     */
    @Override
    public String toString() {
        return (isPacked ? "[X] " : "[ ] ") + name;
    }
}
