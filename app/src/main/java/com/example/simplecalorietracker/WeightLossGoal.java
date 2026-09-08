package com.example.simplecalorietracker;

public enum WeightLossGoal {
    MAINTAIN(0),
    MILD(250),
    MEDIUM(500),
    EXTREME(1000);

    private final int goal;

    WeightLossGoal(int goal){
        this.goal = goal;
    }

    public int getGoal(){return goal;}
}
