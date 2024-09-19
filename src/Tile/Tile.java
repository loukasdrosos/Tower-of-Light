package Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import Item.*;

public class Tile {
    // Image representing the tile's appearance
    public BufferedImage image;

    // Flag indicating whether the tile is solid (true) or passable (false)
    public boolean collision = false;

    // A list  that holds the items on a tile
    private List<Item> itemsOnTile;

    public Tile() {
        itemsOnTile = new ArrayList<>();
    }

    // Method to add an item to the tile, each tile can hold 20 items
    public void addItem(Item item) {
        if (itemsOnTile.size() < 20) {
            itemsOnTile.add(item);
        }
    }

    // Method to get all items on the tile
    public List<Item> getItemsOnTile() {
        return itemsOnTile;
    }

    // Method to check if the tile has any items
    public boolean hasItems() {
        if (itemsOnTile.isEmpty()) {
            return false;
        }
        return true;
    }

    // Method to get the first item on the tile
    public Item getFirstItem() {
        if (!itemsOnTile.isEmpty()) {
            return itemsOnTile.getFirst();
        }
        return null;
    }

    // Method to display items on the tile
    public void displayItems() {
        if (itemsOnTile.isEmpty()) {
            System.out.println("No items on this tile.");
        } else {
            for (Item item : itemsOnTile) {
                System.out.println(item.getName()); // Assuming Item has a getName() method
            }
        }
    }
}
