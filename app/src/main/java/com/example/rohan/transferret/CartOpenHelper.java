package com.example.rohan.transferret;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rohan on 01-Nov-15.
 */
public class CartOpenHelper extends SQLiteOpenHelper
{

    public final static String CART_TABLE = "CartItemsTable";
    public final static String CART_ITEM_NAME = "CartItemsName";
    public final static String CART_ITEM_PRICE = "CartItemsPrice";


    public CartOpenHelper(Context context, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, "My Cart", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + CART_TABLE + " ( " + CART_ITEM_NAME + " varchar(255), " +
                    CART_ITEM_PRICE + " integer )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
