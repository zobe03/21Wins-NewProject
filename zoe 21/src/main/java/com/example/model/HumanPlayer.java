package com.example.model;

import com.example.model.leaderboard.ScoreTracker;

// es können keine weiteren Unterklassen erstellt werden
public final class HumanPlayer extends Player {
    private static int currentID = 1;
    private final String name;

    private final ScoreTracker scoreTracker;

    public HumanPlayer(String name) {
        super();
        this.name = name;
        currentID++;
        this.scoreTracker = new ScoreTracker();
    }

    public ScoreTracker getScoreTracker() {
        return scoreTracker;
    }

    @Override
    public String getName() {
        return name;
    }

}

