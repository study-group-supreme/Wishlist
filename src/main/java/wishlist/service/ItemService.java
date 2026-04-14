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
    public Item getItemById(int id){
        return itemRepository.findItemById(id);
    }
    public List<Item> getAllItems(){
        return itemRepository.getAllItems();
    }
    public Item createItem(Item item){
        itemRepository.insertItem(item);
        return itemRepository.findItemById(item.getId());
    }
}
