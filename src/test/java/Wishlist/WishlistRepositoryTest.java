package Wishlist;

import Wishlist.model.WishlistModel;
import Wishlist.model.Item;
import Wishlist.repository.WishlistRepository;
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
    WishlistModel model = wishlistRepository.findWishlistById(1);
    assertThat(model.getTitle()).isEqualTo("August List of Hopes and Dreams");
    }
}