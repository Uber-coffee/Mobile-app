package com.example.ubercoffee;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuListActivity extends AppCompatActivity {

    private Button buttonPopular;
    private Button buttonCoffee;
    private Button buttonTea;
    private ImageButton buttonFilters;
    private ImageButton buttonOrder;
    private RelativeLayout shapka;
    private double price = 75;
    private double time = 10;
    private double distance = 0.5;
    private int sizeDrinkable = 0;
    private String windowMenu = "Popular";
    private int topPrice = 230;

    public static final String APP_PREFERENCES = "PreferencesFilter";
    public static final String APP_PREFERENCES_ORDER = "PreferencesOrder";
    public static final String APP_PREFERENCES_NAME = "Name";
    public static final String APP_PREFERENCES_SIZE = "Size";
    public static final String APP_PREFERENCES_TYPE = "Type";
    public static final String APP_PREFERENCES_TOP_PRICE = "TopPrice";

    private SharedPreferences PreferencesFilter;

    private GridView gridView;

    private TextView tvAdress;
    private TextView tvB1;
    private TextView tvB3;
    private TextView tvB4;
    private TextView tvButton;

    private String phone;

    List<Drinkables> drink_first = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        phone = getIntent().getStringExtra("phone_number");

        buttonPopular = findViewById(R.id.popular);
        buttonCoffee = findViewById(R.id.coffee);
        buttonTea = findViewById(R.id.tea);
        buttonFilters = findViewById(R.id.filters);
        buttonOrder = findViewById(R.id.order);
        shapka = findViewById(R.id.shapka);
        shapka.setAlpha(1f);

        tvAdress = findViewById(R.id.adress);
        //передаем строкой адрес
        final SpannableString textAdress=  new SpannableString("Nevskiy Prospect, 2");
        tvAdress.setText(textAdress);

        tvB1 = findViewById(R.id.b1);
        //передаем строкой цену
        SpannableString textB1=  new SpannableString("from " + price + "₽");
        tvB1.setText(textB1);

        tvB3 = findViewById(R.id.b3);
        //передаем строкой количество минут
        SpannableString textB3=  new SpannableString("> " + time + " minutes");
        tvB3.setText(textB3);

        tvB4 = findViewById(R.id.b4);
        //передаем строкой расстояние
        SpannableString textB4=  new SpannableString(distance + " km");
        tvB4.setText(textB4);

        PreferencesFilter = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        if(PreferencesFilter.contains(APP_PREFERENCES_SIZE)){
            sizeDrinkable = PreferencesFilter.getInt(APP_PREFERENCES_SIZE, 0);
        }
        if(PreferencesFilter.contains(APP_PREFERENCES_TYPE)){
            windowMenu = PreferencesFilter.getString(APP_PREFERENCES_TYPE, "Popular");
        }
        if(PreferencesFilter.contains(APP_PREFERENCES_TOP_PRICE)){
            topPrice = PreferencesFilter.getInt(APP_PREFERENCES_TOP_PRICE, 230);
        }

        tvButton = findViewById(R.id.textButton);
        //передаем строкой в какой менюшке
        final SpannableString textButton=  new SpannableString(windowMenu);
        tvButton.setText(textButton);

        //заполнение сетки данными (в нашем случае сначала популярными)
        gridView = findViewById(R.id.gridList);

        ParseJson p = new ParseJson();
        List<ParseJson.Drink> list_first = null;
        try {
            list_first = p.parse_json(MenuListActivity.this);
            drink_first = p.getDrinkables(list_first);
        } catch (IOException e) {
            e.printStackTrace();
        }


        GridLoad(gridView, drink_first);

        // When the user clicks on the GridItem
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                Drinkables drinkables = (Drinkables) o;

                Intent intent = new Intent(v.getContext(), CompleteInfoAboutDrink.class);
                intent.putExtra("drink", drinkables);
                startActivityForResult(intent, 0);
                overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
            }
        });

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                intent.putExtra("phone_number", phone);
                startActivity(intent);
            }
        });


        buttonFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuFilterActivity.class);
                SharedPreferences.Editor editor = PreferencesFilter.edit();
                editor.clear().apply();
                editor.putInt(APP_PREFERENCES_SIZE, sizeDrinkable);
                editor.putString(APP_PREFERENCES_TYPE, windowMenu);
                editor.putInt(APP_PREFERENCES_TOP_PRICE, topPrice);
                editor.apply();
                //intent.putExtra("type", windowMenu);
                shapka.setAlpha(0.5f);
                startActivityForResult(intent, 0);
            }
        });

        buttonPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                windowMenu = "Popular";
                final SpannableString textButton=  new SpannableString(windowMenu);
                tvButton.setText(textButton);

                GridLoad(gridView, drink_first);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        Object o = gridView.getItemAtPosition(position);
                        Drinkables drinkables = (Drinkables) o;
                        Toast.makeText(MenuListActivity.this, "Selected :"
                                + " " + drinkables, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        buttonCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                windowMenu = "Coffee";
                final SpannableString textButton=  new SpannableString(windowMenu);
                tvButton.setText(textButton);

                GridLoad(gridView, drink_first);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        Object o = gridView.getItemAtPosition(position);
                        Drinkables drinkables = (Drinkables) o;
                        Toast.makeText(MenuListActivity.this, "Selected :"
                                + " " + drinkables, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        buttonTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                windowMenu = "Tea";
                final SpannableString textButton=  new SpannableString(windowMenu);
                tvButton.setText(textButton);

                GridLoad(gridView, drink_first);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        Object o = gridView.getItemAtPosition(position);
                        Drinkables drinkables = (Drinkables) o;
                        Toast.makeText(MenuListActivity.this, "Selected :"
                                + " " + drinkables, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            MenuListActivity.this.recreate();
        }
    }

    private void GridLoad(GridView gridView, List<Drinkables> drink_first){
        List<Drinkables> details = new ArrayList<>();
        if (windowMenu.equals("Popular"))
            details = getListDataPopular(sizeDrinkable, topPrice, drink_first);
        if (windowMenu.equals("Coffee"))
            details = getListDataCoffee(sizeDrinkable, topPrice, drink_first);
        if (windowMenu.equals("Tea"))
            details = getListDataTea(sizeDrinkable, topPrice, drink_first);
        gridView.setAdapter(new MenuAdapter(this, details));
    }

    private List<Drinkables> getSortedData(int sizeDrinkable, int topPrice, List<Drinkables> drink_first){
        List<Drinkables> list = new ArrayList<>();
        for (int i = 0; i < drink_first.size(); i++){
            int flag = 0;
            Drinkables drink = drink_first.get(i);
            int countSize = drink.getCountSize();
            switch (sizeDrinkable){
                case 0:
                    flag = 0;
                    break;
                case 1:
                    if (countSize < sizeDrinkable)
                        flag = 1;
                    else{
                        if (drink.getSizeDrinkables().get(sizeDrinkable-1) != 0.2)
                            flag = 1;
                    }
                    break;
                case 2:
                    if (countSize < sizeDrinkable)
                        flag = 1;
                    else{
                        if (drink.getSizeDrinkables().get(sizeDrinkable-1) != 0.4)
                            flag = 1;
                    }
                    break;
                case 3:
                    if (countSize < sizeDrinkable)
                        flag = 1;
                    else{
                        if (drink.getSizeDrinkables().get(sizeDrinkable-1) != 0.5)
                            flag = 1;
                    }
                    break;
            }
            if (flag == 0){
                double price = drink.getPriceDrinkables().get(0);
                if (topPrice >= price) {
                    list.add(drink);
                }
            }
        }
        return list;
    }

    private  List<Drinkables> getListDataPopular(int sizeDrinkable, int topPrice, List<Drinkables> drink_first) {
        buttonCoffee.setBackground(this.getResources().getDrawable(R.drawable.rounded_button));
        buttonTea.setBackground(this.getResources().getDrawable(R.drawable.rounded_button));
        buttonPopular.setBackground(this.getResources().getDrawable(R.drawable.rounded_new_button));
        return getSortedData(sizeDrinkable, topPrice, drink_first);
    }

    private  List<Drinkables> getListDataCoffee(int sizeDrinkable, int topPrice, List<Drinkables> drink_first) {
        buttonPopular.setBackground(this.getResources().getDrawable(R.drawable.rounded_button));
        buttonTea.setBackground(this.getResources().getDrawable(R.drawable.rounded_button));
        buttonCoffee.setBackground(this.getResources().getDrawable(R.drawable.rounded_new_button));
        return getSortedData(sizeDrinkable, topPrice, drink_first);
    }

    private  List<Drinkables> getListDataTea(int sizeDrinkable, int topPrice, List<Drinkables> drink_first) {
        buttonPopular.setBackground(this.getResources().getDrawable(R.drawable.rounded_button));
        buttonCoffee.setBackground(this.getResources().getDrawable(R.drawable.rounded_button));
        buttonTea.setBackground(this.getResources().getDrawable(R.drawable.rounded_new_button));
        return getSortedData(sizeDrinkable, topPrice, drink_first);
    }
}