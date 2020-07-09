package com.example.ubercoffee;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ListOfShopsActivity extends AppCompatActivity {

    private  ArrayList<CoffeeMarket> coffeeMarkets = new ArrayList<>();
    private  ArrayList<CoffeeMarket> coffeeMarkets_copy;
    private ImageButton imageButton;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        coffeeMarkets_copy = initShopsList();

        ShopsAdapter sadapter = new ShopsAdapter(this);

        SharedPreferences sharedPreferences = getSharedPreferences("for.filters", MODE_PRIVATE);
        if(sharedPreferences.contains("Distance")){
            if(sharedPreferences.getInt("Distance", 100) != 0 || sharedPreferences.getInt("Fullness", 100) != 0){
                apply_filters(sharedPreferences.getInt("Distance", 100), sharedPreferences.getInt("Fullness", 100));
            }else{
                coffeeMarkets = coffeeMarkets_copy;
            }
        }else{
            coffeeMarkets = coffeeMarkets_copy;
        }



        imageButton = (ImageButton) findViewById(R.id.filters);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOfShopsActivity.this, Pop.class);
                startActivityForResult(intent,0);
            }
        });

        ListView lv = (ListView) findViewById(R.id.lvMain);
        lv.setAdapter(sadapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            ListOfShopsActivity.this.recreate();
        }
    }

    private ArrayList<CoffeeMarket> initShopsList(){

        ArrayList<CoffeeMarket> coffeeMarkets = new ArrayList<>();

        String [] urls = getResources().getStringArray(R.array.images_references);

        for(int i = 0; i < 5; i++){
            CoffeeMarket coffeeMarket = CoffeeMarket.initMarket(new String("test_address" + (i + 1)),100, 0.5*(i+1), urls[i], randVal());
            coffeeMarkets.add(coffeeMarket);
        }

        return coffeeMarkets;
    }

    private void apply_filters(int distance, int fullness){
        ArrayList<CoffeeMarket> sorted_markets = new ArrayList<>();

        double dist = distance*0.5;

        if(distance != 0){
            for(int i = 0; i < coffeeMarkets_copy.size(); i++){
                if(coffeeMarkets_copy.get(i).retDistance() <= dist){
                    sorted_markets.add(coffeeMarkets_copy.get(i));
                }
            }
        }

        if(fullness != 0){
            if(sorted_markets.size() != 0){
                for(int i = 0; i < sorted_markets.size(); i++){
                    if(sorted_markets.get(i).retFullness() == fullness){
                        coffeeMarkets.add(sorted_markets.get(i));
                    }
                }
            }else{
                for(int i = 0; i < coffeeMarkets_copy.size(); i++){
                    if(coffeeMarkets_copy.get(i).retFullness() == fullness){
                        coffeeMarkets.add(coffeeMarkets_copy.get(i));
                    }
                }
            }
        }else{
            coffeeMarkets = sorted_markets;
        }
    }

    class ShopsAdapter extends BaseAdapter {
        LayoutInflater mLayoutInflater;
        ShopsAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return coffeeMarkets.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.shop_item, null);

            ImageView imgView = (ImageView)convertView.findViewById(R.id.just_photo);

            CardView cv = (CardView) convertView.findViewById(R.id.cv);

            Glide.with(ListOfShopsActivity.this)
                    .load(coffeeMarkets.get(position).retUrl())
                    .placeholder(R.drawable.dark_logo)
                    .error(R.drawable.dark_logo)
                    .apply(new RequestOptions().override(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT))
                   .into(imgView);


            TextView distance = (TextView) convertView.findViewById(R.id.distance);
            distance.setText(String.valueOf(coffeeMarkets.get(position).retDistance()) + " km");

            TextView cost = (TextView) convertView.findViewById(R.id.cost);
            cost.setText("from " + coffeeMarkets.get(position).retCost());

            TextView time = (TextView) convertView.findViewById(R.id.time);
            time.setText("> 6 min");

            TextView adress = (TextView)convertView.findViewById(R.id.adress);
            adress.setText(coffeeMarkets.get(position).retAdress());

            TextView fullness = (TextView) convertView.findViewById(R.id.fullness);

            switch (coffeeMarkets.get(position).retFullness()){
                case 1:
                    fullness.setBackgroundResource(R.drawable.drawable_circle_green);
                    break;
                case 2:
                    fullness.setBackgroundResource(R.drawable.drawable_circle_yellow);
                    break;
                case 3:
                    fullness.setBackgroundResource(R.drawable.drawable_circle_red);
                    break;
            }

            return convertView;
        }
    }

    public int randVal(){
        int beg = 1;
        int end = 3;
        return beg + (int)(Math.random()*end);
    }
}
