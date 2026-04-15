package wishlist;

import wishlist.model.Item;
import wishlist.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    void findById_returnsCorrectItem(){
        Item item = itemRepository.findItemById(1);

        assertThat(item.getName()).isEqualTo("Life-size Darth Vader");
    }

    @Test
    void insertItem_createsItemAndInsertsItIntoDB(){
        Item item = new Item();
        item.setName("Dark Souls Figurine");
        item.setDescription("A cool figurine August has");

        int row = itemRepository.insertItem(item);
        assertThat(row).isEqualTo(1);


        Item foundItem = itemRepository.findItemById(3);
        assertThat(foundItem.getName()).isEqualTo("Dark Souls Figurine");

        assertThat(itemRepository.getAllItems().size()).isEqualTo(3);
    }

    @Test
    void getAllItems_retrievesAllItemsInDBAsAList(){
        List<Item> allItems = itemRepository.getAllItems();
        assertThat(allItems.size()).isEqualTo(2);
        assertThat(allItems.get(0).getName()).isEqualTo("Life-size Darth Vader");
    }

}
