package com.example.ubercoffee;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MenuListActivity extends AppCompatActivity {

    Button buttonPopular;
    Button buttonCoffee;
    Button buttonTea;
    Button buttonFilters;
    double price = 75;
    double time = 10;
    double distance = 0.5;


    GridView listView;
    ArrayAdapter<String> adapter;

    // определяем массив типа String
    final String[] catnames = new String[] { "Рыжик", "Барсик", "Мурзик",
            "Мурка", "Васька", "Томасина", "Кристина", "Пушок", "Дымка",
            "Кузя", "Китти", "Масяня", "Симба" };

    List<String> list = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        buttonPopular = findViewById(R.id.popular);
        buttonCoffee = findViewById(R.id.coffee);
        buttonTea = findViewById(R.id.tea);
        buttonFilters = findViewById(R.id.filters);


        Typeface typeface = ResourcesCompat.getFont(this, R.font.roboto_bold);
        //TypefaceSpan type = new TypefaceSpan(typeface);

        TextView tvAdress= (TextView) findViewById(R.id.adress);
        //передаем строкой адрес
        final SpannableString textAdress=  new SpannableString("Nevskiy Prospect, 2");
        //textAdress.setSpan( type, 0, textAdress.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE );
        //textAdress.se
        tvAdress.setText(textAdress);

        TextView tvB1= (TextView) findViewById(R.id.b1);
        //передаем строкой адрес
        SpannableString textB1=  new SpannableString("from " + price + "₽");
        //textB1.setSpan( type, 0, textB1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE );
        tvB1.setText(textB1);

        TextView tvB3= (TextView) findViewById(R.id.b3);
        //передаем строкой адрес
        SpannableString textB3=  new SpannableString("> " + time + " minutes");
        //textB3.setSpan( type, 0, textB3.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE );
        tvB3.setText(textB3);

        TextView tvB4= (TextView) findViewById(R.id.b4);
        //передаем строкой адрес
        SpannableString textB4=  new SpannableString(distance + " km");
        //textB4.setSpan( type, 0, textB4.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE );
        tvB4.setText(textB4);


        TextView tvButton= (TextView) findViewById(R.id.textButton);
        //передаем строкой адрес
        final SpannableString textButton=  new SpannableString("Popular");
        //textButton.setSpan( type, 0, textButton.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE );
        tvButton.setText(textButton);




        List<Drinkables> image_details = getListDataPopular();
        final GridView gridView = (GridView) findViewById(R.id.gridList);
        gridView.setAdapter(new MenuAdapter(this, image_details));

        // When the user clicks on the GridItem
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                Drinkables drinkables = (Drinkables) o;
                Toast.makeText(MenuListActivity.this, "Selected :"
                        + " " + drinkables, Toast.LENGTH_SHORT).show();
            }
        });



        buttonFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MenuFilterActivity.class);
                startActivity(i);
            }
        });


        buttonPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //список популярных Адаптер?
                Context context = view.getContext();

                Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_bold);
                //TypefaceSpan type = new TypefaceSpan(typeface);

                TextView tvButton= (TextView) findViewById(R.id.textButton);
                final SpannableString textButton=  new SpannableString("Popular");
                //textButton.setSpan( type, 0, textButton.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE );
                tvButton.setText(textButton);

                List<Drinkables> image_details = getListDataPopular();
                final GridView gridView = (GridView) findViewById(R.id.gridList);
                gridView.setAdapter(new MenuAdapter(context, image_details));

                // When the user clicks on the GridItem
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
                Context context = view.getContext();

                Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_bold);
                //TypefaceSpan type = new TypefaceSpan(typeface);

                TextView tvButton= (TextView) findViewById(R.id.textButton);
                final SpannableString textButton=  new SpannableString("Coffee");
                //textButton.setSpan( type, 0, textButton.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE );
                tvButton.setText(textButton);


                List<Drinkables> image_details = getListDataCoffee();
                final GridView gridView = (GridView) findViewById(R.id.gridList);
                gridView.setAdapter(new MenuAdapter(context, image_details));

                // When the user clicks on the GridItem
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
                Context context = view.getContext();

                Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_bold);
                //TypefaceSpan type = new TypefaceSpan(typeface);

                TextView tvButton= (TextView) findViewById(R.id.textButton);
                final SpannableString textButton=  new SpannableString("Tea");
                //textButton.setSpan( type, 0, textButton.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE );
                tvButton.setText(textButton);


                List<Drinkables> image_details = getListDataTea();
                final GridView gridView = (GridView) findViewById(R.id.gridList);
                gridView.setAdapter(new MenuAdapter(context, image_details));

                // When the user clicks on the GridItem
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

    private  List<Drinkables> getListDataPopular() {
        buttonCoffee.setBackground(this.getResources().getDrawable(R.drawable.rounded_button));
        buttonTea.setBackground(this.getResources().getDrawable(R.drawable.rounded_button));
        buttonPopular.setBackground(this.getResources().getDrawable(R.drawable.rounded_new_button));
        List<Drinkables> list = new ArrayList<Drinkables>();
        ArrayList<Double> size = new ArrayList<>();
        size.add(0.2);
        size.add(0.4);
        size.add(0.5);
        Drinkables Cappuccino = new Drinkables("Cappuccino", size, 120);
        Drinkables Latte = new Drinkables("Latte", size, 140);
        Drinkables Macchiato = new Drinkables("Macchiato", size, 230);
        Drinkables Expresso = new Drinkables("Espresso", size, 180);
        Drinkables Americano = new Drinkables("Americano", size, 100);

        list.add(Cappuccino);
        list.add(Latte);
        list.add(Macchiato);
        list.add(Expresso);
        list.add(Americano);

        return list;
    }

    private  List<Drinkables> getListDataCoffee() {
        buttonPopular.setBackground(this.getResources().getDrawable(R.drawable.rounded_button));
        buttonTea.setBackground(this.getResources().getDrawable(R.drawable.rounded_button));
        buttonCoffee.setBackground(this.getResources().getDrawable(R.drawable.rounded_new_button));
        List<Drinkables> list = new ArrayList<Drinkables>();
        ArrayList<Double> size = new ArrayList<>();
        size.add(0.2);
        size.add(0.4);
        size.add(0.5);
        Drinkables Glace = new Drinkables("Glace", size, 120);
        Drinkables Raf = new Drinkables("Raf", size, 140);
        Drinkables Frappe = new Drinkables("Frappe", size, 230);
        Drinkables Cappuccino = new Drinkables("Cappuccino", size, 120);
        Drinkables Latte = new Drinkables("Latte", size, 140);
        Drinkables Macchiato = new Drinkables("Macchiato", size, 230);
        Drinkables Expresso = new Drinkables("Espresso", size, 180);
        Drinkables Americano = new Drinkables("Americano", size, 100);

        list.add(Glace);
        list.add(Raf);
        list.add(Frappe);
        list.add(Cappuccino);
        list.add(Latte);
        list.add(Macchiato);
        list.add(Expresso);
        list.add(Americano);

        return list;
    }

    private  List<Drinkables> getListDataTea() {
        buttonPopular.setBackground(this.getResources().getDrawable(R.drawable.rounded_button));
        buttonCoffee.setBackground(this.getResources().getDrawable(R.drawable.rounded_button));
        buttonTea.setBackground(this.getResources().getDrawable(R.drawable.rounded_new_button));
        List<Drinkables> list = new ArrayList<Drinkables>();
        ArrayList<Double> size = new ArrayList<>();
        size.add(0.2);
        size.add(0.4);
        size.add(0.5);
        Drinkables DarkTea = new Drinkables("Dark Tea", size, 120);
        Drinkables GreenTea = new Drinkables("Green Tea", size, 140);


        list.add(DarkTea);
        list.add(GreenTea);

        return list;
    }


    private void setUpList() {
        for (String item:catnames)
            list.add(item);

    }
}