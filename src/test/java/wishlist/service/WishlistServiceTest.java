package wishlist.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import wishlist.exception.BadRequestException;
import wishlist.exception.DatabaseOperationException;
import wishlist.exception.NotFoundException;
import wishlist.model.Item;
import wishlist.model.Wishlist;
import wishlist.repository.ItemRepository;
import wishlist.repository.WishlistRepository;

import java.util.List;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private ItemRepository itemRepository;

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
    void getById_throwsDatabaseOperationException_whenDbFails() {
        when(wishlistRepository.findWishlistById(1))
                .thenThrow(new org.springframework.dao.DataAccessResourceFailureException("DB down"));

        assertThrows(DatabaseOperationException.class,
                () -> wishlistService.getById(1));
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
    void getByTitle_throwsDatabaseOperationException_whenDbFails() {
        when(wishlistRepository.findWishlistByTitle("Hello"))
                .thenThrow(new org.springframework.dao.DataAccessResourceFailureException("DB down"));

        assertThrows(DatabaseOperationException.class,
                () -> wishlistService.getByTitle("Hello"));
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
    void getByOwnerId_throwsDatabaseOperationExcetion_whenDbFails() {
        when(wishlistRepository.findWishlistByOwnerId(5))
                .thenThrow(new org.springframework.dao.DataAccessResourceFailureException("DB down"));

        assertThrows(DatabaseOperationException.class,
                () -> wishlistService.getByOwnerId(5));
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

    @Test
    void createNewWishlist_savesAndReturnsWishlist(){
        Wishlist w = new Wishlist();
        w.setTitle("My List");
        w.setOwner_id(1);

        // Simulate DB assigning ID during insert.
        doAnswer(invocation -> {
            w.setId(10);
            return null;
        }).when(wishlistRepository).insertWishlist(w);

        when(wishlistRepository.findWishlistById(10)).thenReturn(w);

        Wishlist result = wishlistService.createNewWishlist(w);

        assertEquals(10, result.getId());
        verify(wishlistRepository).insertWishlist(w);
    }

    @Test
    void deleteWishlistById_returnsDeletedWishlist() {
        Wishlist w  = new Wishlist();
        w.setId(1);

        // getById must return the wishlist before deletion
        when(wishlistRepository.findWishlistById(1)).thenReturn(w);

        Wishlist deleted = wishlistService.deleteWishlistById(1);

        verify(wishlistRepository).deleteWishlist(1);
        assertEquals(1, deleted.getId());
    }

    @Test
    void updateWishlist_throwsBadRequestWhenTitleEmpty() {
        Wishlist existing = new Wishlist();
        existing.setId(1);
        existing.setTitle("Old");

        Wishlist update = new Wishlist();
        update.setId(1);
        update.setTitle(""); // invalid

        when(wishlistRepository.findWishlistById(1)).thenReturn(existing);

        assertThrows(BadRequestException.class, () -> wishlistService.updateWishlist(update));
    }

    @Test
    void updateWishlist_updatesFieldsCorrectly() {
        // The 'existing' simulates the wishlist currently stored in the db
        // This object is the one that will be mutated by the update method
        Wishlist existing = new Wishlist();
        existing.setId(1);
        existing.setTitle("Old Title");
        existing.setDescription("Old Text");
        existing.setPublic(false);

        // The 'update' is the incoming data from the user's edit form
        // The object is not saved directly, it is used as a source of changes
        Wishlist update = new Wishlist();
        update.setId(1);
        update.setTitle("New Title");
        update.setDescription("New Text");
        update.setPublic(true);

        // inside updateWishlist the service calls getById to return the 'existing' object
        when(wishlistRepository.findWishlistById(1)).thenReturn(existing);

        Wishlist result = wishlistService.updateWishlist(update);

        // Verify repository is called with the merged object
        verify(wishlistRepository).update(existing);

        assertEquals("New Title", existing.getTitle());
        assertEquals("New Text", existing.getDescription());
        assertTrue(existing.isPublic());

        // Returned object should be the updated one
        assertEquals(existing, result);

    }

    @Test
    void addNewItemToWishlist_throwsBadRequest_whenItemNameEmpty() {
        Item item = new Item();
        item.setName("");

        when(wishlistRepository.findWishlistById(1)).thenReturn(new Wishlist());

        assertThrows(BadRequestException.class,
                () -> wishlistService.addNewItemToWishlist(1, item));
    }

    @Test
    void removeItemFromWishlist_throwsNoFound_whenItemMissing() {
        when(wishlistRepository.findWishlistById(1)).thenReturn(new Wishlist());
        when(itemRepository.findItemById(99))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(NotFoundException.class,
                () -> wishlistService.removeItemFromWishlist(1, 99));
    }

    @Test
    void updateItemInWishlist_throwsBadRequest_whenItemNameEmpty() {
        Item item = new Item();
        item.setName("");

        when(wishlistRepository.findWishlistById(1)).thenReturn(new Wishlist());

        assertThrows(BadRequestException.class,
                () -> wishlistService.updateItemInWishlist(1, item));
    }
}
