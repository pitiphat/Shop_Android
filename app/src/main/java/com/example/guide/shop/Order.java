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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.Integer.valueOf;

public class Order extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listview_orders;
    private OrderListResult orderlistResult;
    private int TableId = 0;
    private LisViewAdapter listAdapter;
    private int totalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View btnMenu = findViewById(R.id.nav1);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuListActivity.class));
            }
        });

        View chooseTable = findViewById(R.id.nav2);
        chooseTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTable();
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
        getMenuInflater().inflate(R.menu.order, menu);
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

            RequestBody postData = new FormBody.Builder()
                    .add("table_id", params[1])
                    .build();

            String url = params[0] + "/" + params[1];


            Request request = new Request.Builder()
                    .url(url)
//                    .post(postData)
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

//            Log.d("OrderListResult", "OrderListResult : " + s);
            Gson gson = new Gson();
            orderlistResult = gson.fromJson(s, OrderListResult.class);

            boolean resultStatus = orderlistResult.isResult();

            totalPrice = 0;
            View btnSend = findViewById(R.id.btnSend);
            if (resultStatus == true) {
                int countLoop = orderlistResult.getData().size();

                for (int x = 0; x < countLoop; x++) {
                    OrderListResult.DataBean item = orderlistResult.getData().get(x);
                    totalPrice = totalPrice + (int) item.getTotal_per_item();
                }

                btnSend.setVisibility(View.VISIBLE);
            }
            else{
                btnSend.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"ไม่พบรายการสั่งอาหาร", Toast.LENGTH_SHORT).show();
            }


            TextView totalPriceTxt = (TextView) findViewById(R.id.totalPrice);
            totalPriceTxt.setText("Total Order: " + String.valueOf(totalPrice) + " Baht");


            listAdapter.notifyDataSetChanged();


        }
    }


    private class LisViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (orderlistResult == null || orderlistResult.isResult() == false) {

                return 0;
            } else {
                return orderlistResult.getData().size();
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
                convertView = getLayoutInflater().inflate(R.layout.order_custom_listview, null);
                holder = new Viewholder();
                holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
                holder.txt_amount = (TextView) convertView.findViewById(R.id.txt_amount);
                holder.txt_total_per_price = (TextView) convertView.findViewById(R.id.txt_total_per_price);
                holder.item_images = (ImageView) convertView.findViewById(R.id.img_item);


                convertView.setTag(holder);

            } else {
                holder = (Viewholder) convertView.getTag();
            }

            holder.item_images.setTag(R.id.img_item, position);
            OrderListResult.DataBean item = orderlistResult.getData().get(position);
            holder.txt_name.setText(item.getName());


            String imageURL = getString(R.string.api_2) + "/shop-slim/img_items/" + item.getImg() + "?dummy=3";


            Glide.with(getApplicationContext()).load(imageURL).into(holder.item_images);

            holder.txt_total_per_price.setText(item.getTotal_per_item() + " Baht ");
            holder.txt_amount.setText("จำนวน " + item.getAmount());


//             holder.chooseItem.setTag(R.id.ChooseItem, position); //ส่งตำแหน่ง ของ array เข้าไป
//             holder.chooseItem.setTag(R.id.ChooseItem, item.getId());

            return convertView;
        }

        public class Viewholder {
            ImageView item_images;
            TextView txt_name;
            TextView txt_total_per_price;
            TextView txt_amount;

        }
    }


    public void showDialogTable() {


        final Dialog d = new Dialog(Order.this);
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
                if (TableId > 0) {

                    new FeedTask().execute(getString(R.string.api_2) + "/shop-slim/api/order_list", String.valueOf(TableId));

//            boolean resultStatus = orderlistResult.isResult();
//            Log.d("status", "status : " + resultStatus);


                    listview_orders = (ListView) findViewById(R.id.listview_orders);
                    listAdapter = new LisViewAdapter();
                    listview_orders.setAdapter(listAdapter);


                }


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
