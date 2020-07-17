package com.example.ubercoffee;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.util.Map;


public class CompleteInfoAboutDrink extends AppCompatActivity {
    private Drinkables coffee_item;
    private Button addCart;
    private Button sizeS;
    private Button sizeM;
    private Button sizeL;
    private Button plus;
    private Button minus;
    private Button quantity;
    private TextView total_cost;
    private double selected_size;
    private double price;
    private boolean need_sugar = false;
    private DrinkAdapter mAdapter;

    public static final String APP_PREFERENCES = "Order";
    public static final String APP_PREFERENCES_NAME = "nameDrinkables";
    public static final String APP_PREFERENCES_QUANTITY = "countDrinkables";
    public static final String APP_PREFERENCES_SIZE = "sizeDrinkables";
    public static final String APP_PREFERENCES_PRICE = "priceDrinkables";
   //static SharedPreferences order_saving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        setContentView(R.layout.activity_complete_info);
        SharedPreferences order_saving = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        if (arguments != null) {
             coffee_item = (Drinkables)arguments.getSerializable("drink");
        }
        ImageView imgView = (ImageView)this.findViewById(R.id.image_drink);
        Glide.with(CompleteInfoAboutDrink.this)
                .load(this.getResources().getStringArray(R.array.images_references)[2])
                .error(R.drawable.dark_logo)
                .apply(new RequestOptions().override(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT))
                .into(imgView);
        TextView textView = (TextView)this.findViewById(R.id.name_of_drink);
        textView.setText(coffee_item.getNameDrinkables());
        selected_size = coffee_item.getSizeDrinkables().get(0);
        price = coffee_item.getPriceDrinkables().get(0);

        addCart = (Button)this.findViewById(R.id.button_total);
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences order_saving = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = order_saving.edit();
                Order myObject = new Order(coffee_item.getNameDrinkables(), selected_size, Integer.parseInt(quantity.getText().toString()), price);
                Gson gson = new Gson();
                String json = gson.toJson(myObject);
                String key = coffee_item.getNameDrinkables() + selected_size + price;
                editor.putString(key, json);
                editor.commit();
                Map<String, ?> allEntries = order_saving.getAll();
                setResult(RESULT_OK);
                finish();
            }
        });


        mAdapter = new DrinkAdapter(CompleteInfoAboutDrink.this);
        ListView listView = (ListView)findViewById(R.id.listIngredients);
        listView.setAdapter(mAdapter);

        SwitchCompat switch_comp = (SwitchCompat)this.findViewById(R.id.switch_compat);
        if (switch_comp != null) {
            switch_comp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                           if (isChecked) {
                                                               need_sugar = true;

                                                           } else {
                                                               need_sugar = false;
                                                           }
                                                       }
            });
        }


        sizeS = (Button)this.findViewById(R.id.buttonS);
        sizeM = (Button)this.findViewById(R.id.buttonM);
        sizeL = (Button)this.findViewById(R.id.buttonL);
        sizeS.setText(coffee_item.getSizeDrinkables().get(0).toString());
        sizeM.setText(coffee_item.getSizeDrinkables().get(1).toString());
        sizeL.setText(coffee_item.getSizeDrinkables().get(2).toString());
        minus = (Button)this.findViewById(R.id.button_minus);
        plus = (Button)this.findViewById(R.id.button_plus);
        quantity = (Button)this.findViewById(R.id.quantity);
        quantity.setText("1");
        total_cost = (TextView)this.findViewById(R.id.total_cost);
        String text = getResources().getString(R.string.Total) + coffee_item.getPriceDrinkables().get(0)  + "₽";
        total_cost.setText(text);
        sizeS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_size = Double.parseDouble(sizeS.getText().toString());
                sizeS.setBackground(getResources().getDrawable(R.drawable.rounded_filled_back));
                sizeM.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                sizeL.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                price = coffee_item.getPriceDrinkables().get(0);

                String new_total = getResources().getString(R.string.Total) + Integer.parseInt(quantity.getText().toString()) * price + "₽";
                total_cost.setText(new_total);
            }
        });
        sizeM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_size = Double.parseDouble(sizeM.getText().toString());
                sizeM.setBackground(getResources().getDrawable(R.drawable.rounded_filled_back));
                sizeS.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                sizeL.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                price = coffee_item.getPriceDrinkables().get(1);

                String new_total = getResources().getString(R.string.Total) + Integer.parseInt(quantity.getText().toString()) * price + "₽";
                total_cost.setText(new_total);
            }
        });
        sizeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_size = Double.parseDouble(sizeL.getText().toString());
                sizeL.setBackground(getResources().getDrawable(R.drawable.rounded_filled_back));
                sizeM.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                sizeS.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                price = coffee_item.getPriceDrinkables().get(2);;

                String new_total = getResources().getString(R.string.Total) + Integer.parseInt(quantity.getText().toString()) * price + "₽";
                total_cost.setText(new_total);
            }
        });



        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curr_quan = quantity.getText().toString();
                if (!curr_quan.equals("1")) {
                    int new_quan =  Integer.parseInt(curr_quan) - 1;
                    quantity.setText(String.valueOf(new_quan));

                    String new_total = getResources().getString(R.string.Total) + new_quan * price + "₽";
                    total_cost.setText(new_total);
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curr_quan = quantity.getText().toString();
                int new_quan =  Integer.parseInt(curr_quan) + 1;
                quantity.setText(String.valueOf(new_quan));

                String new_total = getResources().getString(R.string.Total) + new_quan * price + "₽";
                total_cost.setText(new_total);
            }
        });


    }



    class DrinkAdapter extends BaseAdapter {
        LayoutInflater mLayoutInflater;
        DrinkAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return coffee_item.getRecipesDrinkables().size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.drink_item, null);

            TextView ingred_name = (TextView)convertView.findViewById(R.id.ingred_name);
            String name = (String)coffee_item.getRecipesDrinkables().keySet().toArray()[position];
            ingred_name.setText(name);
            TextView ingred_quan = (TextView)convertView.findViewById(R.id.ingred_quan);
            String text = (String)coffee_item.getRecipesDrinkables().values().toArray()[position].toString() + "%";
            ingred_quan.setText(text);
            return convertView;
        }
    }

}

