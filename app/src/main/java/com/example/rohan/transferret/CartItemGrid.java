package com.example.rohan.transferret;

/**
 * Created by Rohan on 01-Nov-15.
 */
public class CartItemGrid
{
    String itemName;
    long itemPrice;
    int itemImage;
    long itemQuantity;
    String itemTimeStamp;
    long itemPriceTotal;

    public CartItemGrid(String itemName, long itemPrice, long itemQuantity, int itemImage, String itemEpochTime, long itemPriceTotal)
    {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemImage = itemImage;
        this.itemTimeStamp = itemEpochTime;
        this.itemPriceTotal = itemPriceTotal;
    }
}
