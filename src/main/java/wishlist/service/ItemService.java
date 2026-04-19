package wishlist.service;

import org.springframework.stereotype.Service;
import wishlist.model.Item;
import wishlist.repository.ItemRepository;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * TODO: Add meaningful error handling
     * - Validate that id > 0
     * - Catch repository exceptions and convert to NotFoundException
     * - Reasoning: "queryForObject throws EmptyResultDataAccessException when no item exists"
     */
    public Item getItemById(int id){
        return itemRepository.findItemById(id);
    }

    /**
     * TODO: Consider whether empty lists should be allowed or if filtering is needed.
     * - Probably no validation needed here.
     */
    public List<Item> getAllItems(){
        return itemRepository.getAllItems();
    }

    /**
     * TODO: Add validation before inserting:
     * - name cannot be null/blank
     * - description length <= 255
     * - price >= 0 (if price is used)
     * - url length <= 500 (if url is used)
     *
     * TODO: Wrap repository.insertItem in try/catch
     * - Convert SQL exceptions into BadRequestException
     *
     * TODO: After insert, fetch the item again using getItemById
     */
    public Item createItem(Item item){
        itemRepository.insertItem(item);
        return itemRepository.findItemById(item.getId());
    }
}
