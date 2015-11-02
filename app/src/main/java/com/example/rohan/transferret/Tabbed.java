package com.example.rohan.transferret;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListView;
import android.widget.Toast;

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
                    return "LIST";
                case 1:
                    return "GRID";
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {

            helper = new CartOpenHelper(getContext(), null, 1);
            db = helper.getReadableDatabase();

            View rootView;
            Bundle args = getArguments();
            int currentView = args.getInt(ARG_SECTION_NUMBER) - 2;

            if(currentView == 0)
            {
                rootView = inflater.inflate(R.layout.fragment_list, container, false);

                lvList = (ListView) rootView.findViewById(R.id.listView);

                cartItemList = new CartItemList[10];

                cartItemList[0] = new CartItemList("OnePlus 2");
                cartItemList[1] = new CartItemList("iPhone 6");
                cartItemList[2] = new CartItemList("Moto G");
                cartItemList[3] = new CartItemList("Blackberry");
                cartItemList[4] = new CartItemList("Samsung Grand");
                cartItemList[5] = new CartItemList("Mi 4");
                cartItemList[6] = new CartItemList("Sony Xperia");
                cartItemList[7] = new CartItemList("Nexus 6");
                cartItemList[8] = new CartItemList("iPhone 5S");
                cartItemList[9] = new CartItemList("Nokia 1100");

                ListArrayAdapter files = new ListArrayAdapter(getContext(), 0, cartItemList);
                lvList.setAdapter(files);

                lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, final int position, final long id) {
                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                        b.setTitle("Add to cart?");
//                        if(cartItemList[position].itemName.equals("OnePlus2"))
                            b.setIcon(R.drawable.oneplus2);
                        b.setMessage("Price = Rs.25000");
                        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cv.put("CartItemsName", "" + cartItemList[position].itemName);
                                db.insert(CartOpenHelper.CART_TABLE, null, cv);
                                Snackbar.make(view, cartItemList[position].itemName + " added to cart.", Snackbar.LENGTH_SHORT).show();
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
            else if(currentView == 1)
            {
                rootView = inflater.inflate(R.layout.fragment_grid, container, false);
//                Snackbar.make(rootView.findViewById(android.R.id.content), "Click on item to add to cart..", Snackbar.LENGTH_LONG).show();
                grid = (GridView) rootView.findViewById(R.id.gridView);

                cartItemGrid = new CartItemGrid[10];

                cartItemGrid[0] = new CartItemGrid("OnePlus 2", 25000, R.drawable.oneplus2);
                cartItemGrid[1] = new CartItemGrid("iPhone 6", 60000, R.drawable.iphone6);
                cartItemGrid[2] = new CartItemGrid("Moto G", 8000, R.drawable.motog);
                cartItemGrid[3] = new CartItemGrid("Blackberry", 15000, R.drawable.blackberry);
                cartItemGrid[4] = new CartItemGrid("Samsung Grand", 10000, R.drawable.grand);
                cartItemGrid[5] = new CartItemGrid("Mi 4", 10000, R.drawable.xiaomimi4);
                cartItemGrid[6] = new CartItemGrid("Sony Xperia", 6000, R.drawable.xperia);
                cartItemGrid[7] = new CartItemGrid("Nexus 6", 24000, R.drawable.nexus6);
                cartItemGrid[8] = new CartItemGrid("iPhone 5S", 40000, R.drawable.iphone5s);
                cartItemGrid[9] = new CartItemGrid("Nokia 1100", 150, R.drawable.nokia1100);

                GridArrayAdapter files = new GridArrayAdapter(getContext(), R.layout.cart_item_layout, cartItemGrid);
                grid.setAdapter(files);

                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                        b.setMessage("Add to cart?");
                        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                cv.put("CartItemsName", cartItemGrid[position].itemName);
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
