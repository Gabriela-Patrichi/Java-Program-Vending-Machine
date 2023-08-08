package model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ItemDTO {

    //declare attributes of the Item (private - encapsulation)
    private int itemId;
    private String itemName;
    // private BigDecimal itemCost;
    private int itemCost;
    private int itemStock;

    //constructor
    public ItemDTO(int itemId, String itemName, int itemCost, int itemStock) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemStock = itemStock;
    }

    //getters and setters for the attributes (for public access)
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemCost() {
        return itemCost;
    }

    public void setItemCost(int itemCost) {
        this.itemCost = itemCost;
    }

    public int getItemStock() {
        return itemStock;
    }

    public void setItemStock(int itemStock) {
        this.itemStock = itemStock;
    }

    @Override
    public String toString() {
        return
                "itemId=" + itemId +
                        ",itemName=" + itemName +
                        ",itemCost=" + itemCost +
                        ",itemStock=" + itemStock;
    }
}
