package com.example.ubercoffee;


import android.widget.ImageButton;

import java.util.ArrayList;

public class Order {

    private String nameDrinkables;
    private int countDrinkables;
    private double sizeDrinkables;
    private double priceDrinkables;

    public Order(String nameDrinkables, double sizeDrinkables, int countDrinkables, double priceDrinkables) {
        this.nameDrinkables= nameDrinkables;
        this.sizeDrinkables= sizeDrinkables;
        this.countDrinkables= countDrinkables;
        this.priceDrinkables= priceDrinkables;
    }

    public String getNameDrinkables() {
        return nameDrinkables;
    }

    public void setNameDrinkables(String nameDrinkables) {
        this.nameDrinkables = nameDrinkables;
    }

    public double getSizeDrinkables() {
        return sizeDrinkables;
    }

    public int getCountDrinkables() {
        return countDrinkables;
    }

    public void setCountDrinkables(int countDrinkables) {
        this.countDrinkables = countDrinkables;
    }

    public void setSizeDrinkables(double sizeDrinkables) {
        this.sizeDrinkables = sizeDrinkables;
    }

    public double getPriceDrinkables() {
        return priceDrinkables;
    }

    public void setPriceDrinkables(double priceDrinkables) {
        this.priceDrinkables = priceDrinkables;
    }

}
