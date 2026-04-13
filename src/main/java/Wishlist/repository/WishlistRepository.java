package Wishlist.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbc;

    public WishlistRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    public void deleteWishlist(int wishlistId) {
        String sql = "DELETE FROM wishlist where wishlist_id = ?";
        jdbc.update(sql, wishlistId);
    }

}
