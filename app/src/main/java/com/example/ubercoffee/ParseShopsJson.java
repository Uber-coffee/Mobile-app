package com.example.ubercoffee;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ParseShopsJson {
    static class CoffeeShop{
        public tradePoint tradePoint;
        public int time;
        public double distance;
    }
    static class tradePoint{
        public int id;
        public String address;
        public double latitude;
        public double longitude;
        public boolean isActive;
        public String[] users;
    }

    public List<CoffeeShop> parse_json(Activity activity) throws IOException
    {
        InputStream is = activity.getAssets().open("coffee_shops.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        String text = new String(buffer);

        Gson g = new Gson();
        Type type = new TypeToken<List<CoffeeShop>>(){}.getType();
        return g.fromJson(text, type);
    }

    List<CoffeeMarket> getCoffeeMarkets(List<CoffeeShop> shops) {
        ArrayList<CoffeeMarket> markets = new ArrayList<>();
        for (CoffeeShop shop : shops) {
            markets.add(new CoffeeMarket(shop.tradePoint.address + " ", 120, shop.distance, shop.time));
        }
        return markets;
    }
}
