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

        // Validate title
        if (wishlist.getTitle() == null || wishlist.getTitle().isBlank()) {
            throw new BadRequestException("Title cannot be empty");
        }

        if (wishlist.getTitle().length() > 100){
            throw new BadRequestException("Wishlist title cannot exceed 100 characters");
        }

        // Validate description
        if (wishlist.getDescription() != null && wishlist.getDescription().length() > 255){
            throw new BadRequestException("Description cannot exceed 255 characters");
        }

        // Validate owner
        if (wishlist.getOwner_id() <= 0){
            throw new BadRequestException("Wishlist must have a valid owner");
        }

        try {
            wishlistRepository.insertWishlist(wishlist);
            return wishlistRepository.findWishlistById(wishlist.getId());
        } catch (Exception e) {
            throw new BadRequestException("Could not create wishlist: "+ e.getMessage());
        }
    }

    public Wishlist deleteWishlistById(int id) {
        Wishlist deletedWishlist = wishlistRepository.findWishlistById(id);
        wishlistRepository.deleteWishlist(id);
        return deletedWishlist;
    }

    public Wishlist updateWishlist(Wishlist wishlist) {
        wishlistRepository.update(wishlist);
        return wishlistRepository.findWishlistById(wishlist.getId());
    }
}
