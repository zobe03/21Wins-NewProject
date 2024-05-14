package com.example.model.leaderboard;

public class StaticLeaderboard {
    private static LeaderBoard leaderBoard;

    // Initializes static leaderboard and loads current data from default file
    public static void init() {
        leaderBoard = new LeaderBoard();
        leaderBoard.initializeFileManager();
    }

    public static LeaderBoard get() {
        return leaderBoard;
    }

}
