package Wishlist.service;

import Wishlist.model.Wishlist;
import Wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public WishlistService (WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }




    public Wishlist deleteWishlist(int wishlistId) {
        Wishlist wishlistToBeDeleted = wishlistRepository.findWishlistById(wishlistId);
        wishlistRepository.deleteWishlist(wishlistId);
        return wishlistToBeDeleted;
    }

}
