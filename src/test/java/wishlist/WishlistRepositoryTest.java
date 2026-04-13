package wishlist;

import wishlist.model.Wishlist;
import wishlist.model.Item;
import wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
void findById_returnsCorrectWishlist(){
    Wishlist model = wishlistRepository.findWishlistById(1);
    assertThat(model.getTitle()).isEqualTo("August List of Hopes and Dreams");
    }
    @Test
    void findByTitle_returnsCorrectWishlistId(){
        Wishlist model = wishlistRepository.findWishlistByTitle("August List of Hopes and Dreams");
        assertThat(model.getId()).isEqualTo(1);
    }
    @Test
    void findByOwnerId_returnsCorrectWishlist(){
        Wishlist model = wishlistRepository.findWishlistByOwnerId(2);
        assertThat(model.getTitle()).isEqualTo("Andreas Filthy Dirty Wishes");
    }
    @Test
    void fetchItemsByWishlistId_returnsCorrectItemsOnWishlist(){
        List<Item> items = wishlistRepository.fetchItemsById(1);
        assertThat(items.size()).isEqualTo(2);
        assertThat(items.get(0).getName()).isEqualTo("Life-size Darth Vader");
        assertThat(items.get(1).getName()).isEqualTo("Spider-Man figure");
    }
}