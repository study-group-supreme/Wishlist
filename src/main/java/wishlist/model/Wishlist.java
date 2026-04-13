package wishlist.model;

import wishlist.model.Item;

import java.util.List;

public class Wishlist {
    private int id;
    private List<Item> items;
    private String title;
    private String description;
    private boolean isPublic;
    private int owner_id;


    public Wishlist(int id, List<Item> items, String title, String description, boolean isPublic, int owner_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.items = items;
        this.owner_id = owner_id;
    }
    public Wishlist(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }
}