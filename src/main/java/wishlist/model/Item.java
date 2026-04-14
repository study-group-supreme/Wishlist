package wishlist.model;

public class Item {
    private int id;
    private String name;
    private String description;
    private String url;
    private long price;

    private String note;

    public Item() {
    }

    public Item(String name, String description, String url, long price) {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
