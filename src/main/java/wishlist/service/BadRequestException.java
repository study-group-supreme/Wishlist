package wishlist.service;

public class BadRequestException extends RuntimeException {
    // TODO: move to wishlist/exception
    // This is for WishlistService error handling
    public BadRequestException(String message) {
        super(message);
    }
}
