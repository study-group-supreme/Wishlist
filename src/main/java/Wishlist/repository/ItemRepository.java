package Wishlist.repository;

import Wishlist.model.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Item> itemRowMapper = (rs, rowNum) -> {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("title"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getLong("price"));
        item.setUrl(rs.getString("url"));
        return item;
    };

    public ItemRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Item findItemById(int itemId){
        String sql = "SELECT * FROM item WHERE id = ?";
        return jdbc.queryForObject(sql, itemRowMapper, itemId);
    }

    public RowMapper<Item> getItemRowMapper() {
        return itemRowMapper;
    }
}
