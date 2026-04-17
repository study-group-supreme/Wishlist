package wishlist.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wishlist.model.Item;
import wishlist.model.Wishlist;
import wishlist.service.ItemService;
import wishlist.service.MemberService;
import wishlist.service.WishlistService;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final ItemService itemService;
    private final WishlistService wishlistService;
    private final MemberService memberService;

    public WishlistController(ItemService itemService, WishlistService wishlistService, MemberService memberService){
        this.itemService = itemService;
        this.wishlistService = wishlistService;
        this.memberService = memberService;
    }

    // show all wishlists for logged in member GET
    @GetMapping()
    public String showAllWishlists(Model model, HttpSession session) {
        int memberId = (Integer) session.getAttribute("memberId");

        model.addAttribute("wishlists",  wishlistService.getByOwnerId(memberId));
        return "list";
    }

    // show wishlist by wishlist-id
    @GetMapping("/{id}")
    public String showOneWishlist(@PathVariable int id, Model model, HttpSession session) {

        int memberId = (Integer) session.getAttribute("memberId");

        Wishlist wishlist = wishlistService.getById(id);
        List<Item> items = wishlistService.getItemsFromWishlistByWishlistId(id);

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("items", items);

        return "wishlist/details";
    }
}
