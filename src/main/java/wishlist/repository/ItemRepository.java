package wishlist.repository;

import wishlist.model.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ItemRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Item> itemRowMapper = (rs, rowNum) -> {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("title"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getBigDecimal("price"));
        item.setUrl(rs.getString("url"));
        item.setNote(rs.getString("note"));
        return item;
    };


    public ItemRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public RowMapper<Item> getItemRowMapper() {
        return itemRowMapper;
    }

    public Item findItemById(int itemId){
        String sql = """
            SELECT item.id, item.title, item.description, wishlist_item.note, wishlist_item.url, wishlist_item.price
            FROM item
            LEFT JOIN wishlist_item ON wishlist_item.item_id = id
            WHERE id = ?""";
        return jdbc.queryForObject(sql, itemRowMapper, itemId);
    }

    public List<Item> getAllItems(){
        String sql = """
            SELECT item.id, item.title, item.description, wishlist_item.note, wishlist_item.url, wishlist_item.price
            FROM item
            LEFT JOIN wishlist_item ON wishlist_item.item_id = id
            ORDER BY id""";
        return jdbc.query(sql, itemRowMapper);
    }

    public int insertItem(Item item){
        String sql = """
                INSERT INTO item (title, description)
                VALUES(?,?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            return ps;
        },keyHolder);
        item.setId(keyHolder.getKey().intValue());
        return rows;
    }

    public int updateItem(Item item){
        String sql = """
                UPDATE item
                SET title = ?, description = ?
                WHERE id = ?
                """;

        return jdbc.update(
                sql,
                item.getName(),
                item.getDescription(),
                item.getId()
                );
    }



    public int deleteItemById(int id){
        String sql = "DELETE FROM item WHERE id = ?";
        return jdbc.update(sql, id);
    }


}
