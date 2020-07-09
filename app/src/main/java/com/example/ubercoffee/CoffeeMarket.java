package com.example.ubercoffee;

public class CoffeeMarket {

    private String marketAdress_;
    private int coffeeCost_;
    private double distanceToMarket_;
    private String url_photo_;
    private int fullness_;

    private CoffeeMarket( String marketAdress, int coffeeCost, double distanceToMarket,String url_photo, int fullness){
        marketAdress_ = marketAdress;
        coffeeCost_ = coffeeCost;
        distanceToMarket_ = distanceToMarket;
        url_photo_ = url_photo;
        fullness_ = fullness;
    }

    public static CoffeeMarket initMarket(String marketAdress, int coffeeCost, double distanceToMarket,String url_photo, int fullness){
        if(marketAdress.equals("") || coffeeCost < 0){
            return null;
        }else{
            return new CoffeeMarket(marketAdress,coffeeCost, distanceToMarket, url_photo, fullness);
        }
    }

    public String retAdress(){
        return marketAdress_;
    }

    public double retDistance(){
        return distanceToMarket_;
    }

    public int retCost(){
        return coffeeCost_;
    }

    public int retFullness(){
        return fullness_;
    }

    public String retUrl(){
        return url_photo_;
    }

}
