package wishlist.service;

public class NotFoundException extends RuntimeException {
    // TODO: move to wishlist/exceptoion/
    // This is for WishlistService errorhandling
    public NotFoundException(String message) {
        super(message);
    }
}
