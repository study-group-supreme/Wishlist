package Wishlist.model;

public class WishlistModel {
    private int id;
    private String name;
    private String description;
    private boolean isPublic;

public WishlistModel(int id, String name, String description, boolean isPublic){
    this.id = id;
    this.name = name;
    this.description = description;
    this.isPublic = isPublic;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}