package wishlist.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        return "/wishlist/list";
    }

    // show wishlist by wishlist-id
    @GetMapping("/{wishlistId}")
    public String showOneWishlist(@PathVariable int wishlistId, Model model) {

        Wishlist wishlist = wishlistService.getById(wishlistId);
        List<Item> items = wishlistService.getItemsFromWishlistByWishlistId(wishlistId);

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("items", items);

        return "wishlist/details";
    }

    @GetMapping("/new")
    public String showCreateWishlistForm(Model model){
        model.addAttribute("wishlist", new Wishlist());
        return "wishlist/create-wishlist";
    }

    @PostMapping("/save")
    public String saveNewWishlist(@ModelAttribute Wishlist wishlist, HttpSession session){
        int memberId = (Integer) session.getAttribute("memberId");

        wishlist.setOwner_id(memberId);

        wishlistService.createNewWishlist(wishlist);

        return "redirect:/wishlist";
    }

    @PostMapping("/{wishlistId}/delete")
    public String deleteWishlist(@PathVariable int wishlistId, HttpSession session){
        int memberId = (Integer) session.getAttribute("memberId");

        Wishlist wishlist = wishlistService.getById(wishlistId);
        if (wishlist == null || wishlist.getOwner_id() != memberId){
            return "redirect:/wishlist";
        }

        wishlistService.deleteWishlistById(wishlistId);
        return "redirect:/wishlist";
    }

    @GetMapping("/{wishlistId}/edit")
    public String showEditWishlistForm(@PathVariable int wishlistId, Model model, HttpSession session){
        int memberId = (Integer) session.getAttribute("memberId");

        Wishlist wishlist = wishlistService.getById(wishlistId);

        if (wishlist.getOwner_id() != memberId){
            return "redirect:/wishlist";
        }

        model.addAttribute("wishlist", wishlist);
        return "wishlist/edit-wishlist";
    }

    @PostMapping("/{wishlistId}/update")
    public String updateWishlist(@PathVariable int wishlistId, @ModelAttribute Wishlist wishlist, HttpSession session){
        int memberId = (Integer) session.getAttribute("memberId");

        Wishlist existingWishlist = wishlistService.getById(wishlistId);
        if (existingWishlist.getOwner_id() != memberId){
            return "redirect:/wishlist";
        }

        wishlist.setId(wishlistId);
        wishlist.setOwner_id(memberId);

        wishlistService.updateWishlist(wishlist);

        return "redirect:/wishlist/" + wishlistId;
    }
}
