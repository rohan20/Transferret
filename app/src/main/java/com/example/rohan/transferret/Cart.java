package com.example.rohan.transferret;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

public class Cart extends AppCompatActivity implements AdapterView.OnItemClickListener
{

    TextView tvCartValue;
    ListView cartList;
    ArrayList<CartItemList> cart;

    Snackbar snackbar;

    CartOpenHelper helper;
    SQLiteDatabase db;
    CartArrayAdapter adapter;

    CoordinatorLayout parentLayout;

    long priceOfThisProduct = 0;
    public static long grandTotal = 0;

    ContentValues cv;

    String[] choices;

    public String[] getCartItems()
    {
        helper = new CartOpenHelper(this, null, 1);
        db = helper.getReadableDatabase();

        String columns[] = {CartOpenHelper.CART_ITEM_NAME, CartOpenHelper.CART_ITEM_PRICE, CartOpenHelper.CART_ITEM_QUANTITY, CartOpenHelper.CART_ITEM_TIMESTAMP, CartOpenHelper.CART_ITEM_PRICE_TOTAL};
        Cursor c = db.query(CartOpenHelper.CART_TABLE, columns, null, null, null, null, null);

        int i = 0;
        String output[] = new String[5 * c.getCount()];
        while (c.moveToNext())
        {
            output[i++] = c.getString(c.getColumnIndex(CartOpenHelper.CART_ITEM_NAME));
            output[i++] = c.getString(c.getColumnIndex(CartOpenHelper.CART_ITEM_PRICE));
            output[i++] = c.getString(c.getColumnIndex(CartOpenHelper.CART_ITEM_QUANTITY));
            output[i++] = c.getString(c.getColumnIndex(CartOpenHelper.CART_ITEM_TIMESTAMP));
            output[i++] = c.getString(c.getColumnIndex(CartOpenHelper.CART_ITEM_PRICE_TOTAL));
        }

        return output;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        loadActivity();

    }


    public void loadActivity()
    {
        cv = new ContentValues();

        parentLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        setTitle("Your Cart");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        cartList = (ListView)findViewById(R.id.cartListView2);
        tvCartValue = (TextView)findViewById(R.id.totalValue);
        tvCartValue.setText("Total Cart Value: Rs. " + Cart.grandTotal);
        cartList.setOnItemClickListener(this);

        String cartItems[] = getCartItems();
        cart = new ArrayList<>();

        for(int i = 0; i < cartItems.length; i+=5)
        {
            switch(cartItems[i])
            {
                case "OnePlus 2": cart.add(new CartItemList(cartItems[i], Long.parseLong(cartItems[i+1]), Long.parseLong(cartItems[i+2]), R.drawable.oneplus2, cartItems[i+3], Long.parseLong(cartItems[i+4])));
                    break;
                case "iPhone 6": cart.add(new CartItemList(cartItems[i], Long.parseLong(cartItems[i+1]), Long.parseLong(cartItems[i+2]), R.drawable.iphone6, cartItems[i+3], Long.parseLong(cartItems[i+4])));
                    break;
                case "Moto G": cart.add(new CartItemList(cartItems[i], Long.parseLong(cartItems[i+1]), Long.parseLong(cartItems[i+2]), R.drawable.motog, cartItems[i+3], Long.parseLong(cartItems[i+4])));
                    break;
                case "Blackberry": cart.add(new CartItemList(cartItems[i], Long.parseLong(cartItems[i+1]), Long.parseLong(cartItems[i + 2]), R.drawable.oneplus2, cartItems[i+3], Long.parseLong(cartItems[i+4])));
                    break;
                case "Samsung Grand": cart.add(new CartItemList(cartItems[i], Long.parseLong(cartItems[i + 1]), Long.parseLong(cartItems[i+2]), R.drawable.grand, cartItems[i+3], Long.parseLong(cartItems[i+4])));
                    break;
                case "Mi 4": cart.add(new CartItemList(cartItems[i], Long.parseLong(cartItems[i+1]), Long.parseLong(cartItems[i + 2]), R.drawable.xiaomimi4, cartItems[i+3], Long.parseLong(cartItems[i+4])));
                    break;
                case "Sony Xperia": cart.add(new CartItemList(cartItems[i], Long.parseLong(cartItems[i+1]), Long.parseLong(cartItems[i+2]), R.drawable.xperia, cartItems[i+3], Long.parseLong(cartItems[i+4])));
                    break;
                case "Nexus 6": cart.add(new CartItemList(cartItems[i], Long.parseLong(cartItems[i+1]), Long.parseLong(cartItems[i + 2]), R.drawable.nexus6, cartItems[i+3], Long.parseLong(cartItems[i+4])));
                    break;
                case "iPhone 5S": cart.add(new CartItemList(cartItems[i], Long.parseLong(cartItems[i+1]), Long.parseLong(cartItems[i + 2]), R.drawable.iphone6, cartItems[i+3], Long.parseLong(cartItems[i+4])));
                    break;
                case "Nokia 1100": cart.add(new CartItemList(cartItems[i], Long.parseLong(cartItems[i+1]), Long.parseLong(cartItems[i + 2]), R.drawable.nokia1100, cartItems[i+3], Long.parseLong(cartItems[i+4])));
                    break;

            }
        }


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

        if(id == R.id.action_call)
        {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+919920712205"));
            startActivity(callIntent);
        }
        if(id == R.id.action_email)
        {
            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.setData(Uri.parse("mailto:rohan.bvcoe@gmail.com"));
            i.putExtra(Intent.EXTRA_SUBJECT, "Internship Application");
            if(i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }

        }
        if(id == R.id.action_goBack)
        {
            Intent i = new Intent(Cart.this, Tabbed.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
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

                    Cart.grandTotal = 0;
                    tvCartValue.setText("Total Cart Value: Rs. " + Cart.grandTotal);

                    snackbar = Snackbar.make(parentLayout, "Cart is now empty.", Snackbar.LENGTH_SHORT);
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

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id)
    {
        final int[] selection = {10};
        choices = new String[2];
        choices[0] = "Change Quantity";
        choices[1] = "Remove";

        final TextView tvQuantity = (TextView)view.findViewById(R.id.quantity);
        final TextView tvPrice = (TextView)view.findViewById(R.id.totalCost);
        final CartItemList item = (CartItemList) adapter.getItem(position);

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(item.itemName + ": Rs. " + item.itemPrice);

        switch(item.itemName)
        {
            case "OnePlus 2":
                b.setIcon(R.drawable.oneplus2);
                break;
            case "iPhone 6":
                b.setIcon(R.drawable.iphone6);
                break;
            case "Moto G":
                b.setIcon(R.drawable.motog);
                break;
            case "Blackberry":
                b.setIcon(R.drawable.oneplus2);
                break;
            case "Samsung Grand":
                b.setIcon(R.drawable.grand);
                break;
            case "Mi 4":
                b.setIcon(R.drawable.xiaomimi4);
                break;
            case "Sony Xperia":
                b.setIcon(R.drawable.xperia);
                break;
            case "Nexus 6":
                b.setIcon(R.drawable.nexus6);
                break;
            case "iPhone 5S":
                b.setIcon(R.drawable.iphone6);
                break;
            case "Nokia 1100":
                b.setIcon(R.drawable.nokia1100);
                break;

        }

        b.setSingleChoiceItems(choices, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choices[which].equals("Change Quantity")) {
                    selection[0] = 0;
                } else if (choices[which].equals("Remove")) {
                    selection[0] = 1;
                }
            }
        });
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selection[0] == 0) {

                    final Dialog d = new Dialog(Cart.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.number_picker, null);
                    d.setContentView(v);
                    d.setTitle("Select Quantity");
                    final NumberPicker numberPicker = (NumberPicker) v.findViewById(R.id.numberPicker);
                    Button setQuantity = (Button) v.findViewById(R.id.butonSetQuantity);
                    numberPicker.setMinValue(1);
                    numberPicker.setMaxValue(10);
                    numberPicker.setValue(1);
                    numberPicker.setWrapSelectorWheel(false);

                    setQuantity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Snackbar.make(parentLayout, "New Quantity: " + numberPicker.getValue(), Snackbar.LENGTH_SHORT).show();
                            priceOfThisProduct = (numberPicker.getValue() * item.itemPrice);
                            Snackbar.make(parentLayout, "New Quantity: " + priceOfThisProduct, Snackbar.LENGTH_SHORT).show();

                            cv.put("CartItemsName", "" + item.itemName);
                            cv.put("CartItemsPrice", item.itemPrice);
                            cv.put("CartItemsPriceTotal", priceOfThisProduct);
                            cv.put("CartItemsQuantity", numberPicker.getValue());
                            cv.put("CartItemsTimeStamp", System.currentTimeMillis());
                            db.insert(CartOpenHelper.CART_TABLE, null, cv);

                            Cart.grandTotal += priceOfThisProduct - Long.parseLong(tvPrice.getText().toString());

                            tvQuantity.setText(numberPicker.getValue() + "");
                            tvPrice.setText(priceOfThisProduct + "");
                            tvCartValue.setText("Total Cart Value: Rs. " + Cart.grandTotal);

                            d.dismiss();
                            db.delete(CartOpenHelper.CART_TABLE, CartOpenHelper.CART_ITEM_TIMESTAMP + " = " + "'" + item.itemTimeStamp + "'", null);
                            loadActivity();
                        }
                    });

                    d.show();

                } else if (selection[0] == 1) {
                    CartItemList item = (CartItemList) adapter.getItem(position);
                    helper = new CartOpenHelper(Cart.this, null, 1);
                    db = helper.getReadableDatabase();

                    Cart.grandTotal -= Long.parseLong(tvPrice.getText().toString());
                    tvCartValue.setText("Total Cart Value: Rs. " + Cart.grandTotal);

                    Snackbar.make(view, item.itemName + " removed from cart.", Snackbar.LENGTH_SHORT).show();
                    cartList.setAdapter(adapter);

                    db.delete(CartOpenHelper.CART_TABLE, CartOpenHelper.CART_ITEM_TIMESTAMP + " = " + "'" + item.itemTimeStamp + "'", null);
                    cart.remove(adapter.getPosition(item));
                    adapter.notifyDataSetChanged();
                } else
                    Snackbar.make(view, "No selection made", Snackbar.LENGTH_LONG).show();
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        b.create().show();
    }

}
