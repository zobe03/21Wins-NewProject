package com.example.model.leaderboard;

public class StaticLeaderBoard {
    private static LeaderBoard leaderBoard;
    public static void initialize() {
        leaderBoard = new LeaderBoard();
        leaderBoard.initializeFileManager();
    }

    public static LeaderBoard getLeaderBoard() {
        return leaderBoard;
    }
}
