package com.example.ubercoffee;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Drinkables implements Serializable {

    private String nameDrinkables;

    // Image name (Without extension)
    private ArrayList<Double> sizeDrinkables;
    private ArrayList<Double> priceDrinkables;
    private HashMap<String, Integer> recipes_ = null;


    public Drinkables(String nameDrinkables, ArrayList<Double> sizeDrinkables, ArrayList<Double> priceDrinkables) {
        this.nameDrinkables= nameDrinkables;
        this.sizeDrinkables= sizeDrinkables;
        this.priceDrinkables= priceDrinkables;
    }

    public Drinkables(String nameDrinkables, ArrayList<Double> sizeDrinkables, ArrayList<Double> priceDrinkables,
                      HashMap<String, Integer> recipes) {
        this.nameDrinkables= nameDrinkables;
        this.sizeDrinkables= sizeDrinkables;
        this.priceDrinkables= priceDrinkables;
        this.recipes_ = recipes;
    }

    public String getNameDrinkables() {
        return nameDrinkables;
    }

    public void setNameDrinkables(String nameDrinkables) {
        this.nameDrinkables = nameDrinkables;
    }

    public ArrayList<Double> getSizeDrinkables() {
        return sizeDrinkables;
    }

    public int getCountSize() {
        return sizeDrinkables.size();
    }

    public void setSizeDrinkables(ArrayList<Double> sizeDrinkables) {
        this.sizeDrinkables = sizeDrinkables;
    }

    public ArrayList<Double> getPriceDrinkables() {
        return priceDrinkables;
    }

    public void setPriceDrinkables(ArrayList<Double> priceDrinkables) {
        this.priceDrinkables = priceDrinkables;
    }

    public HashMap<String, Integer> getRecipesDrinkables() {
        return this.recipes_;
    }

}


