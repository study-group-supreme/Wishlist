package wishlist.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import wishlist.model.Wishlist;
import wishlist.repository.WishlistRepository;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @Test
    void getById_returnsWishlist_whenFound() {
        Wishlist wishlist = new Wishlist();
        wishlist.setId(1);

        when(wishlistRepository.findWishlistById(1)).thenReturn(wishlist);

        Wishlist result = wishlistService.getById(1);
        assertEquals(1, result.getId());
    }

    @Test
    void getById_throwsNotFound_whenMissing() {
        when(wishlistRepository.findWishlistById(99))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(NotFoundException.class,
                () -> wishlistService.getById(99));
    }

    @Test
    void getByTitle_returnsWishlist_whenFound() {
        Wishlist wishlist = new Wishlist();
        wishlist.setId(1);

        when(wishlistRepository.findWishlistByTitle("MyList"))
                .thenReturn(wishlist);

        Wishlist result = wishlistService.getByTitle("MyList");
        assertEquals(1, result.getId());
    }

    @Test
    void getByTitle_throwsNotFound_whenMissing() {
        // Simulate the real behavior of JdbcTemplate.queryForObject:
        // When no row is found, it throws EmptyResultDataAccessException.
        when(wishlistRepository.findWishlistByTitle("Unknown"))
                .thenThrow(new EmptyResultDataAccessException(1));

        // The service should catch that exception and convert it into NotFoundException.
        assertThrows(NotFoundException.class, () -> wishlistService.getByTitle("Unknown"));
    }

}
