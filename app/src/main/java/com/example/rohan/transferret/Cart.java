package com.example.rohan.transferret;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.CountDownTimer;
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

    TextView tvTotalValue;
    ListView cartList;
    ArrayList<CartItemList> cart;

    Snackbar snackbar;

    CartOpenHelper helper;
    SQLiteDatabase db;
    CartArrayAdapter adapter;

    CoordinatorLayout parentLayout;

    long totalCartValue = 0;
    long priceOfThisProduct = 0;

    String[] choices;

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
        cartList.setOnItemClickListener(this);

        Typeface Sitka = Typeface.createFromAsset(getAssets(), "fonts/Sitka.ttc");
//        tvTotalValue.setTypeface(Sitka);

        String cartItems[] = getCartItems();
        cart = new ArrayList<>();

        for(int i = 0; i < cartItems.length; i++)
            cart.add(new CartItemList(cartItems[i], 5000, R.drawable.oneplus2));

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

        }
        if(id == R.id.action_email)
        {

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
        final CartItemList item = (CartItemList) parent.getItemAtPosition(position);

        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setSingleChoiceItems(choices, 0, new DialogInterface.OnClickListener() {
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
                        public void onClick(View v)
                        {
                            Snackbar.make(parentLayout, "New Quantity: " + numberPicker.getValue(), Snackbar.LENGTH_SHORT).show();
                            tvQuantity.setText("Quantity: " + numberPicker.getValue());
                            priceOfThisProduct = (numberPicker.getValue() * item.itemPrice);
                            tvPrice.setText("Rs." + priceOfThisProduct);
                            d.dismiss();
                            totalCartValue += numberPicker.getValue() * item.itemPrice;
                            tvTotalValue.setText("Total Cart Value: Rs. " + totalCartValue);
                        }
                    });

                    d.show();

                } else if (selection[0] == 1) {
                    CartItemList item = (CartItemList) adapter.getItem(position);
                    helper = new CartOpenHelper(Cart.this, null, 1);
                    db = helper.getReadableDatabase();

                    totalCartValue -= priceOfThisProduct;
                    tvTotalValue.setText("Total Cart Value: Rs. " + totalCartValue);

                    Snackbar.make(view, item.itemName + " removed from cart.", Snackbar.LENGTH_SHORT).show();
                    cartList.setAdapter(adapter);
                    db.delete(CartOpenHelper.CART_TABLE, CartOpenHelper.CART_ITEM_NAME + " = " + "'" + item.itemName + "'", null);
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
