package com.example.simplecalorietracker;

import java.io.Serializable;

public class Profile implements Serializable {
    private int age;
    private boolean gender;
    private int feet;
    private int inches;
    private int weight;
    private ActivityLevel activityLevel;

    public Profile(int age, boolean gender, int feet, int inches, int weight, ActivityLevel activityLevel) {
        this.age = age;
        this.gender = gender;
        this.feet = feet;
        this.inches = inches;
        this.weight = weight;
        this.activityLevel = activityLevel;
    }

    public double getHeightInCm() {
        return (feet * 30.48) + (inches * 2.54);
    }

    public double calculateBMR() {
        if (gender){
            return 10 * weight + 6.25 * getHeightInCm() - 5 * age + 5;
        }
        else{
            return 10 * weight + 6.25 * getHeightInCm() - 5 * age - 161;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getFeet() {
        return feet;
    }

    public void setFeet(int feet) {
        this.feet = feet;
    }

    public int getInches() {
        return inches;
    }

    public void setInches(int inches) {
        this.inches = inches;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }
}
