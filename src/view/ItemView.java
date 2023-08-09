package view;

import model.ItemDTO;
import service.ItemService;
import service.ItemServiceImpl;
import utility.Change;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class ItemView {


    ItemService myItemService;

    {
        try {       //here, we deal with the propagated exceptions
            myItemService = new ItemServiceImpl();
        } catch (FileNotFoundException e){
            System.out.println("Sorry, File Not Found! Please try again.");
        } catch (IOException e) {
            System.out.println("Sorry, there was an issue processing that. Please try again!);");
        }
    }

    //display all items, filtered
    public void displayAllItemsFiltered() {

        //create a collection to store the retrieved collection
        List<ItemDTO> itemsAvailable = null;

        System.out.println("************************************************************");
        System.out.println("Items available today:");

        //call displayFilteredCollectionOfItems() from Service
        itemsAvailable = myItemService.displayFilteredCollectionOfItems();

        // list items available (with stock != 0)
        for (ItemDTO item : itemsAvailable) {
            System.out.println(item);}
        System.out.println("************************************************************");
    }

    //2. run menu
    public void showMenu() {

        Scanner input = new Scanner(System.in);

        char option = 'n'; //initialize option to N for the menu loop

        do {
            int userChoice=0;

            System.out.println("1. Vend Item");
            System.out.println("2. Exit");

            //handle InputMismatchException
            try {
                userChoice = input.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Invalid entry! Numerical value is expected.\n" +"Exiting the program...");
                System.exit(1);
              //userChoice =2; //set the user choice to 2 to exit the program
            }

            //switch case based on user input (int userChoice)
            switch (userChoice) {
                case 1:

                    //variables declared and initialized due to scope
                    //handle InputMismatchException
                    int insertedAmount=0;
                    int itemCode=0;

                    try {
                        System.out.println("Insert money: ");
                        insertedAmount = input.nextInt();

                        System.out.println("Enter the item's code you wish to purchase:");
                        itemCode = input.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid entry. A numerical value is required.\n"+"Choose again:");
                        showMenu(); //send the user back at the start of the menu options
                    }

                    // call the searchItemById() to retrieve the item so that we can check against the money criteria
                    ItemDTO itemToBeVended = myItemService.searchItemById(itemCode);

                    //check if item code input is valid (item exists)
                    if (itemToBeVended == null) {
                        System.out.println("Invalid item code.");
                        break;
                    }

                    // check whether the inserted amount is sufficient
                    if (insertedAmount < itemToBeVended.getItemCost()) {
                        // Exception of insufficient funds - CUSTOM EXCEPTION

                        System.out.println("Insufficient funds.");
                        System.out.println("Inserted amount has been returned: " + insertedAmount);
                        System.exit(0);
                    }

                    // if itemCost condition is met
                    else if (insertedAmount >= itemToBeVended.getItemCost()) {
                        // check for itemStock=0 condition
                        // NoItemInventoryException - TO BE IMPLEMENTED
                        if (itemToBeVended.getItemStock() == 0) {
                            System.out.println("Sorry, the item is not available.");
                            System.out.println("Returning your amount : " + insertedAmount);
                            System.out.println("**********************************************");
                            break;
                        }

                        // if itemStock != 0
                        else {
                            //stock of the item selected decreases by 1
                            itemToBeVended.setItemStock(itemToBeVended.getItemStock() - 1);

                            // call vendItem(), passing the itemToBeVended as parameter(with stock -1)
                            // and assign its return value to a ItemDTO vendedItem
                            ItemDTO vendedItem = myItemService.vendItem(itemToBeVended);

                            //stock of the item selected decreases by 1
                            //vendedItem.setItemStock(vendedItem.getItemStock() - 1);

                            System.out.println("Done. Please collect your " + vendedItem.getItemName() +".\n");
                            System.out.println("----------------------------------------------");

                            System.out.println("If there is any change, you should collect the following:");

                            // calculate the change due amount
                            int dueAmount = insertedAmount - vendedItem.getItemCost();
                            //call calculateChange() from Change class, passing dueAmount as parameter
                            Change.calculateChange(dueAmount);

                            System.out.println("***************************************************");
                            System.out.println("-- Thank you for your purchase. Have a nice day! --");
                            System.out.println("***************************************************\n");
                            break;
                        }

                    }
                //User chooses to Exit
                case 2:
                    System.out.println("Thank you for using the vending machine.");
                    //write to file
                    try { //handle the exception here (propagated from the DAO layer)
                        myItemService.writeToFile();
                    } catch (IOException e) {
                        System.out.println("Sorry, something went wrong when saving data to file. \n Please try again.");
                    }
                    System.out.println("Items data store collection updated.");
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Select either 1 or 2 ");
                    break;
            }
        } while (option == 'n'); // user needs to choose Exit to exit the program
                                 // and the DB file is updated accordingly
                                 // while using the vending machine, the stock is related to a collection
                                 //designed on purpose so that the item with stock=0 cannot be vended
                                 //(if stock=1 initially, it can be vended once, but no further)
    }
}
