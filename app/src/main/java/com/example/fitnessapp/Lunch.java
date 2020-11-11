package com.example.fitnessapp;

public class Lunch {

    public Lunch(){

    }
    private static String LunchCalorie;
    private   static String Lunchdescription;



    public static String getLunchCalorie() {
        return LunchCalorie;
    }

    public static void setLunchCalorie(String lCal) {
        LunchCalorie = lCal;
    }

    public static String getLunchdescription()
    {
        return Lunchdescription;
    }

    public static void setLunchdescription(String lDes) {
        Lunchdescription = lDes;
    }
}
