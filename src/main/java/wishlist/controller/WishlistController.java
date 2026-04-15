package wishlist.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wishlist.model.Wishlist;
import wishlist.service.ItemService;
import wishlist.service.WishlistService;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final ItemService itemService;
    private final WishlistService wishlistService;

    public WishlistController(ItemService itemService, WishlistService wishlistService){
        this.itemService = itemService;
        this.wishlistService = wishlistService;
    }

    // show all wishlists for logged in member GET
    @GetMapping()
    public String showAllWishlists(Model model, HttpSession session) {
        int memberId = (Integer) session.getAttribute("memberId");

        model.addAttribute("wishlists",  wishlistService.getByOwnerId(memberId));
        return "list";
    }

    // show wishlist by wishlist-id
}
