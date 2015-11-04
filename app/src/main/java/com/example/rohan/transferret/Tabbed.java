package com.example.rohan.transferret;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Tabbed extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        setTitle("Transferret");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        if (id == R.id.action_showCart)
        {
            Intent i = new Intent(Tabbed.this, Cart.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "GRID";
                case 1:
                    return "LIST";
                case 2:
                    return "CART";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {

        GridView grid;
        ListView lvList;
        ListView cartList;
        ArrayList<CartItemList> cart;
        CartItemGrid[] cartItemGrid;
        CartItemList[] cartItemList;

        CartOpenHelper helper;
        SQLiteDatabase db;
        ContentValues cv = new ContentValues();
        CartArrayAdapter adapter;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            switch (sectionNumber)
            {
                case 0: return new PlaceholderFragment();

                default:
                            PlaceholderFragment fragment = new PlaceholderFragment();
                            Bundle args = new Bundle();
                            args.putInt(ARG_SECTION_NUMBER, sectionNumber + 1);
                            fragment.setArguments(args);
                            return fragment;
            }
        }

        public PlaceholderFragment() {
        }


        public String[] getCartItems()
        {
            helper = new CartOpenHelper(getContext(), null, 1);
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
        public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {

            helper = new CartOpenHelper(getContext(), null, 1);
            db = helper.getReadableDatabase();

            View rootView;
            Bundle args = getArguments();
            int currentView = args.getInt(ARG_SECTION_NUMBER) - 2;

            if(currentView == 1)
            {
                rootView = inflater.inflate(R.layout.fragment_list, container, false);

                lvList = (ListView) rootView.findViewById(R.id.listView);


                cartItemList = new CartItemList[20];

                cartItemList[0] = new CartItemList("OnePlus 2", 25000, 1, R.drawable.oneplus2, null, 25000);
                cartItemList[1] = new CartItemList("iPhone 6", 60000, 1, R.drawable.iphone6, null, 60000);
                cartItemList[2] = new CartItemList("Moto G", 8000, 1, R.drawable.motog, null, 8000);
                cartItemList[3] = new CartItemList("Blackberry", 15000, 1, R.drawable.oneplus2, null, 15000);
                cartItemList[4] = new CartItemList("Samsung Grand", 10000, 1, R.drawable.grand, null, 10000);
                cartItemList[5] = new CartItemList("Mi 4", 10000, 1, R.drawable.xiaomimi4, null, 10000);
                cartItemList[6] = new CartItemList("Sony Xperia", 6000, 1, R.drawable.xperia, null, 6000);
                cartItemList[7] = new CartItemList("Nexus 6", 24000, 1, R.drawable.nexus6, null, 24000);
                cartItemList[8] = new CartItemList("iPhone 5S", 40000, 1, R.drawable.iphone6, null, 40000);
                cartItemList[9] = new CartItemList("Nokia 1100", 150, 1, R.drawable.nokia1100, null, 150);
                cartItemList[10] = new CartItemList("OnePlus 2", 25000, 1, R.drawable.oneplus2, null, 25000);
                cartItemList[11] = new CartItemList("iPhone 6", 60000, 1, R.drawable.iphone6, null, 60000);
                cartItemList[12] = new CartItemList("Moto G", 8000, 1, R.drawable.motog, null, 8000);
                cartItemList[13] = new CartItemList("Blackberry", 15000, 1, R.drawable.oneplus2, null, 15000);
                cartItemList[14] = new CartItemList("Samsung Grand", 10000, 1, R.drawable.grand, null, 10000);
                cartItemList[15] = new CartItemList("Mi 4", 10000, 1, R.drawable.xiaomimi4, null, 10000);
                cartItemList[16] = new CartItemList("Sony Xperia", 6000, 1, R.drawable.xperia, null, 6000);
                cartItemList[17] = new CartItemList("Nexus 6", 24000, 1, R.drawable.nexus6, null, 24000);
                cartItemList[18] = new CartItemList("iPhone 5S", 40000, 1, R.drawable.iphone6, null, 40000);
                cartItemList[19] = new CartItemList("Nokia 1100", 150, 1, R.drawable.nokia1100, null, 150);

                ListArrayAdapter files = new ListArrayAdapter(getContext(), 0, cartItemList);
                lvList.setAdapter(files);


                lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                        View v = inflater.inflate(R.layout.list_item_on_click, null);
                        b.setView(v);
                        ImageView iv = (ImageView)v.findViewById(R.id.listItemImage);

                        if(cartItemList[position].itemName.equals("OnePlus 2"))
                        {
                            b.setTitle(cartItemList[position].itemName + ": Rs." + cartItemList[position].itemPrice);
                            Picasso.with(getContext()).load(R.drawable.oneplus2).into(iv);
                        }
                        else if(cartItemList[position].itemName.equals("iPhone 6"))
                        {
                            b.setTitle(cartItemList[position].itemName + ": Rs." + cartItemList[position].itemPrice);
                            Picasso.with(getContext()).load(R.drawable.iphone6).into(iv);
                        }
                        else if(cartItemList[position].itemName.equals("Moto G"))
                        {
                            b.setTitle(cartItemList[position].itemName + ": Rs." + cartItemList[position].itemPrice);
                            Picasso.with(getContext()).load(R.drawable.motog).into(iv);
                        }
                        else if(cartItemList[position].itemName.equals("Blackberry"))
                        {
                            b.setTitle(cartItemList[position].itemName + ": Rs." + cartItemList[position].itemPrice);
                            Picasso.with(getContext()).load(R.drawable.oneplus2).into(iv);
                        }
                        else if(cartItemList[position].itemName.equals("Samsung Grand"))
                        {
                            b.setTitle(cartItemList[position].itemName + ": Rs." + cartItemList[position].itemPrice);
                            Picasso.with(getContext()).load(R.drawable.grand).into(iv);
                        }
                        else if(cartItemList[position].itemName.equals("Mi 4"))
                        {
                            b.setTitle(cartItemList[position].itemName + ": Rs." + cartItemList[position].itemPrice);
                            Picasso.with(getContext()).load(R.drawable.xiaomimi4).into(iv);
                        }
                        else if(cartItemList[position].itemName.equals("Sony Xperia"))
                        {
                            b.setTitle(cartItemList[position].itemName + ": Rs." + cartItemList[position].itemPrice);
                            Picasso.with(getContext()).load(R.drawable.xperia).into(iv);
                        }
                        else if(cartItemList[position].itemName.equals("Nexus 6"))
                        {
                            b.setTitle(cartItemList[position].itemName + ": Rs." + cartItemList[position].itemPrice);
                            Picasso.with(getContext()).load(R.drawable.nexus6).into(iv);
                        }
                        else if(cartItemList[position].itemName.equals("iPhone 5S"))
                        {
                            b.setTitle(cartItemList[position].itemName + ": Rs." + cartItemList[position].itemPrice);
                            Picasso.with(getContext()).load(R.drawable.iphone6).into(iv);
                        }
                        else if(cartItemList[position].itemName.equals("Nokia 1100"))
                        {
                            b.setTitle(cartItemList[position].itemName + ": Rs." + cartItemList[position].itemPrice);
                            Picasso.with(getContext()).load(R.drawable.nokia1100).into(iv);
                        }

                        b.create().show();
                    }
                });

            }
            else if(currentView == 0)
            {
                rootView = inflater.inflate(R.layout.fragment_grid, container, false);
                grid = (GridView) rootView.findViewById(R.id.gridView);

                cartItemGrid = new CartItemGrid[20];

                cartItemGrid[0] = new CartItemGrid("OnePlus 2", 25000, 1, R.drawable.oneplus2, null, 25000);
                cartItemGrid[1] = new CartItemGrid("iPhone 6", 60000, 1, R.drawable.iphone6, null, 60000);
                cartItemGrid[2] = new CartItemGrid("Moto G", 8000, 1, R.drawable.motog, null, 8000);
                cartItemGrid[3] = new CartItemGrid("Blackberry", 15000, 1, R.drawable.oneplus2, null, 15000);
                cartItemGrid[4] = new CartItemGrid("Samsung Grand", 10000, 1, R.drawable.grand, null, 10000);
                cartItemGrid[5] = new CartItemGrid("Mi 4", 10000, 1, R.drawable.xiaomimi4, null, 10000);
                cartItemGrid[6] = new CartItemGrid("Sony Xperia", 6000, 1, R.drawable.xperia, null, 6000);
                cartItemGrid[7] = new CartItemGrid("Nexus 6", 24000, 1, R.drawable.nexus6, null, 24000);
                cartItemGrid[8] = new CartItemGrid("iPhone 5S", 40000, 1, R.drawable.iphone6, null, 40000);
                cartItemGrid[9] = new CartItemGrid("Nokia 1100", 150, 1, R.drawable.nokia1100, null, 150);
                cartItemGrid[10] = new CartItemGrid("OnePlus 2", 25000, 1, R.drawable.oneplus2, null, 25000);
                cartItemGrid[11] = new CartItemGrid("iPhone 6", 60000, 1, R.drawable.iphone6, null, 60000);
                cartItemGrid[12] = new CartItemGrid("Moto G", 8000, 1, R.drawable.motog, null, 8000);
                cartItemGrid[13] = new CartItemGrid("Blackberry", 15000, 1, R.drawable.oneplus2, null, 15000);
                cartItemGrid[14] = new CartItemGrid("Samsung Grand", 10000, 1, R.drawable.grand, null, 10000);
                cartItemGrid[15] = new CartItemGrid("Mi 4", 10000, 1, R.drawable.xiaomimi4, null, 10000);
                cartItemGrid[16] = new CartItemGrid("Sony Xperia", 6000, 1, R.drawable.xperia, null, 6000);
                cartItemGrid[17] = new CartItemGrid("Nexus 6", 24000, 1, R.drawable.nexus6, null, 24000);
                cartItemGrid[18] = new CartItemGrid("iPhone 5S", 40000, 1, R.drawable.iphone6, null, 40000);
                cartItemGrid[19] = new CartItemGrid("Nokia 1100", 150, 1, R.drawable.nokia1100, null, 150);


                GridArrayAdapter files = new GridArrayAdapter(getContext(), R.layout.cart_item_layout, cartItemGrid);
                grid.setAdapter(files);

                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                        b.setMessage("Add to cart?");
                        b.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                cv.put("CartItemsName", cartItemGrid[position].itemName);
                                cv.put("CartItemsPrice", cartItemGrid[position].itemPrice);
                                cv.put("CartItemsPriceTotal", cartItemGrid[position].itemPrice);
                                cv.put("CartItemsQuantity", 1);
                                cv.put("CartItemsTimeStamp", System.currentTimeMillis());

                                Cart.grandTotal += cartItemGrid[position].itemPrice;

                                db.insert(CartOpenHelper.CART_TABLE, null, cv);
                                Snackbar.make(view, cartItemGrid[position].itemName + " added to cart.", Snackbar.LENGTH_SHORT).show();
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


            }
            else
            {
                rootView = inflater.inflate(R.layout.fragment_cart, container, false);

                String cartItems[] = getCartItems();
                cart = new ArrayList<>();

                for(int i = 0; i < cartItems.length; i++)
                    cart.add(cartItemList[i]);

                adapter = new CartArrayAdapter(getContext(), 0, cart);
                adapter.notifyDataSetChanged();
                cartList = (ListView) rootView.findViewById(R.id.cartListView);
                cartList.setAdapter(adapter);

            }

            return rootView;
        }
    }
}
