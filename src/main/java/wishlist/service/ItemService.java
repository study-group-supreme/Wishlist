package wishlist.service;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wishlist.exception.BadRequestException;
import wishlist.exception.DatabaseOperationException;
import wishlist.exception.NotFoundException;
import wishlist.model.Item;
import wishlist.repository.ItemRepository;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item getItemById(int id) {
        if (id <= 0) {
            throw new BadRequestException("Invalid item id");
        }


        try {
            return itemRepository.findItemById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Nothing to show for item with id: " + id);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Database error while loading item", e);
        }
    }

    public List<Item> getAllItems() {
        return itemRepository.getAllItems();
    }

    @Transactional
    public Item createItem(Item item) {
        // TODO: is this not checked for errors in WishlistService ... this method is not even used!?
//        if (item.getName() == null || item.getName().isBlank()) {
//            throw new BadRequestException("Item name cannot be empty");
//        }

        try {
            itemRepository.insertItem(item);
            return itemRepository.findItemById(item.getId());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Item creation failed", e);
        }
    }
}