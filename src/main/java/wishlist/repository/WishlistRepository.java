package wishlist.repository;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import wishlist.model.Item;
import wishlist.model.Member;
import wishlist.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
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
        w.setItems(fetchItemsByWishlistId(w.getId()));
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

    public List<Wishlist> findWishlistByOwnerId(int Owner_id) {
        String sql = "SELECT * FROM Wishlist WHERE member_id = ?";
        return jdbc.query(sql, wishlistRowMapper, Owner_id);
    }

    public List<Item> fetchItemsByWishlistId(int id) {
        String sql = """
                SELECT item.id, item.title, item.description, wishlist_item.url, wishlist_item.price, wishlist_item.note
                FROM item
                LEFT JOIN wishlist_item
                ON item.id = wishlist_item.item_id
                WHERE wishlist_item.wishlist_id = ?
                """;
        return jdbc.query(sql, itemRepository.getItemRowMapper(), id);
    }

       public int deleteWishlist(int id){
        String sql = "DELETE FROM wishlist WHERE id = ?";
        return jdbc.update(sql, id);
    }

    public List<Wishlist> getAllWishlists() {
        String sql = "SELECT * FROM wishlist ORDER BY id";
        return jdbc.query(sql, wishlistRowMapper);
    }

    public int insertWishlist(Wishlist model) {
        String sql = """
            INSERT INTO wishlist (title, description, member_id, is_public)
            VALUES (?, ?, ?, ?)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, model.getTitle());
            ps.setString(2, model.getDescription());
            ps.setInt(3, model.getOwner_id());
            ps.setBoolean(4, model.isPublic());
            return ps;
        }, keyHolder);

        model.setId(keyHolder.getKey().intValue());

        return rows;
    }

    public int update(Wishlist model) {
        String sql = """
                            UPDATE wishlist
                SET title = ?, description = ?, is_public = ?
                WHERE id = ?
                """;
        return jdbc.update(sql,
                model.getTitle(),
                model.getDescription(),
                model.isPublic(),
                model.getId());
    }
    public List<Item> fetchItemsInWishlistByTitle(int id, String keyword) {
        String sql = """
            SELECT item.id,
                   item.title,
                   item.description,
                   wishlist_item.note,
                   wishlist_item.url,
                   wishlist_item.price
            FROM item
            JOIN wishlist_item
              ON item.id = wishlist_item.item_id
            WHERE item.title LIKE ?
              AND wishlist_item.wishlist_id = ?
            """;
        return jdbc.query(sql, itemRepository.getItemRowMapper(), "%"+keyword+"%", id);
    }

    public int addItemToWishlist(int wishlistId, int itemId, String note, String url, long price){
        String sql = """
                INSERT INTO wishlist_item (wishlist_id, item_id, note, url, price)
                VALUES (?, ?, ?, ?, ?)
                """;
        return jdbc.update(sql, wishlistId, itemId, note, url, price);
    }

    public int removeItemFromWishlist(int wishlistId, int itemId){
        String sql = """
                DELETE FROM wishlist_item
                WHERE wishlist_id = ? AND item_id = ?
                """;
        return jdbc.update(sql, wishlistId, itemId);
    }

    public int updateWishlistItem(int wishlistId, int itemId, String note, String url, long price){
        String sql = """
                UPDATE wishlist_item
                SET note = ?,url = ?, price = ?
                WHERE wishlist_id = ? AND item_id = ?
                """;
        return jdbc.update(sql, note, url, price, wishlistId, itemId);
    }

    public int insertSavedWishlist(Wishlist wishlist, Member member){
        String sql = """
                INSERT INTO saved_wishlist (wishlist_id, member_id, owner_id)
                VALUES (?, ?, ?)
                """;
        return jdbc.update(sql, wishlist.getId(), member.getId(), wishlist.getOwner_id());
    }

    public List<Wishlist> fetchSavedWishlists(Member member){
        String sql = """
                SELECT wishlist.id, wishlist.member_id, wishlist.title, wishlist.description, wishlist.is_public
                FROM saved_wishlist
                JOIN wishlist
                ON wishlist.id = saved_wishlist.wishlist_id
                WHERE saved_wishlist.member_id = ?
                """;

        return jdbc.query(sql, wishlistRowMapper, member.getId());
    }

    public int removeSavedWishlist(Wishlist wishlist, Member member){
        String sql = """
                DELETE FROM saved_wishlist
                WHERE wishlist_id = ? AND member_id = ?
                """;
        return jdbc.update(sql, wishlist.getId(), member.getId());
    }

}


