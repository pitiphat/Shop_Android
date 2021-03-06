package com.example.guide.shop;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.Integer.valueOf;

public class MenuListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ListView listview_items;
    private LisViewAdapter listAdapter;
    private YoutubeResult youtubeResult;
    private int TableId;

    static Dialog d ;
    private String itemId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        new FeedTask().execute(getString(R.string.api_1));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        listview_items = (ListView) findViewById(R.id.listview_items);
        listview_items.setAdapter(new LisViewAdapter());

        listview_items = (ListView) findViewById(R.id.listview_items);
        listAdapter = new LisViewAdapter();
        listview_items.setAdapter(listAdapter);


        View chooseTable = findViewById(R.id.nav2);
        chooseTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTable();
            }
        });

        View btnOrder = findViewById(R.id.nav3);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Order.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class FeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url(params[0])
                    .build();

            try {
                Response result = client.newCall(request).execute();
                return result.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            Log.d("ItemList", "ItemList : " + s);
            Gson gson = new Gson();
            youtubeResult = gson.fromJson(s, YoutubeResult.class);
            listAdapter.notifyDataSetChanged();

        }
    }



    public class Gateway extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            Log.d("Params", "Params : " + params);

            OkHttpClient client = new OkHttpClient();

            RequestBody postData = new FormBody.Builder()
                    .add("table_id", params[1])
                    .add("items_id", params[2])
                    .add("amount", params[3])
                    .build();


            Request request = new Request.Builder()
                    .url(params[0])
                    .post(postData)
                    .build();

            try {
                Response result = client.newCall(request).execute();
                return result.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            Log.d("Order respond", "Package Name : " + s);


        }
    }

    private class LisViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (youtubeResult == null) {
                return 0;
            }
            else{
                Log.d("feedLog", "Package Name : " );
                return youtubeResult.getYoutubes().size();
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Viewholder holder = null;

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_listview, null);
                holder = new Viewholder();
                holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
                holder.txt_description = (TextView) convertView.findViewById(R.id.txt_description);
                holder.txt_price= (TextView) convertView.findViewById(R.id.txt_price);
                holder.item_images = (ImageView) convertView.findViewById(R.id.img_item);

                holder.chooseItem = (TextView) convertView.findViewById(R.id.ChooseItem);
                holder.chooseItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        itemId = (String) v.getTag(R.id.ChooseItem); //index ของ array

//                        Log.d("BTN_CHOODE", "BTN_CHOODE : " + itemId);


                        showDialogAmount();
                    }
                });

                convertView.setTag(holder);

            } else {
                holder = (Viewholder) convertView.getTag();
            }


            holder.item_images.setTag(R.id.img_item, position);
            YoutubeResult.YoutubesBean item = youtubeResult.getYoutubes().get(position);
            holder.txt_name.setText(item.getName());

            String imageURL = getString(R.string.api_2)+"/shop-slim/img_items/"+item.getImg()+"?dummy=3";

//            Log.d("imageURL", "imageURL : " + imageURL);
            Glide.with(getApplicationContext()).load(imageURL).into(holder.item_images);

            holder.txt_price.setText(item.getPrice() + " Baht ");

           // holder.chooseItem.setTag(R.id.ChooseItem, position); //ส่งตำแหน่ง ของ array เข้าไป
            holder.chooseItem.setTag(R.id.ChooseItem, item.getId());

            return convertView;
        }
    }

    public class Viewholder {
        ImageView item_images;
        TextView txt_name;
        TextView txt_price;
        TextView txt_description;
        TextView chooseItem;
    }
    public void showDialogAmount()
    {


        final Dialog d = new Dialog(MenuListActivity.this);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.number_dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);

        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(10);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("ItemID", "ItemID : " + itemId);
//                Log.d("feedLog", "Package Name : " + TableId);
                String amount = String.valueOf(np.getValue());
//                Toast.makeText(getApplicationContext(),String.valueOf(np.getValue()), Toast.LENGTH_LONG).show();

                new Gateway().execute(getString(R.string.api_2)+"/shop-slim/api/order", String.valueOf(TableId), String.valueOf(itemId), amount);
                d.dismiss();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });


        d.show();


    }

    public void showDialogTable()
    {


        final Dialog d = new Dialog(MenuListActivity.this);
        d.setTitle("TableNumber");
        d.setContentView(R.layout.table_number_dialog);
        Button b1 = (Button) d.findViewById(R.id.tableBtnOk);
        Button b2 = (Button) d.findViewById(R.id.tableBtnCancel);

        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPickerTable);
        np.setMaxValue(10);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("feedLog", "Package Name : " + TableId);
                TableId = valueOf(np.getValue());
//                Toast.makeText(getApplicationContext(),String.valueOf(np.getValue()), Toast.LENGTH_LONG).show();


                d.dismiss();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });


        d.show();


    }
}
