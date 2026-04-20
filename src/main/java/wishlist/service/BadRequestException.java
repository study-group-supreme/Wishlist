package wishlist.service;

public class BadRequestException extends RuntimeException {
    // TODO: move to wishlist/exception
    // This is for WishlistService error handling
    // TODO: Can we use a globalexceptionhandling????
    public BadRequestException(String message) {
        super(message);
    }
}
