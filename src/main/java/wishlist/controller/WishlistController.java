package wishlist.controller;

import org.springframework.stereotype.Controller;
import wishlist.model.Wishlist;
import wishlist.service.ItemService;
import wishlist.service.WishlistService;

@Controller

public class WishlistController {

    private final ItemService itemService;
    private final WishlistService wishlistService;

    public WishlistController(ItemService itemService, WishlistService wishlistService){
        this.itemService = itemService;
        this.wishlistService = wishlistService;
    }

    // show all wishlists GET


    // show wishlist by wishlist-id
}
