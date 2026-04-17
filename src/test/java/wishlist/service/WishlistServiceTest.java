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
        when(wishlistRepository.findWishlistById(99)).thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(NotFoundException.class, () -> wishlistService.getById(99));
    }

}
