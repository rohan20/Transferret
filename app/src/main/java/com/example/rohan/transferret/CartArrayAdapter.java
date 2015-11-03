package com.example.rohan.transferret;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rohan on 01-Nov-15.
 */
public class CartArrayAdapter extends ArrayAdapter
{

    Context mContext;

    public CartArrayAdapter(Context context, int resource, List objects)
    {
        super(context, resource, objects);
        mContext = context;
    }

    public class CartItemViewHolder
    {
        TextView tvName;
        TextView tvQuantity;
        TextView tvTotalCost;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if(convertView == null)
        {
            convertView = View.inflate(mContext, R.layout.cart_item_layout, null);
            CartItemViewHolder vh = new CartItemViewHolder();
            vh.tvName = (TextView)convertView.findViewById(R.id.textViewCartItemName);
            vh.tvQuantity = (TextView)convertView.findViewById(R.id.quantity);
            vh.tvTotalCost = (TextView)convertView.findViewById(R.id.totalCost);
            Typeface Sitka = Typeface.createFromAsset(getContext().getAssets(), "fonts/Sitka.ttc");
            vh.tvName.setTypeface(Sitka);
            convertView.setTag(vh);
        }

        CartItemViewHolder vh = (CartItemViewHolder)convertView.getTag();
        CartItemList item = (CartItemList) getItem(position);
        vh.tvName.setText(item.itemName);
        vh.tvTotalCost.setText("Rs." + item.itemPrice);


        return convertView;

    }
}
