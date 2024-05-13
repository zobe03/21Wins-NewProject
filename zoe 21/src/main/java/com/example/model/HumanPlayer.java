package com.example.model;


import com.example.model.leaderboard.ScoreTracker;

// es können keine weiteren Unterklassen erstellt werden
public final class HumanPlayer extends Player {
    private static int currentID = 1;
    private final String name;
    public HumanPlayer(String name) {
        super();
        this.name = name;
        currentID++;
        this.scoreTracker = new ScoreTracker();
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public ScoreTracker getScoreTracker() {
        return scoreTracker;

    }

}

