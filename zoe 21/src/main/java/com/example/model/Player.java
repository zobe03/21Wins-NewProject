package com.example.model;

import com.example.model.leaderboard.ScoreTracker;

public abstract class Player {
    protected String name;
    protected ScoreTracker scoreTracker;

    public abstract String getName();
    public ScoreTracker getScoreTracker() {
        return scoreTracker;
    }

}
