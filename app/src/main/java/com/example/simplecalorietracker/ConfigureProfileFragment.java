package com.example.simplecalorietracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConfigureProfileFragment extends DialogFragment {
    private Profile profile;

    static ConfigureProfileFragment newInstance(Profile profile){
        Bundle args = new Bundle();
        args.putSerializable("profile", profile);

        ConfigureProfileFragment fragment = new ConfigureProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    interface ConfigureProfileDialogListener {
        void configureProfile(Profile profile, int age, boolean gender, int feet, int inches, int weight, ActivityLevel activityLevel, WeightLossGoal wlg);

        void createProfile(Profile profile);
    }

    private ConfigureProfileDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ConfigureProfileDialogListener) {
            listener = (ConfigureProfileDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement ConfigureProfileDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_configure_profile, null);
        EditText editAge = view.findViewById(R.id.editText_age);
        EditText editWeight = view.findViewById(R.id.editText_weight);
        EditText editHeightFeet = view.findViewById(R.id.editText_feet);
        EditText editHeightInches = view.findViewById(R.id.editText_inches);
        RadioGroup genderRadioGroup = view.findViewById(R.id.radioGroup);
        Spinner activityLevel = view.findViewById(R.id.spinner_activity_level);
        Spinner weightLossGoal = view.findViewById(R.id.spinner_goal);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // handle spinners
        ActivityLevel[] activityLevels = ActivityLevel.values();
        ArrayAdapter<ActivityLevel> ActivityAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                activityLevels
        );
        ActivityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevel.setAdapter(ActivityAdapter);

        WeightLossGoal[] weightLossGoals = WeightLossGoal.values();
        ArrayAdapter<WeightLossGoal> wlgAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                weightLossGoals
        );
        wlgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightLossGoal.setAdapter(wlgAdapter);

        profile = (Profile) getArguments().getSerializable("profile");
        if (profile != null){
            editAge.setText(Integer.toString(profile.getAge()));
            editWeight.setText(Integer.toString(profile.getWeight()));
            editHeightFeet.setText(Integer.toString(profile.getWeight()));
            editHeightInches.setText(Integer.toString(profile.getInches()));
            if (profile.isGender()){
                genderRadioGroup.check(R.id.radioButton_male);
            }
            else{
                genderRadioGroup.check(R.id.radioButton_female);
            }
            activityLevel.setSelection(ActivityAdapter.getPosition(profile.getActivityLevel()));
            weightLossGoal.setSelection(wlgAdapter.getPosition(profile.getWlg()));
        }

        return builder
                .setView(view)
                .setTitle("Configure Profile")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String age = editAge.getText().toString();
                    String weight = editWeight.getText().toString();
                    String feet = editHeightFeet.getText().toString();
                    String inches = editHeightInches.getText().toString();
                    boolean gender;
                    int selectedId = genderRadioGroup.getCheckedRadioButtonId();
                    gender = selectedId == R.id.radioButton_male;
                    ActivityLevel selectedActivityLevel = (ActivityLevel) activityLevel.getSelectedItem();
                    WeightLossGoal selectedWLG = (WeightLossGoal) weightLossGoal.getSelectedItem();

                    if (profile == null){
                        listener.createProfile(new Profile(Integer.parseInt(age), gender,
                                Integer.parseInt(feet), Integer.parseInt(inches), Integer.parseInt(weight),
                                selectedActivityLevel, selectedWLG));
                    }
                    else{
                        listener.configureProfile(profile, Integer.parseInt(age), gender,
                                Integer.parseInt(feet), Integer.parseInt(inches), Integer.parseInt(weight),
                                selectedActivityLevel, selectedWLG);
                    }
                })
                .create();
    }

}
