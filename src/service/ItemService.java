package service;

import model.ItemDTO;

import java.io.IOException;
import java.util.List;

public interface ItemService {

    List<ItemDTO> displayAllItems();

    ItemDTO vendItem(ItemDTO vendItem);

    boolean writeToFile() throws IOException;


    List<ItemDTO> displayFilteredCollectionOfItems();

    ItemDTO searchItemById(int itemId); // method to search for an item (to use it for determining whether the item exists
                                        //check for itemStock condition
                                        //appreciate if there are sufficient funds
                                        //and also to use it further in vending the item

}
