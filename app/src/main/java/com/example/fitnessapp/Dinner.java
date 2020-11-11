package com.example.fitnessapp;

public class Dinner {



    public Dinner(){

    }
    private static String DinnerCalorie;
    private static String Dinnerdescription;

        public Dinner(String dc,String dd){
            this.DinnerCalorie=dc;
            this.Dinnerdescription=dd;

        }

    public static String getDinnerCalorie() {
        return DinnerCalorie;
    }

    public static void setDinnerCalorie(String dCal) {
        DinnerCalorie = dCal;
    }

    public static String getDinnerdescription() {

        return Dinnerdescription;
    }

    public static void setDinnerdescription(String dDes) {
        Dinnerdescription = dDes;
    }
    
    
    
    
    
}
