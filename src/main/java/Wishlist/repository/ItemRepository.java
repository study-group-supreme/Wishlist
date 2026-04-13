package Wishlist.repository;

import Wishlist.model.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

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


    public Item createItem(Item item){
        String sql = """
                INSERT INTO item (title, description, url, price)
                VALUES(?,?,?,?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getUrl());
            ps.setLong(4, item.getPrice());
            return ps;
        }, keyHolder);

        int id = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

        if(id != -1){
            return findItemById(id);
        } else {
            throw new RuntimeException("Item creation failed");
        }
    }

    public void deleteItem(int id){

    }
}
