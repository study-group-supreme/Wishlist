package wishlist.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wishlist.exception.BadRequestException;
import wishlist.exception.NotFoundException;
import wishlist.model.Item;
import wishlist.model.Member;
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

    @GetMapping()
    public String showAllWishlists(Model model, HttpSession session) {
        int memberId = (Integer) session.getAttribute("memberId");

        model.addAttribute("wishlists",  wishlistService.getByOwnerId(memberId));
        model.addAttribute("isOwner", true);
        return "wishlist/list";
    }

    @GetMapping("/{wishlistId}")
    public String showOneWishlist(@PathVariable int wishlistId, Model model, HttpSession session) {

        Wishlist wishlist = wishlistService.getById(wishlistId);
        List<Item> items = wishlistService.getItemsFromWishlistByWishlistId(wishlistId);

        int ownerId = wishlist.getOwner_id();
        int loggedInId = (Integer) session.getAttribute("memberId");
        model.addAttribute("isOwner", ownerId == loggedInId);
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

    @GetMapping("/{wishlistId}/items/new")
    public String showAddItemForm(@PathVariable int wishlistId, Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("wishlistId", wishlistId);
        return "wishlist/add-item";
    }

    @PostMapping("/{wishlistId}/items")
    public String addItemToWishlist(@PathVariable int wishlistId, @ModelAttribute Item item, HttpSession session) {
        int memberId = (Integer) session.getAttribute("memberId");

        Wishlist wishlist = wishlistService.getById(wishlistId);

        if (wishlist.getOwner_id() != memberId) {
            return "redirect:/wishlist";
        }

        wishlistService.addNewItemToWishlist(wishlistId, item);

        return "redirect:/wishlist/" + wishlistId;
    }

    @PostMapping("/{wishlistId}/items/{itemId}/delete")
    public String deleteItem(
            @PathVariable int wishlistId,
            @PathVariable int itemId,
            HttpSession session
    ) {
        int memberId = (Integer) session.getAttribute("memberId");

        Wishlist wishlist = wishlistService.getById(wishlistId);
        if (wishlist.getOwner_id() != memberId){
            return "redirect:/wishlist";
        }

        wishlistService.removeItemFromWishlist(wishlistId, itemId);

        return "redirect:/wishlist/" + wishlistId;
    }

    @GetMapping("/{wishlistId}/items/{itemId}/edit")
    public String showEditItemForm(
            @PathVariable int wishlistId,
            @PathVariable int itemId,
            Model model,
            HttpSession session
    ) {
        int memberId = (Integer) session.getAttribute("memberId");
        Wishlist wishlist = wishlistService.getById(wishlistId);

        if (wishlist.getOwner_id() != memberId){
            return "redirect:/wislist";
        }

        Item item = itemService.getItemById(itemId);

        model.addAttribute("item", item);
        model.addAttribute("wishlistId", wishlistId);

        return "wishlist/edit-item";
    }

    @PostMapping("/{wishlistId}/items/{itemId}/edit")
    public String updateItem(
            @PathVariable int wishlistId,
            @PathVariable int itemId,
            @ModelAttribute Item item,
            HttpSession session
    ){
        int memberId = (Integer) session.getAttribute("memberId");
        Wishlist wishlist = wishlistService.getById(wishlistId);

        if (wishlist.getOwner_id() != memberId){
            return "redirect:/wishlist";
        }

        item.setId(itemId);
        wishlistService.updateItemInWishlist(wishlistId, item);
        return "redirect:/wishlist/"+ wishlistId;
    }

    @GetMapping("/search")
    public String showWishlistsForUsername(@RequestParam String username, Model model, HttpSession session){
        try {
            Member owner = memberService.getByUsername(username);

            int loggedInId = (Integer) session.getAttribute("memberId");

            List<Wishlist> publicListsForUsername = wishlistService.getWishlistByOwnerUsername(username);
            model.addAttribute("owner", owner);
            model.addAttribute("wishlists", publicListsForUsername);
            model.addAttribute("isOwner", owner.getId() == loggedInId);
            return "wishlist/list";
        } catch (NotFoundException e){
            model.addAttribute("nothingToShow", true);
            model.addAttribute("wishlists", List.of());
            model.addAttribute("owner", null);
            model.addAttribute("isOwner", false);
            return "wishlist/list";
        } catch(BadRequestException e){
            model.addAttribute("emptySearch", true);
            model.addAttribute("wishlists", List.of());
            model.addAttribute("owner", null);
            model.addAttribute("isOwner", false);
            return "wishlist/list";
        }
    }

    @PostMapping ("/follow")
    public String followWishlist(@ModelAttribute Wishlist wishlist, HttpSession session){
        Member member = memberService.getById((Integer) session.getAttribute("memberId"));
        wishlistService.followWishlist(wishlist, member);
        return "redirect:/wishlist/followed";
    }

    @GetMapping("/followed")
    public String showFollowedWishlists(Model model, HttpSession session){
        Member member = memberService.getById((Integer) session.getAttribute("memberId"));
        List<Wishlist> followedLists = wishlistService.getFollowedWishlists(member);
        model.addAttribute("followedLists", followedLists);
        return "wishlist/followed";
    }

    @PostMapping("/{wishlistId}/unfollow")
    public String unfollowWishlist(@PathVariable int wishlistId, HttpSession session){
        Member member = memberService.getById((Integer) session.getAttribute("memberId"));
        Wishlist wishlist = wishlistService.getById(wishlistId);
        wishlistService.unfollowWishlist(wishlist, member);
        return "redirect:/wishlist/followed";
    }
}
