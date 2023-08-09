package service;

import dao.ItemDAO;
import dao.ItemDAOImpl;
import model.ItemDTO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ItemServiceImpl implements ItemService {

    //for logic relating to the DB
    ItemDAO itemDao = null;

    public ItemServiceImpl() throws FileNotFoundException,IOException,IOException {

        itemDao = new ItemDAOImpl();
    }


    @Override
    public List<ItemDTO> displayAllItems() {

        return itemDao.displayAllItems();
    }

    @Override
    public List<ItemDTO> displayFilteredCollectionOfItems() {

        // create a copy of the Items collection stored in DAO, which is accessed here through the displayAllItems,
        // to be used for filtering stock=0 condition

        List<ItemDTO> filteredItemCollection = new ArrayList<ItemDTO>(displayAllItems());

        //loop through collection (lambda - RemoveIf could also be applied)
        for (int i = 0; i < filteredItemCollection.size(); i++) {
            //if the itemStock is 0 , remove the item from the service layer collection
            if (filteredItemCollection.get(i).getItemStock() == 0) {
                filteredItemCollection.remove(i);
            }
        }
        return filteredItemCollection; //return collection
    }


    @Override
    public ItemDTO searchItemById(int itemId) {

        ItemDTO retrieveItem = null;

        for (int i = 0; i < itemDao.displayAllItems().size(); i++) {
            if (itemDao.displayAllItems().get(i).getItemId() == itemId) {
                retrieveItem = itemDao.displayAllItems().get(i);
            }
        }

        return retrieveItem;
    }

    @Override
    public ItemDTO vendItem(ItemDTO vendItem) {

        return itemDao.vendItem(vendItem);
    }

    @Override
    public boolean writeToFile() throws IOException{ //propagate exception
        return itemDao.writeToFile();
    }

}
