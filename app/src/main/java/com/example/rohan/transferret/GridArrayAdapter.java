package com.example.rohan.transferret;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

/**
 * Created by Rohan on 02-Nov-15.
 */
public class GridArrayAdapter extends ArrayAdapter
{

    Context mContext;

    public GridArrayAdapter(Context context, int resource, CartItemGrid[] objects)
    {
        super(context, resource, objects);
        mContext = context;
    }

    public class GridItemViewHolder
    {
        TextView tvItemName;
        TextView tvItemPrice;
        ImageView ivItemPic;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView = View.inflate(mContext, R.layout.grid_item_layout, null);
            GridItemViewHolder vh = new GridItemViewHolder();
            Typeface Sitka = Typeface.createFromAsset(getContext().getAssets(), "fonts/Sitka.ttc");
            vh.tvItemName = (TextView)convertView.findViewById(R.id.name);
            vh.tvItemName.setTypeface(Sitka);
            vh.tvItemPrice = (TextView)convertView.findViewById(R.id.price);
//            vh.tvItemPrice.setTypeface(Sitka);
            vh.ivItemPic = (ImageView)convertView.findViewById(R.id.image);
            convertView.setTag(vh);
        }

        GridItemViewHolder vh = (GridItemViewHolder)convertView.getTag();
        CartItemGrid item = (CartItemGrid) getItem(position);
        vh.tvItemName.setText(item.itemName);
        vh.tvItemPrice.setText("Price: Rs." + item.itemPrice);


        if(item.itemName.equals("OnePlus 2"))
            Picasso.with(mContext).load(R.drawable.oneplus2).into(vh.ivItemPic);
        else if(item.itemName.equals("iPhone 6"))
            Picasso.with(mContext).load(R.drawable.iphone6).into(vh.ivItemPic);
        else if(item.itemName.equals("Moto G"))
            Picasso.with(mContext).load(R.drawable.motog).into(vh.ivItemPic);
        else if(item.itemName.equals("Blackberry"))
            Picasso.with(mContext).load(R.drawable.oneplus2).into(vh.ivItemPic);
        else if(item.itemName.equals("Samsung Grand"))
            Picasso.with(mContext).load(R.drawable.grand).into(vh.ivItemPic);
        else if(item.itemName.equals("Mi 4"))
            Picasso.with(mContext).load(R.drawable.xiaomimi4).into(vh.ivItemPic);
        else if(item.itemName.equals("Sony Xperia"))
            Picasso.with(mContext).load(R.drawable.xperia).into(vh.ivItemPic);
        else if(item.itemName.equals("Nexus 6"))
            Picasso.with(mContext).load(R.drawable.nexus6).into(vh.ivItemPic);
        else if(item.itemName.equals("iPhone 5S"))
            Picasso.with(mContext).load(R.drawable.iphone6).into(vh.ivItemPic);
        else if(item.itemName.equals("Nokia 1100"))
            Picasso.with(mContext).load(R.drawable.nokia1100).into(vh.ivItemPic);




        return convertView;
    }
}
