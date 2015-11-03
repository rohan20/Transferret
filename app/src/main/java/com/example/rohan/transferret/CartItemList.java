package com.example.rohan.transferret;

/**
 * Created by Rohan on 02-Nov-15.
 */
public class CartItemList
{
    String itemName;
    long itemPrice;
    int itemImage;

    public CartItemList(String itemName, long itemPrice, int itemImage)
    {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
    }
}
