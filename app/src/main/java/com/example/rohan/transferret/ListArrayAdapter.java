package com.example.rohan.transferret;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Rohan on 02-Nov-15.
 */
public class ListArrayAdapter extends ArrayAdapter {

    Context mContext;

    public ListArrayAdapter(Context context, int resource, CartItemList[] objects)
    {
        super(context, resource, objects);
        mContext = context;
    }

    public class ListItemVIewHolder
    {
        TextView tvName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if(convertView == null)
        {
            convertView = View.inflate(mContext, R.layout.list_item_layout, null);
            ListItemVIewHolder vh = new ListItemVIewHolder();
            vh.tvName = (TextView)convertView.findViewById(R.id.textViewListItemName);
            Typeface Sitka = Typeface.createFromAsset(getContext().getAssets(), "fonts/Sitka.ttc");
            vh.tvName.setTypeface(Sitka);
            convertView.setTag(vh);
        }

        ListItemVIewHolder vh = (ListItemVIewHolder)convertView.getTag();
        CartItemList item = (CartItemList) getItem(position);
        vh.tvName.setText(item.itemName);

        return convertView;

    }

}
