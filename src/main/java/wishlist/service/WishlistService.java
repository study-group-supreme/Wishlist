package wishlist.service;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import wishlist.exception.BadRequestException;
import wishlist.exception.DatabaseOperationException;
import wishlist.exception.NotFoundException;
import wishlist.model.Item;
import wishlist.model.Member;
import wishlist.model.Wishlist;
import wishlist.repository.ItemRepository;
import wishlist.repository.MemberRepository;
import wishlist.repository.WishlistRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public WishlistService(WishlistRepository wishlistRepository, ItemRepository itemRepository, MemberRepository memberRepository) {
        this.wishlistRepository = wishlistRepository;
        this.itemRepository = itemRepository;
        this.memberRepository = memberRepository;
    }

    public Wishlist getById(int id) {
        try {
            return wishlistRepository.findWishlistById(id);
        } catch (Exception e) {
            throw new NotFoundException("The wishlist could not load.");
        }
    }

    public Wishlist getByTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new BadRequestException("Title cannot be empty");
        }

        try {
            return wishlistRepository.findWishlistByTitle(title);
        } catch (Exception e) {
            throw new NotFoundException("Could not find wishlist with title: '" + title);
        }

    }

    public List<Wishlist> getByOwnerId(int ownerId) {
        // negative or zero id must be a broken session or bug (this method is used with sessionattribute memberId in showAllWishlists)
        if (ownerId <= 0) {
            throw new BadRequestException("Invalid owner id");
        }
        return wishlistRepository.findWishlistByOwnerId(ownerId);
    }

    public List<Item> getItemsFromWishlistByWishlistId(int id) {
        getById(id); //Ensure wishlist exists
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

        if (wishlist.getTitle().length() > 100) {
            throw new BadRequestException("Wishlist title cannot exceed 100 characters");
        }

        // Validate description
        if (wishlist.getDescription() != null && wishlist.getDescription().length() > 255) {
            throw new BadRequestException("Description cannot exceed 255 characters");
        }

        // Validate owner
        if (wishlist.getOwner_id() <= 0) {
            throw new BadRequestException("Wishlist must have a valid owner");
        }

        try {
            wishlistRepository.insertWishlist(wishlist);
            return wishlistRepository.findWishlistById(wishlist.getId());
        } catch (Exception e) {
            throw new BadRequestException("Could not create wishlist: " + e.getMessage());
        }
    }

    public Wishlist deleteWishlistById(int id) {
        Wishlist deletedWishlist = getById(id);
        wishlistRepository.deleteWishlist(id);
        return deletedWishlist;
    }

    public Wishlist updateWishlist(Wishlist wishlist) {
        // Ensure it exists
        Wishlist existing = getById(wishlist.getId());

        // Validate title
        if (wishlist.getTitle() == null || wishlist.getTitle().isBlank()) {
            throw new BadRequestException("Title cannot be empty");
        }
        if (wishlist.getTitle().length() > 100) {
            throw new BadRequestException("Wishlist title cannot exceed 100 characters");
        }

        // Validate description
        if (wishlist.getDescription() != null && wishlist.getDescription().length() > 255) {
            throw new BadRequestException("Description cannot exceed 255 characters");
        }

        // Apply updates
        existing.setTitle(wishlist.getTitle());
        existing.setDescription(wishlist.getDescription());
        existing.setPublic(wishlist.isPublic());

        wishlistRepository.update(existing);
        return existing;
    }

    public Item addNewItemToWishlist(int wishlistId, Item item) {
        Wishlist wishlist = getById(wishlistId);

        itemRepository.insertItem(item);

        wishlistRepository.addItemToWishlist(
                wishlistId,
                item.getId(),
                item.getNote(),
                item.getUrl(),
                item.getPrice()
        );

        return itemRepository.findItemById(item.getId());
    }

    public Item removeItemFromWishlist(int wishlistId, int itemId){
        getById(wishlistId);
        Item removedItem = itemRepository.findItemById(itemId);
        wishlistRepository.removeItemFromWishlist(wishlistId, itemId);
        return removedItem;
    }

    public Item updateItemInWishlist(int wishlistId, Item item){
        getById(wishlistId);

        itemRepository.updateItem(item);

        wishlistRepository.updateWishlistItem(
                wishlistId,
                item.getId(),
                item.getNote(),
                item.getUrl(),
                item.getPrice()
        );

        return itemRepository.findItemById(item.getId());
    }

    //Andreas trying stuffs about searching
    public List<Wishlist> getWishlistByOwnerUsername(String username){
        try {
            Member owner = memberRepository.findByUsername(username);
            List<Wishlist> ownerList= getByOwnerId(owner.getId());
            List<Wishlist> publicList = new ArrayList<>();
            for(Wishlist wishlist : ownerList){
                if(wishlist.isPublic())
                    publicList.add(wishlist);
            }
            return publicList;

        } catch (EmptyResultDataAccessException e){
            throw new NotFoundException("No wishlists where found for this username");
        }
    }
}
