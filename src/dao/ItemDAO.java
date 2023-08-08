package dao;

import model.ItemDTO;

import java.util.List;

public interface ItemDAO {

    //no longer needed - because the collection will only be filtered at the Service level (not needing access to the DB)
    List<ItemDTO> displayAllItems();
    ItemDTO vendItem(ItemDTO itemId); // method to vend an item

    boolean writeToFile();

}
