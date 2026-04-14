package wishlist.repository;

import wishlist.model.Item;
import wishlist.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbc;
    private final ItemRepository itemRepository;

    public WishlistRepository(JdbcTemplate jdbc, ItemRepository itemRepository) {
        this.jdbc = jdbc;
        this.itemRepository = itemRepository;
    }

    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> {
        Wishlist w = new Wishlist();
        w.setId(rs.getInt("id"));
        w.setTitle(rs.getString("title"));
        w.setDescription(rs.getString("description"));
        w.setPublic(rs.getBoolean("is_public"));
        w.setOwner_id(rs.getInt("member_id"));
        w.setItems(fetchItemsById(w.getId()));
        return w;
    };

    public Wishlist findWishlistById(int id) {
        String sql = "SELECT * FROM Wishlist WHERE id = ?";
        return jdbc.queryForObject(sql, wishlistRowMapper, id);
    }

    public Wishlist findWishlistByTitle(String title) {
        String sql = "SELECT * FROM Wishlist WHERE title = ?";
        return jdbc.queryForObject(sql, wishlistRowMapper, title);
    }

    public Wishlist findWishlistByOwnerId(int Owner_id) {
        String sql = "SELECT * FROM Wishlist WHERE member_id = ?";
        return jdbc.queryForObject(sql, wishlistRowMapper, Owner_id);
    }

    public List<Item> fetchItemsById(int id) {
        String sql = """
                SELECT item.id, item.title, item.description, item.url, item.price
                FROM item
                JOIN wishlist_item
                ON item.id = wishlist_item.item_id
                WHERE wishlist_item.wishlist_id = ?
                """;
        return jdbc.query(sql, itemRepository.getItemRowMapper(), id);
    }

//        public void deleteWishlist(int id){
//        String sql = "DELETE FROM wishlist WHERE id = ?";
//        jdbc.update(sql, id);
//    }

    public List<Wishlist> getAllWishlists() {
        String sql = "SELECT * FROM wishlist ORDER BY id";
        return jdbc.query(sql, wishlistRowMapper);
    }

    public int insertWishlist(Wishlist model) {
        String sql = "INSERT INTO wishlist(title, description, member_id, is_public) VALUES (?,?,?,?)";
        return jdbc.update(sql,
                model.getTitle(),
                model.getDescription(),
                model.getOwner_id(),
                model.isPublic());
    }
    public int update(Wishlist model){
        String sql = """
                UPDATE wishlist
    SET title = ?, SET description = ?, SET is_public = ?
    WHERE id = ?
    """;
        return jdbc.update(sql,
                model.getTitle(),
                model.getDescription(),
                model.isPublic());
    }
}


