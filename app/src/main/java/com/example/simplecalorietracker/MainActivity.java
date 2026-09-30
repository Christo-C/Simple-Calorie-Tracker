package com.example.simplecalorietracker;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements
        AddFoodFragment.AddFoodDialogListener, ConfigureProfileFragment.ConfigureProfileDialogListener{
    private ArrayList<Food> dataList;
    private FoodArrayAdapter foodAdapter;

    private TextView totalCalories;
    private TextView remainingCalories;

    private Profile userProfile;

    @Override
    public void configureProfile(Profile profile, int age, boolean gender, int feet, int inches, int weight, ActivityLevel activityLevel, WeightLossGoal wlg) {
        userProfile.setAge(age);
        userProfile.setGender(gender);
        userProfile.setFeet(feet);
        userProfile.setInches(inches);
        userProfile.setWeight(weight);
        userProfile.setActivityLevel(activityLevel);
        userProfile.setWlg(wlg);
        updateTotalCalories();
    }

    @Override
    public void createProfile(Profile profile) {
        userProfile = profile;
        updateTotalCalories();
    }

    @Override
    public void addFood(Food food) {
        foodAdapter.add(food);
        updateTotalCalories();
        foodAdapter.notifyDataSetChanged();
    }

    public void editFood(Food food, String name, int calories) {
        food.setName(name);
        food.setCalories(calories);
        updateTotalCalories();
        foodAdapter.notifyDataSetChanged();
    }

    public void deleteFood(Food food){
        foodAdapter.remove(food);
        updateTotalCalories();
        foodAdapter.notifyDataSetChanged();
    }

    public void updateTotalCalories(){
        int total = 0;
        double remaining = 0;
        if (!dataList.isEmpty()){
            for (Food food: dataList){
                total += food.getCalories();
            }
            totalCalories.setText("Total Calories: "+total);
            if (userProfile != null){
                remaining = userProfile.calculateTDEE() - total;
                remainingCalories.setText("Remaining Calories: "+String.format("%.0f", remaining));
            }
        }
        else{
            totalCalories.setText("Add a food to get started!");
            remainingCalories.setText("Create a profile to see remaining calories!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList = new ArrayList<>();
        ListView foodList = findViewById(R.id.food_list);
        foodAdapter = new FoodArrayAdapter(this, dataList);
        foodList.setAdapter(foodAdapter);

        totalCalories = findViewById(R.id.total_calories);
        remainingCalories = findViewById(R.id.remaining_calories);

        Button addFoodButton = findViewById(R.id.add_food);
        addFoodButton.setOnClickListener(v -> {
            new AddFoodFragment().show(getSupportFragmentManager(), "Add Food");
        });

        Button clearFoodButton = findViewById(R.id.clear_food);
        clearFoodButton.setOnClickListener(v -> {
            dataList.clear();
            foodAdapter.notifyDataSetChanged();
            updateTotalCalories();
        });

        Button configureProfileButton = findViewById(R.id.profile);
        configureProfileButton.setOnClickListener(v -> {
            ConfigureProfileFragment.newInstance(userProfile).show(getSupportFragmentManager(), "Configure Profile");
        });

        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AddFoodFragment.newInstance(dataList.get(i)).show(getSupportFragmentManager(), "Edit Food");
            }
        });
    }
}