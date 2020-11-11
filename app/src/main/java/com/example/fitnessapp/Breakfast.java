package com.example.fitnessapp;

public class Breakfast {

    public Breakfast(){

    }
    private static Double breakfastCalorie;
    private static String breakfastdescription;

public Breakfast(double bc, String bd){
    this.breakfastCalorie= bc;
    this.breakfastdescription= bd;

}
    public static double getBreakfastCalorie() {
        return breakfastCalorie;
    }

    public static void setBreakfastCalorie(double breakfastCal) {
        breakfastCalorie = breakfastCal;
    }

    public static String getBreakfastdescription() {
        return breakfastdescription;
    }

    public static void setBreakfastdescription(String breakfastdes) {
        breakfastdescription = breakfastdes;
    }
}
