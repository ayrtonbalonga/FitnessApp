package com.example.fitnessapp;

public class Profile {



    private  static double Height;
    private  static double Weight;
    private  static  String Sex;
    private  static  String DOB;

    public static double getHeight() {
        return Height;
    }

    public static void setHeight(double height) {
        Height = height;
    }

    public static double getWeight() {
        return Weight;
    }

    public static void setWeight(double weight) {
        Weight = weight;
    }


    public static String getSex() {
        return Sex;
    }

    public static void setSex(String sex) {
        Sex = sex;
    }

    public static String getDOB() {
        return DOB;
    }

    public static void setDOB(String dob) {
        DOB = dob;
    }
}
