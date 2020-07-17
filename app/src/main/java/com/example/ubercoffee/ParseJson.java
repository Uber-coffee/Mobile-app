package com.example.ubercoffee;

import android.app.Activity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ParseJson {
    static class Drink{
        public String name;
        public int price;
        public List<Ingredients> recipe;
        public double volume;
    }
    static class Ingredients{
        public String measure;
        public String name;
        public int quantity;
    }

    public List<Drink> parse_json(Activity activity) throws IOException
    {
        InputStream is = activity.getAssets().open("coffee.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        String text = new String(buffer);

        Gson g = new Gson();
        Type type = new TypeToken<List<Drink>>(){}.getType();
        return g.fromJson(text, type);
    }

    public Drinkables getItemDrinkables(List<Drink> drinks, String coffee_name) {
        ArrayList<Double> sizes = new ArrayList<>();
        ArrayList<Double> prices = new ArrayList<>();
        HashMap<String, Integer> recipes = new HashMap<>();
        for (int i = 0; i < drinks.size(); i++) {
            String current_name = drinks.get(i).name;
            if (coffee_name.equals(current_name)) {
                sizes.add(drinks.get(i).volume);
                prices.add(Double.parseDouble(String.valueOf(drinks.get(i).price)));
                List<Ingredients> ingredients = drinks.get(i).recipe;
                for (Ingredients ingredient_group : ingredients) {
                    recipes.put(ingredient_group.name, ingredient_group.quantity);
                }
            }
        }
        Collections.sort(sizes);
        Collections.sort(prices);
        return new Drinkables(coffee_name, sizes, prices, recipes);
    }

    public List<Drinkables> getDrinkables(List<Drink> drinks) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Drinkables> drink_items = new ArrayList<>();
        for (int i = 0; i < drinks.size(); i++) {
            String name = drinks.get(i).name;
            if (!names.contains(name))
                names.add(name);
        }
        for (String current_name : names) {
            drink_items.add(getItemDrinkables(drinks, current_name));
        }
        return drink_items;
    }

}
