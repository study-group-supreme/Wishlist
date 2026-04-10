package Wishlist.model;

public class Item {
    private int id;
    private String name;
    private String description;
    private String url;
    private long price;

    public Item() {
    }

    public Item(int id, String name, String description, String url, long price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.price = price;
    }

    public int getId(){
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
