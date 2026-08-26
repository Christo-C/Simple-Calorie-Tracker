package com.example.simplecalorietracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FoodArrayAdapter extends ArrayAdapter<Food> {
    public FoodArrayAdapter(Context context, ArrayList<Food> foods) {super(context, 0, foods);}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.content,
                    parent, false);
        } else {
            view = convertView;
        }
        Food food = getItem(position);
        TextView foodName = view.findViewById(R.id.food_text);
        TextView calories = view.findViewById(R.id.calories_text);
        foodName.setText(food.getName());
        calories.setText(Integer.toString(food.getCalories()));
        return view;
    }
}
