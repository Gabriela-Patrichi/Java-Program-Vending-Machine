package view;

import model.ItemDTO;
import service.ItemService;
import service.ItemServiceImpl;
import utility.Change;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ItemView {

    //display all items, filtered

    //here, we deal with the propagated exceptions
    ItemService myItemService;

    {
        try {
            myItemService = new ItemServiceImpl();
        } catch (FileNotFoundException e){
            System.out.println("Sorry, File Not Found! Please try again.");
        } catch (IOException e) {
            System.out.println("Sorry, there was an issue processing that. Please try again!);");
        }
    }

    public void displayAllItemsFiltered() {

        //create a collection to store the retrieved collection
        List<ItemDTO> itemsAvailable = null;

        System.out.println("************************************************************");
        System.out.println("Items available today:");

        //call displayFilteredCollectionOfItems() from Service
        itemsAvailable = myItemService.displayFilteredCollectionOfItems();

        // list items available (with stock != 0)
        for (ItemDTO item : itemsAvailable
        ) {
            System.out.println(item);
        }
        System.out.println("************************************************************");

    }


    //2. run menu

    public void showMenu() {

        Scanner input = new Scanner(System.in);

        char option = 'n'; //initialize option to N for the menu loop

        do {

            System.out.println("1. Continue with purchase...");
            System.out.println("2. Exit");

            int userChoice = input.nextInt();

            switch (userChoice) {
                case 1:
                    System.out.println("Insert money: ");
                    int insertedAmount = input.nextInt();
                    System.out.println("Enter the item's code you wish to purchase:");
                    int itemCode = input.nextInt();

                    // call the searchItemById() to retrieve the item so that we can check against the money criteria
                    ItemDTO itemToBeVended = myItemService.searchItemById(itemCode);

                    //check if item code input is valid (item exists)
                    if (itemToBeVended == null) {
                        System.out.println("Sorry.Invalid Id.");
                        break;
                    }

                    System.out.println("One " + itemToBeVended.getItemName() + " is being vended...\n");

                    // if money is not enough
                    if (itemToBeVended.getItemCost() > insertedAmount) {
                        // Exception of insufficient funds - CUSTOM EXCEPTION

                        System.out.println("Insufficient funds.");
                        System.out.println("You have inserted " + insertedAmount);
                    }


                    // if itemCost condition is met
                    else if (itemToBeVended.getItemCost() <= insertedAmount) {

                        // check for itemStock , if 0
                        // NoItemInventoryException - TO BE IMPLEMENTED
                        if (itemToBeVended.getItemStock() == 0) {
                            System.out.println("NoItemInventoryException");

                            System.out.println("Returning your amount : " + insertedAmount);
                            //  Change.calculateChange(insertedAmount); - CHANGE SHOULD ONLY BE CALLED IF THERE IS A PURCHASE
                            break;
                        }

                        // if itemStock != 0
                        else {

                            //stock of the item selected decreases by 1
                            itemToBeVended.setItemStock(itemToBeVended.getItemStock() - 1);

                            // call vendItem(), passing the itemToBeVended as parameter(with stock -1)
                            // and assign its return value to a ItemDTO vendedItem
                            ItemDTO vendedItem = myItemService.vendItem(itemToBeVended);

                            System.out.println("Done. Please collect your " + vendedItem.getItemName() +".\n");

                            // calculate the change due amount
                            int dueAmount = insertedAmount - vendedItem.getItemCost();
                            System.out.println("If there is any change, you should collect the following:");

                            //call calculateChange() from Change class, passing dueAmount as parameter
                            Change.calculateChange(dueAmount);
                            System.out.println("*************************************************");
                            System.out.println("**Thank you for your purchase. Have a nice day!**");
                            System.out.println("**************************************************");
                            break;
                        }

/*
                        //call calculateChange() from Change class, passing dueAmount as parameter
                        //store change (to be returned from calculateChange()) in a collection and display to user
                       // Collection<String, Integer> displayChange =

                        Set<Change.Coins> keys = Change.calculateChange(dueAmount);
                        // print the keys to the screen
                        for (Change.Coins k : keys) {
                            System.out.println( k + keys.get(k));
                        }
*/
                    }

                case 2:
                    System.out.println("Thank you for using the vending machine.");
                    //write to file
                    try {
                        myItemService.writeToFile();
                    } catch (IOException e) {
                        System.out.println("Sorry, something went wrong when saving data to file. \n Please try again.");
                    }
                    System.out.println("Items data store collection updated successfully.");
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Select either 1 or 2 ");
                    break;
            }
        } while (option == 'n'); // user needs to choose Exit to exit the program
    }
}
