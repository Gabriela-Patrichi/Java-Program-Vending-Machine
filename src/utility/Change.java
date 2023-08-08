package utility;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Change {

    public enum Coins {
        QUARTERS, DIMES, NICKELS, PENNIES;
    }

    public static void calculateChange(int amountDue) {

        //create a hashmap, with enum values as key
        Map<Coins, Integer> changeInUnits = new HashMap<>();

        changeInUnits.put(Coins.QUARTERS, 100);
        changeInUnits.put(Coins.DIMES, 0);
        changeInUnits.put(Coins.NICKELS, 0);
        changeInUnits.put(Coins.PENNIES, 0);

        //calculate the change in coins units
        Integer quarters = amountDue / 25;
        Integer dimes = (amountDue % 25) / 10;
        Integer nickels = (amountDue % 25 % 10) / 5;
        Integer pennies = (amountDue % 25 % 10 % 10 % 5);

        changeInUnits.put(Coins.QUARTERS,quarters);
        changeInUnits.put(Coins.DIMES, dimes);
        changeInUnits.put(Coins.NICKELS, nickels);
        changeInUnits.put(Coins.PENNIES, pennies);

        // get the Set of keys from the map
        Set<Coins> keys = changeInUnits.keySet();

        // print the keys to the screen
        for (Coins k : keys) {
             System.out.println ( k + " " + changeInUnits.get(k));
        }

        //instead, transverse the collection and store each element in a new collection. to call in the view layer

    }
}
