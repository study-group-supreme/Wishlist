package wishlist.integration;

import org.springframework.jdbc.core.JdbcTemplate;
import wishlist.model.Wishlist;
import wishlist.model.Item;
import wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findById_returnsCorrectWishlist() {
        Wishlist model = wishlistRepository.findWishlistById(1);
        assertThat(model.getTitle()).isEqualTo("August List of Hopes and Dreams");
    }

    @Test
    void findByTitle_returnsCorrectWishlistId() {
        Wishlist model = wishlistRepository.findWishlistByTitle("August List of Hopes and Dreams");
        assertThat(model.getId()).isEqualTo(1);
    }

    @Test
    void findByOwnerId_returnsCorrectWishlist() {
       List<Wishlist> wishlists = wishlistRepository.findWishlistByOwnerId(2);
        assertThat(wishlists.get(0).getTitle()).isEqualTo("Andreas Filthy Dirty Wishes");
    }

    @Test
    void fetchItemsByWishlistId_returnsCorrectItemsOnWishlist() {
        List<Item> items = wishlistRepository.fetchItemsByWishlistId(1);
        assertThat(items.size()).isEqualTo(2);
        assertThat(items.get(0).getName()).isEqualTo("Life-size Darth Vader");
        assertThat(items.get(1).getName()).isEqualTo("Spider-Man figure");
    }

  @Test
  void deleteWishlist_shouldDeleteWishlistById() {
      int rows = wishlistRepository.deleteWishlist(1);

      assertThat(rows).isEqualTo(1);
  }



    @Test
    void getAllWishlists_shouldReturnAllWishlists() {
        List<Wishlist> wishlists = wishlistRepository.getAllWishlists();
        assertThat(wishlists).isNotNull();
        assertThat(wishlists.size()).isEqualTo(2);
        assertThat(wishlists.get(0).getId()).isEqualTo(1);
        assertThat(wishlists.get(1).getId()).isEqualTo(2);
    }

    @Test
    void CreateNewWishlist_returnsNewWishlist() {
        Wishlist newWishlist = new Wishlist();
        newWishlist.setTitle("Mads ønskeliste");
        newWishlist.setDescription("Hej med dig");
        newWishlist.setPublic(true);
        newWishlist.setOwner_id(4);

        wishlistRepository.insertWishlist(newWishlist);
        Wishlist created = wishlistRepository.findWishlistByTitle("Mads ønskeliste");
        assertThat(created).isNotNull();
        assertThat(created.getTitle()).isEqualTo("Mads ønskeliste");
        assertThat(created.getDescription()).isEqualTo("Hej med dig");
        assertThat(created.isPublic()).isEqualTo(true);
        assertThat(created.getOwner_id()).isEqualTo(4);
        assertThat(created.getId()).isGreaterThan(0);

        int rows = wishlistRepository.insertWishlist(newWishlist);
        assertThat(rows).isEqualTo(1);
    }

    @Test
    void update_returnsUpdatedWishlist() {
        Wishlist model = wishlistRepository.findWishlistById(1);
        model.setTitle("New name");
        model.setDescription("Hej");
        model.setPublic(true);

        wishlistRepository.update(model);

        int rows = wishlistRepository.update(model);
        assertThat(rows).isEqualTo(1);

        Wishlist updated = wishlistRepository.findWishlistById(1);
        assertThat(updated.getTitle()).isEqualTo("New name");
        assertThat(updated.getDescription()).isEqualTo("Hej");
        assertThat(updated.isPublic()).isEqualTo(true);

    }
    @Test
    void fetchItemsByWishlistTitle_returnsAllItemsFromDBWithMatchingKeywordAndId(){
        List<Item> items = wishlistRepository.fetchItemsInWishlistByTitle(1, "size");
        assertThat(items.get(0).getName()).isEqualTo("Life-size Darth Vader");

    }

    @Test
    void updateWishlistItem_updatesExistingRow() {
        int wishlistId = 1;
        int itemId = 1;

        String newNote = "Updated note";
        String newUrl = "http://updated-url.com";
        BigDecimal newPrice = new BigDecimal("12345.00");

        int rows = wishlistRepository.updateWishlistItem(wishlistId, itemId, newNote, newUrl, newPrice);

        assertThat(rows).isEqualTo(1);

        String sql = """
        SELECT note, url, price
        FROM wishlist_item
        WHERE wishlist_id = ? AND item_id = ?
        """;

        var result = jdbcTemplate.queryForMap(sql, wishlistId, itemId);

        assertThat(result.get("note")).isEqualTo(newNote);
        assertThat(result.get("url")).isEqualTo(newUrl);
        assertThat(((BigDecimal) result.get("price")).compareTo(newPrice)).isEqualTo(0);
    }

    @Test
    void removeItemFromWishlist_deletesRowCorrectly() {
        int wishlistId = 1;
        int itemId = 2;

        int rows = wishlistRepository.removeItemFromWishlist(wishlistId, itemId);

        assertThat(rows).isEqualTo(1);

        String sql = """
        SELECT COUNT(*) FROM wishlist_item
        WHERE wishlist_id = ? AND item_id = ?
        """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, wishlistId, itemId);

        assertThat(count).isEqualTo(0);
    }

    @Test
    void addItemToWishlist_insertsRowCorrectly() {
        jdbcTemplate.update("INSERT INTO item (title, description) VALUES ('JUnit Item', 'Test Desc')");
        int newItemId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM item", Integer.class);

        int wishlistId = 1;

        String note = "JUnit note";
        String url = "http://example.com";
        BigDecimal price = new BigDecimal(999);

        int rows = wishlistRepository.addItemToWishlist(wishlistId, newItemId, note, url, price);

        assertThat(rows).isEqualTo(1);

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM wishlist_item WHERE wishlist_id = ? AND item_id = ?",
                Integer.class,
                wishlistId, newItemId
        );

        assertThat(count).isEqualTo(1);
    }
}