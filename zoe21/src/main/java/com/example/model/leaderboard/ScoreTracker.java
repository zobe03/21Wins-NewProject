
package com.example.model.leaderboard;

public class ScoreTracker {

    // Number of moves made by the player
    private int moves;

    // Total time spent by the player
    public long time;

    // Time when the player's turn started
    private long startTime;

    // Constructor to initialize the ScoreTracker
    public ScoreTracker() {
        reset();
    }

    // Method to reset the ScoreTracker, called when starting a new game or round
    public void reset() {
        moves = 0;
        time = 0;
        startTime = 0;
    }

    // Method to increment the number of moves made by the player
    public void incrementMoves() {
        // Check if the timer is running, indicating a valid move was made
        moves++;
    }

    // Method to start the timer when the player begins their turn
    public void startTimer() {
        // Check if the timer is already running
        if (isTimerRunning()) {
            throw new IllegalStateException("Timer was already started");
        }
        // Set the start time to the current system time
        startTime = System.currentTimeMillis();
    }

    // Method to stop the timer when the player ends their turn
    public void stopTimer() {
        // Check if the timer was started
        if (!isTimerRunning()) {
            throw new IllegalStateException("Timer was never started");
        }
        // Calculate the time elapsed during the turn and add it to the total time
        time += System.currentTimeMillis() - startTime;
        // Reset the start time
        startTime = 0;
    }

    // Method to check if the timer is currently running
    public boolean isTimerRunning() {
        return startTime != 0;
    }

    // Method to convert the ScoreTracker data to a LeaderBoardItem with the given player name
    public LeaderBoardItem toLeaderBoardItem(String name) {

        // Check if the required data is available
        if (moves == 0 || time == 0 || name == null || name.isEmpty()) {
            throw new IllegalStateException("A name, time, and amount of moves are required to convert to LeaderBoardItem");
        }
        System.out.println("toLeaderBoardItem" + name + " " + moves + " " + time);
        // Create and return a new LeaderBoardItem with the player's data
        return new LeaderBoardItem(name, moves, time);
    }

}
