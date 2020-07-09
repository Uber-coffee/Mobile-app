package com.example.ubercoffee;


import java.util.ArrayList;

public class Drinkables {

    private String nameDrinkables;

    // Image name (Without extension)
    private ArrayList<Double> sizeDrinkables;
    private double priceDrinkables;

    public Drinkables(String nameDrinkables, ArrayList<Double> sizeDrinkables, double priceDrinkables) {
        this.nameDrinkables= nameDrinkables;
        this.sizeDrinkables= sizeDrinkables;
        this.priceDrinkables= priceDrinkables;
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

    public double getPriceDrinkables() {
        return priceDrinkables;
    }

    public void setPriceDrinkables(double priceDrinkables) {
        this.priceDrinkables = priceDrinkables;
    }

    //@Override
    //public String toString()  {
    //    return this.countryName+" (Population: "+ this.population+")";
    //}

}
