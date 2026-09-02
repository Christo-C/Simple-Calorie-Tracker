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
        AddFoodFragment.AddFoodDialogListener {
    private ArrayList<Food> dataList;
    private FoodArrayAdapter foodAdapter;

    private TextView totalCalories;
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
        if (!dataList.isEmpty()){
            for (Food food: dataList){
                total += food.getCalories();
            }
            totalCalories.setText("Total Calories: "+total);
        }
        else{
            totalCalories.setText("Add a food to get started!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] foods = {"Water"};
        int[] calories = {0};

        dataList = new ArrayList<>();
        /*for (int i = 0; i < foods.length; i++) {
            dataList.add(new Food(foods[i], calories[i]));
        }*/
        ListView foodList = findViewById(R.id.food_list);
        foodAdapter = new FoodArrayAdapter(this, dataList);
        foodList.setAdapter(foodAdapter);

        totalCalories = findViewById(R.id.total_calories);

        Button addFoodButton = findViewById(R.id.add_food);
        addFoodButton.setOnClickListener(v -> {
            new AddFoodFragment().show(getSupportFragmentManager(), "Add Food");
        });

        Button clearFoodButton = findViewById(R.id.clear_food);
        clearFoodButton.setOnClickListener(v -> {
            dataList.clear();
            updateTotalCalories();
            foodAdapter.notifyDataSetChanged();
        });

        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AddFoodFragment.newInstance(dataList.get(i)).show(getSupportFragmentManager(), "Edit Food");
            }
        });
    }
}