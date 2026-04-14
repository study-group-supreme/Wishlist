package wishlist.service;

import org.springframework.stereotype.Service;
import wishlist.model.Item;
import wishlist.model.Wishlist;
import wishlist.repository.WishlistRepository;

import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist getById(int id) {
        return wishlistRepository.findWishlistById(id);
    }

    public Wishlist getByTitle(String title) {
        return wishlistRepository.findWishlistByTitle(title);
    }

    public Wishlist getByOwnerId(int owner_Id) {
        return wishlistRepository.findWishlistByOwnerId(owner_Id);
    }

    public List<Item> getItemsFromWishlistByWishlistId(int id) {
        return wishlistRepository.fetchItemsById(id);
    }

    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.getAllWishlists();
    }

    public int createNewWishlist(Wishlist model) {
        return wishlistRepository.insertWishlist(model);
    }
}
