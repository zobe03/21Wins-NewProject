package com.example.model.leaderboard;

public class LeaderBoardItem {
    // Multiplier to calculate score based on moves
    public static final int SCORE_MOVES_MULTIPLYER = 1000;

    // Constructs a LeaderBoardItem from a single CSV line -> Split the CSV line into parts
    // Create and return a new LeaderBoardItem using the extracted data
    public static LeaderBoardItem fromString(String s) {
        String[] parts = s.split(",");
        return new LeaderBoardItem(parts[0], Integer.parseInt(parts[1]), Long.parseLong(parts[2]));
    }


    private String name;
    // Number of moves made by the player
    private int moves;
    // Total time spent by the player
    private long time;

    // Constructor to initialize a LeaderBoardItem with name, moves, and time
    public LeaderBoardItem(String name, int moves, long time) {
        this.name = name;
        this.moves = moves;
        this.time = time;
        System.out.println("LeaderBoardItem angekommen: " + name + moves + time);
    }

    // Getter method for the player's name
    public String getName() {
        return name;
    }

    // Getter method for the number of moves made by the player
    public int getMoves() {
        return moves;
    }

    // Getter method for the total time spent by the player
    public long getTime() {
        return time;
    }

    // Calculate and return the score based on moves and time
    public double getScore() {
        return (double) (moves * SCORE_MOVES_MULTIPLYER) / time;
    }

    // Return the score as a formatted string with 2 decimal places
    public String getFormattedScore() {
        return String.format("%.2f", getScore());
    }

    // Convert the LeaderBoardItem to a CSV line
    @Override
    public String toString() {
        System.out.println("LeaderBoardItem toString: " + name + " " + moves + " " + time);
        return name + "," + moves + "," + time + "\n";
    }

}
