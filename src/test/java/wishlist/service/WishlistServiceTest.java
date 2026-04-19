package wishlist.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import wishlist.model.Item;
import wishlist.model.Wishlist;
import wishlist.repository.WishlistRepository;

import java.util.List;
import static org.mockito.Mockito.*;
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

    @Test
    void getByTitle_throwsBadRequest_whenTitleEmpty() {
        assertThrows(BadRequestException.class, () -> wishlistService.getByTitle(""));
    }

    @Test
    void getByOwnerId_throwsBadRequest_whenOwnerInvalid() {
        // Owner ID must be positive, 0 means broken session or bug
        assertThrows(BadRequestException.class, () -> wishlistService.getByOwnerId(0));
    }

    @Test
    void getByOwnerId_returnsWishlists_whenValid(){
        Wishlist w = new Wishlist();
        w.setId(1);

        when(wishlistRepository.findWishlistByOwnerId(5))
                .thenReturn(List.of(w));

        List<Wishlist> result = wishlistService.getByOwnerId(5);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    @Test
    void getItemsFromWishlistByWishlistId_callsGetByIdFirst() {
        // getById must be called first to ensure the wishlist exists
        Wishlist w = new Wishlist();
        w.setId(1);

        when(wishlistRepository.findWishlistById(1)).thenReturn(w);
        when(wishlistRepository.fetchItemsByWishlistId(1))
                .thenReturn(List.of(new Item()));

        List<Item> result = wishlistService.getItemsFromWishlistByWishlistId(1);

        assertEquals(1, result.size());
        verify(wishlistRepository).findWishlistById(1); // check so it exists
        verify(wishlistRepository).fetchItemsByWishlistId(1); // actual fetch
    }

    @Test
    void createNewWishlist_throwsBadRequest_whenTitleEmpty() {
        Wishlist w = new Wishlist();
        w.setTitle("");
        w.setOwner_id(1);

        assertThrows(BadRequestException.class, () -> wishlistService.createNewWishlist(w));
    }

    @Test
    void createNewWishlist_throwsBadRequest_whenOwnerInvalid() {
        // owner ID must be positive
        Wishlist w = new Wishlist();
        w.setTitle("Title");
        w.setOwner_id(0);

        assertThrows(BadRequestException.class, () -> wishlistService.createNewWishlist(w));
    }

}
