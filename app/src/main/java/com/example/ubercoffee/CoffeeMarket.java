package com.example.ubercoffee;

public class CoffeeMarket {

    private String marketAddress_;
    private int coffeeCost_;
    private double distanceToMarket_;
    private int time_;
    private String url_photo_;
    private int fullness_;

    public CoffeeMarket(String marketAddress, int coffeeCost, double distanceToMarket, int time){
        marketAddress_ = marketAddress;
        coffeeCost_ = coffeeCost;
        distanceToMarket_ = distanceToMarket;
        time_ = time;
    }

    public CoffeeMarket(String marketAddress, int coffeeCost, double distanceToMarket, int time, String url_photo, int fullness){
        marketAddress_ = marketAddress;
        coffeeCost_ = coffeeCost;
        distanceToMarket_ = distanceToMarket;
        url_photo_ = url_photo;
        fullness_ = fullness;
        time_ = time;
    }

    public static CoffeeMarket initMarket(String marketAddress, int coffeeCost, double distanceToMarket, int time, String url_photo, int fullness){
        if(marketAddress.equals("") || coffeeCost < 0){
            return null;
        }else{
            return new CoffeeMarket(marketAddress, coffeeCost, distanceToMarket, time, url_photo, fullness);
        }
    }

    public void setUrl_photo(String url) {
        url_photo_ = url;
    }

    public void setFullness(int fullness) {
        fullness_ = fullness;
    }

    public String retAddress(){
        return marketAddress_;
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

    public int retTime() { return time_; }

}
