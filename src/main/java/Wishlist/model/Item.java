package Wishlist.model;

public class Item {
    private int id;
    private String name;
    private String description;
    private long price;

    public Item(int id, String name, String description, long price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
