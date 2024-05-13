package com.example.model.leaderboard;

public class ScoreTracker {

    private int moves;
    public long time;
    private long startTime;

    public ScoreTracker() {
        reset();
    }

    public void reset() {
        moves = 0;
        time = 0;
        startTime = 0;
    }

    public void incrementMoves() {
         // Überprüfen, ob der Timer läuft, d.h. ob ein gültiger Zug gemacht wurde
            moves++;

    }

    // Should be called when player begins turn
    public void startTimer() {
        if(isTimerRunning()) {
            throw new IllegalStateException("Timer was already started");
        }
        startTime = System.currentTimeMillis();
    }

    // Should be called when player ends turn
    public void stopTimer() {
        if(!isTimerRunning()) {
            throw new IllegalStateException("Timer was never started");
        }
        time += System.currentTimeMillis() - startTime;
        startTime = 0;
    }

    public boolean isTimerRunning() {
        return startTime != 0;
    }

    // Converts the ScoreTracker to a LeaderBoardItem with given player name
    public LeaderBoardItem toLeaderBoardItem(String name) {
        if(moves == 0 || time == 0 || name == null || name.isEmpty()) {
            throw new IllegalStateException("A name, time and amount of moves is required to convert to LeaderBoardItem");
        }
        return new LeaderBoardItem(name, moves, time);
    }

}
