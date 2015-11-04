package com.example.rohan.transferret;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Rohan on 02-Nov-15.
 */
public class ListArrayAdapter extends ArrayAdapter {

    Context mContext;
    CartOpenHelper helper;
    SQLiteDatabase db;
    ContentValues cv;

    public ListArrayAdapter(Context context, int resource, CartItemList[] objects)
    {
        super(context, resource, objects);
        mContext = context;
    }

    public class ListItemVIewHolder
    {
        TextView tvName;
        ImageButton ibAddToCart;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        if(convertView == null)
        {
            convertView = View.inflate(mContext, R.layout.list_item_layout, null);
            ListItemVIewHolder vh = new ListItemVIewHolder();
            vh.tvName = (TextView)convertView.findViewById(R.id.textViewListItemName);
            vh.ibAddToCart = (ImageButton)convertView.findViewById(R.id.addToCart);
            Typeface Sitka = Typeface.createFromAsset(getContext().getAssets(), "fonts/Sitka.ttc");
            vh.tvName.setTypeface(Sitka);
            convertView.setTag(vh);
        }

        helper = new CartOpenHelper(mContext, null, 1);
        db = helper.getWritableDatabase();
        cv = new ContentValues();

        final ListItemVIewHolder vh = (ListItemVIewHolder)convertView.getTag();
        final CartItemList item = (CartItemList) getItem(position);
        vh.tvName.setText(item.itemName);

        final View finalConvertView = convertView;
        vh.ibAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setTitle("Add to cart?");

                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        cv.put("CartItemsName", "" + item.itemName);
                        cv.put("CartItemsPrice", "" + item.itemPrice);
                        Cart.grandTotal += item.itemPrice;
                        db.insert(CartOpenHelper.CART_TABLE, null, cv);
                        vh.ibAddToCart.setImageResource(R.drawable.ic_check_green_300_36dp);
                        Snackbar.make(finalConvertView, item.itemName + " added to cart.", Snackbar.LENGTH_SHORT).show();
                    }
                });

                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                b.create().show();

            }
        });

        return convertView;

    }

}
