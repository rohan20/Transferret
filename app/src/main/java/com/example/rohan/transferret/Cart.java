package com.example.rohan.transferret;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Cart extends AppCompatActivity
{

    TextView tvTotalValue;
    ListView cartList;
    ArrayList<CartItemList> cart;

    Snackbar snackbar;


    CartOpenHelper helper;
    SQLiteDatabase db;
    CartArrayAdapter adapter;

    CoordinatorLayout parentLayout;

    public String[] getCartItems()
    {
        helper = new CartOpenHelper(this, null, 1);
        db = helper.getReadableDatabase();

        String columns[] = {CartOpenHelper.CART_ITEM_NAME};
        Cursor c = db.query(CartOpenHelper.CART_TABLE, columns, null, null, null, null, null);

        int i = 0;
        String output[] = new String[c.getCount()];
        while (c.moveToNext())
            output[i++] = c.getString(c.getColumnIndex(CartOpenHelper.CART_ITEM_NAME));

        return output;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        parentLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        setTitle("Your Cart");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        cartList = (ListView)findViewById(R.id.cartListView2);
        tvTotalValue = (TextView)findViewById(R.id.totalValue);

        Typeface Sitka = Typeface.createFromAsset(getAssets(), "fonts/Sitka.ttc");
        tvTotalValue.setTypeface(Sitka);

        String cartItems[] = getCartItems();
        cart = new ArrayList<>();

        for(int i = 0; i < cartItems.length; i++)
            cart.add(new CartItemList(cartItems[i]));

        adapter = new CartArrayAdapter(this, 0, cart);
        cartList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clearCart)
        {

            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setMessage("Are you sure you want to delete all items in your cart?");
            b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cart.clear();
                    db = helper.getWritableDatabase();
                    db.execSQL("DELETE FROM " + CartOpenHelper.CART_TABLE);
                    cartList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

//                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)tvTotalValue.getLayoutParams();
//                    params.setAnchorId(R.id.);
//                    tvTotalValue.setLayoutParams(params);

                    snackbar = Snackbar.make(parentLayout, "Cart is now empty.", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", null);

                    snackbar.show();
                }
            });
            b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            b.create().show();
        }

        return super.onOptionsItemSelected(item);
    }

}
