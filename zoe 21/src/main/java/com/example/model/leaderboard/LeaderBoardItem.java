package com.example.model.leaderboard;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LeaderBoardItem {
    public static final int SCORE_MOVES_MULTIPLYER = 1000;

    // Constructs a LeaderBoardItem from a single csv line
    public static LeaderBoardItem fromString(String s) {
        String[] parts = s.split(",");
        return new LeaderBoardItem(parts[0], Integer.parseInt(parts[1]), Long.parseLong(parts[2]));
    }

    private String name;
    private static int moves;
    private static long time;

    private IntegerProperty place;

    public LeaderBoardItem(String name, int moves, long time) {
        this.name = name;
        this.moves = moves;
        this.time = time;
    }

    public LeaderBoardItem(int place, String name, int moves, long time) {
        this.place = new SimpleIntegerProperty(place);
        this.name = name;
        this.moves = moves;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getMoves() {
        return moves;
    }

    public long getTime() {
        return time;
    }

    public static double getScore() {
        return (double) (moves * SCORE_MOVES_MULTIPLYER) / time;
    }

    // Return the score as a string with 2 decimal places
    public static String getFormatedScore() {
        return String.format("%.2f", getScore());
    }

    // Parses the LeaderBoardItem to a csv line
    @Override
    public String toString() {
        return name + "," + moves + "," + time + "\n";
    }

    public int getPlace() {
        return place.get();
    }

    public void setPlace(int place) {
        this.place.set(place);
    }

    public IntegerProperty placeProperty() {
        return place;
    }
}
