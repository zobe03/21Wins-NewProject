package com.example.model;


import com.example.model.leaderboard.ScoreTracker;

// HumanPlayer set to final so that it is impossible to create any subclasses
// HumanPlayer is a subclass of Player, as is MachinePlayer
public final class HumanPlayer extends Player {

    private final String name;

    public HumanPlayer(String name) {
        super();
        this.name = name;
        this.scoreTracker = new ScoreTracker(); // create a new ScoreTracker for the HumanPlayer object
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

