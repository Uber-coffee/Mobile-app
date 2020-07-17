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
import android.widget.AdapterView;
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

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListOfShopsActivity extends AppCompatActivity {

    private  ArrayList<CoffeeMarket> coffeeMarkets = new ArrayList<>();
    private  ArrayList<CoffeeMarket> coffeeMarkets_copy;
    private ImageButton imageButton;
    private Button buttonProfile;
    private Button buttonShops;
    private String phone;

    String answer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        test_with_base();


        phone = getIntent().getStringExtra("phone_number");




        buttonShops = findViewById(R.id.listOfShops);
        buttonProfile = findViewById(R.id.profile);

        buttonShops.setAlpha(1f);
        buttonProfile.setAlpha(0.7f);

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //buttonShops.setAlpha(0.7f);
                //buttonProfile.setAlpha(1f);
                Intent intentProfile = new Intent(ListOfShopsActivity.this, ProfileActivity.class);
                intentProfile.putExtra("phone_number", phone);
                startActivity(intentProfile);
            }
        });

        imageButton = (ImageButton) findViewById(R.id.filters);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOfShopsActivity.this, Pop.class);
                startActivityForResult(intent,0);
            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //buttonShops.setAlpha(0.7f);
                //buttonProfile.setAlpha(1f);
                Intent intentProfile = new Intent(ListOfShopsActivity.this, ProfileActivity.class);
                intentProfile.putExtra("phone_number", phone);
                startActivity(intentProfile);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            ListOfShopsActivity.this.recreate();
        }
    }

    private ArrayList<CoffeeMarket> initShopsList() throws IOException {

        ArrayList<CoffeeMarket> coffeeMarkets = new ArrayList<>();

        ParseShopsJson p = new ParseShopsJson();

        coffeeMarkets = (ArrayList<CoffeeMarket>) p.getCoffeeMarkets(p.parse_json(ListOfShopsActivity.this, answer));

        String [] urls = getResources().getStringArray(R.array.images_references);

        int i = 0;
        int size = 3;
        for (CoffeeMarket market : coffeeMarkets) {
            market.setUrl_photo(urls[i]);
            market.setFullness(i % size + 1);
            i++;
        }

       /* for(int i = 0; i < 10; i++){
            double dist = 0.2*(i+1);
            dist*=10;
            int res = (int) Math.round(dist);
            dist = (double)res/10;
            CoffeeMarket coffeeMarket = CoffeeMarket.initMarket(new String("test_address" + (i + 1)),100, dist, urls[i], randVal());
            coffeeMarkets.add(coffeeMarket);
        }*/

        return coffeeMarkets;
    }

    private void apply_filters(int distance, int fullness_green, int fullness_red, int fullness_yellow){
        ArrayList<CoffeeMarket> sorted_markets = new ArrayList<>();

        double dist = distance*0.5;

        if(distance != 0){
            for(int i = 0; i < coffeeMarkets_copy.size(); i++){
                if(coffeeMarkets_copy.get(i).retDistance() <= dist){
                    sorted_markets.add(coffeeMarkets_copy.get(i));
                }
            }
        }

        if(fullness_green != 0 || fullness_red != 0 || fullness_yellow != 0){
            if(sorted_markets.size() != 0){
                for(int i = 0; i < sorted_markets.size(); i++){
                    if(sorted_markets.get(i).retFullness() == fullness_green || sorted_markets.get(i).retFullness() == fullness_red || sorted_markets.get(i).retFullness() == fullness_yellow){
                        coffeeMarkets.add(sorted_markets.get(i));
                    }
                }
            }else{
                for(int i = 0; i < coffeeMarkets_copy.size(); i++){
                    if(coffeeMarkets_copy.get(i).retFullness() == fullness_green || coffeeMarkets_copy.get(i).retFullness() == fullness_red || coffeeMarkets_copy.get(i).retFullness() == fullness_yellow){
                        coffeeMarkets.add(coffeeMarkets_copy.get(i));
                    }
                }
            }
        }else{
            coffeeMarkets = sorted_markets;
        }
    }

    private void test_with_base(){
        String str = " ";
        SharedPreferences sp = getSharedPreferences("forAuthorization",MODE_PRIVATE);
        if(sp.contains("Authorization")){
            str = sp.getString("Authorization"," ");
        }

        Retrofit retrofit = ApiClient.getClientForTrade(str);

        Json service = retrofit.create(Json.class);
        Call<ResponseBody> call = service.getTrade(22.164,33.264);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.code());
                if(response.isSuccessful()){
                    try {
                        answer = new String(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(answer);

                        try {
                            coffeeMarkets_copy = initShopsList();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        SharedPreferences sharedPreferences = getSharedPreferences("for.filters", MODE_PRIVATE);
                        if(sharedPreferences.contains("Distance")){
                            if(sharedPreferences.getInt("Distance", 100) != 0 || sharedPreferences.getInt("Fullness_green", 100) != 0 || sharedPreferences.getInt("Fullness_red", 100) != 0 || sharedPreferences.getInt("Fullness_yellow", 100) != 0){
                                apply_filters(sharedPreferences.getInt("Distance", 100), sharedPreferences.getInt("Fullness_green", 100), sharedPreferences.getInt("Fullness_red", 100), sharedPreferences.getInt("Fullness_yellow", 100));
                            }else{
                                coffeeMarkets = coffeeMarkets_copy;
                            }
                        }else{
                            coffeeMarkets = coffeeMarkets_copy;
                        }

                    ShopsAdapter sadapter = new ShopsAdapter(ListOfShopsActivity.this);
                    ListView lv = (ListView) findViewById(R.id.lvMain);
                    lv.setAdapter(sadapter);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ListOfShopsActivity.this, MenuListActivity.class);
                            intent.putExtra("phone_number", phone);
                            startActivity(intent);
                        }
                    });

                    System.out.println(answer);
                }else{
                    System.out.println(response.code() + " NOT GOoD");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("BAD");
            }
        });
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
            cost.setText("from " + coffeeMarkets.get(position).retCost() + " ₽");

            TextView time = (TextView) convertView.findViewById(R.id.time);
            String time_out = "> " + coffeeMarkets.get(position).retTime() + " min";
            time.setText(time_out);

            TextView address = (TextView)convertView.findViewById(R.id.adress);
            address.setText(coffeeMarkets.get(position).retAddress());

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
