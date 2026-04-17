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
        try {
            return wishlistRepository.findWishlistById(id);
        } catch (Exception e){
            throw new NotFoundException("The wishlist could not load.");
        }
    }

    public Wishlist getByTitle(String title) {
        return wishlistRepository.findWishlistByTitle(title);
    }

    public List<Wishlist> getByOwnerId(int owner_Id) {
        return wishlistRepository.findWishlistByOwnerId(owner_Id);
    }

    public List<Item> getItemsFromWishlistByWishlistId(int id) {
        return wishlistRepository.fetchItemsByWishlistId(id);
    }

    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.getAllWishlists();
    }

    public Wishlist createNewWishlist(Wishlist wishlist) {
        wishlistRepository.insertWishlist(wishlist);
        return wishlistRepository.findWishlistById(wishlist.getId());
    }

    public Wishlist deleteWishlistById(int id) {
        Wishlist deletedWishlist = wishlistRepository.findWishlistById(id);
        wishlistRepository.deleteWishlist(id);
        return deletedWishlist;
    }

    public int updateWishlist(Wishlist model) {
        return wishlistRepository.update(model);
    }
}
