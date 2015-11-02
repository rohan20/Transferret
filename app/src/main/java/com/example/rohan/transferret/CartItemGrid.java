package com.example.rohan.transferret;

import android.widget.ImageView;

/**
 * Created by Rohan on 01-Nov-15.
 */
public class CartItemGrid
{
    String itemName;
    long itemPrice;
    int itemImage;

    public CartItemGrid(String itemName, long itemPrice, int itemImage)
    {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
    }
}
