package Wishlist.repository;

import Wishlist.model.WishlistModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbc;

    public WishlistRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    private final RowMapper<WishlistModel> wishlistRowMapper = (rs, rowNum) -> {
        WishlistModel w = new WishlistModel();
        w.setId(rs.getInt("id"));
        w.setTitle(rs.getString("title"));
        w.setDescription(rs.getString("description"));
        w.setPublic(rs.getBoolean("is_public"));
        w.setOwner_id(rs.getInt("member_id"));
        w.setItems(null);
        return w;
    };

    public WishlistModel findWishlistById(int id){
        String sql = "SELECT * FROM Wishlist WHERE id = ?";
        return jdbc.queryForObject(sql, wishlistRowMapper, id);
    }
    public WishlistModel findWishlistByTitle(String title){
        String sql = "SELECT * FROM Wishlist WHERE title = ?";
        return jdbc.queryForObject(sql, wishlistRowMapper, title);
    }
    public WishlistModel findWishlistByOwnerId(int Owner_id){
        String sql = "SELECT * FROM Wishlist WHERE member_id = ?";
        return jdbc.queryForObject(sql, wishlistRowMapper, Owner_id);
    }
}
