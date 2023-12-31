package dao;

import model.ItemDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ItemDAOImpl implements ItemDAO {

    //collection which will hold the data read from the file at the start of the application (and returned to file before program shuts down)
    List<ItemDTO> itemsFileDataStore = new ArrayList<ItemDTO>();

    //read from File in the constructor
    public ItemDAOImpl() throws FileNotFoundException, IOException { //propagate the exception

        //1. create a File Object
        File myFile = new File("FileDB.txt");
        myFile.createNewFile();

        //2.create a FileReader object - will throw exception
        FileReader fr = new FileReader(myFile);

        //3. create a BufferReader object (because FileReader only reads char by char) - will also throw exception
        BufferedReader br = new BufferedReader(fr);

        //4. store each line of the BufferReader
        // take the tokens one by one and put them in var so that we can create a ItemDTO object, to be then added to the ItemsFileDB collection
        String line = null;
        while (true) {
            if (!((line = br.readLine()) != null)) break;

            StringTokenizer stringToken = new StringTokenizer(line, "=,");

            //store each token (a string) in attributes relevant to form a ItemDTO
            stringToken.nextToken();
            String id = stringToken.nextToken();
            int itemID = Integer.parseInt(id); //parse to int

            stringToken.nextToken();
            String itemName = stringToken.nextToken();

            stringToken.nextToken();
            String cost = stringToken.nextToken();
            Integer itemCostInt = Integer.parseInt(cost);
            //  BigDecimal itemCostBD= BigDecimal.valueOf(itemCostInt);

            stringToken.nextToken();
            String stock = stringToken.nextToken();
            int itemStock = Integer.parseInt(stock);

            //now, put together the ItemDTO
            ItemDTO item = new ItemDTO(itemID, itemName, itemCostInt, itemStock);

            //add the object to the Collection - itemsCollectionFileDB
            itemsFileDataStore.add(item);

        }
    }

    @Override
    public List<ItemDTO> displayAllItems() {
        return itemsFileDataStore;
    }

    @Override
    public ItemDTO vendItem(ItemDTO vendItem) {

        // loop through the array list and look for the DVD object with corresponding ID
        for (int i = 0; i < itemsFileDataStore.size(); i++) {
            if (itemsFileDataStore.get(i).getItemId() == vendItem.getItemId()) {
                //once found, store the object at that index
                vendItem = itemsFileDataStore.get(i);
            }
        }
        return vendItem; //return object
    }


    @Override
    public boolean writeToFile() throws IOException { //propagate exception

            //create a file object
            File myFile = new File("FileDB.txt");

            //stream - a FileWriter object
            FileWriter fw = new FileWriter(myFile); //generates exception

            //traverse through the itemsFileDataStore collection and write the String representation of ItemDTO to the file
            for (ItemDTO eachItem : itemsFileDataStore) {
                fw.write((eachItem.toString() + "\n").toCharArray());
                fw.flush(); //make sure everything is written (flushed) to the file
            }

        return true;

    }
}
