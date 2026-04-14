package wishlist;

import org.springframework.ui.Model;
import wishlist.model.Member;
import wishlist.model.Wishlist;
import wishlist.model.Item;
import wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

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
        List<Item> items = wishlistRepository.fetchItemsById(1);
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
        List<Item> items = wishlistRepository.fetchItemsInWishlistByTitel(1, "Life-size Darth Vader");
        assertThat(items.get(0).getName()).isEqualTo("Life-size Darth Vader");

    }
}