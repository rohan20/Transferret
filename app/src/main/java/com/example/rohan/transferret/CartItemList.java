package com.example.rohan.transferret;

/**
 * Created by Rohan on 02-Nov-15.
 */
public class CartItemList
{
    String itemName;
    long itemPrice;
    int itemImage;
    long itemQuantity;
    String itemTimeStamp;
    long itemPriceTotal;


    public CartItemList(String itemName, long itemPrice, long itemQuantity, int itemImage, String itemTimeStamp, long itemPriceTotal)
    {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemImage = itemImage;
        this.itemTimeStamp = itemTimeStamp;
        this.itemPriceTotal = itemPriceTotal;
    }
}
