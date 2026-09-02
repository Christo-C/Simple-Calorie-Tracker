package com.example.simplecalorietracker;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;


public class AddFoodFragment extends DialogFragment {
    private Food food;

    static AddFoodFragment newInstance(Food food){
        Bundle args = new Bundle();
        args.putSerializable("food", food);

        AddFoodFragment fragment = new AddFoodFragment();
        fragment.setArguments(args);
        return fragment;
    }

    interface AddFoodDialogListener {
        void addFood(Food food);

        void editFood(Food food, String foodName, int caloriesAmt);

        void deleteFood(Food food);
    }

    private AddFoodDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddFoodDialogListener) {
            listener = (AddFoodDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddFoodDialogListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_food, null);
        EditText editFoodName = view.findViewById(R.id.edit_text_food_text);
        EditText editCaloriesAmt = view.findViewById(R.id.edit_text_calories_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments() != null){
            food = (Food) getArguments().getSerializable("food");
            editFoodName.setText(food.getName());
            editCaloriesAmt.setText(Integer.toString(food.getCalories()));
        }

        return builder
                .setView(view)
                .setTitle("Add/Edit Food")
                .setNegativeButton("Cancel", null)
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.deleteFood(food);
                    }
                })
                .setPositiveButton("OK", (dialog, which) -> {
                    String foodName = editFoodName.getText().toString();
                    String caloriesAmt = editCaloriesAmt.getText().toString();
                    if (caloriesAmt.isBlank()){
                        caloriesAmt = "0";
                    }
                    if (foodName.isBlank()) {
                        dialog.dismiss();
                    }
                    else if (food == null){
                        listener.addFood(new Food(foodName, Integer.parseInt(caloriesAmt)));
                    }
                    else{
                        listener.editFood(food, foodName, Integer.parseInt(caloriesAmt));
                    }
                })
                .create();
    }
}