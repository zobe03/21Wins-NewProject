package com.example.model.leaderboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LeaderBoardFileManager {

    // Path to the file storing leaderboard data
    private Path path;
    // Reference to the LeaderBoard instance
    private LeaderBoard leaderBoard;

    // Constructor to initialize the LeaderBoardFileManager with a filename and LeaderBoard instance
    public LeaderBoardFileManager(String filename, LeaderBoard leaderBoard) {
        this.path = Path.of(filename);
        System.out.println("LeaderBoardFileManager" + path);
        this.leaderBoard = leaderBoard;
        System.out.println("LeaderBoardFileManager" + leaderBoard);
    }

    // Getter method for the file path
    public Path getPath() {
        return path;
    }

    // Save the leaderboard data to the file
    public void save() {
        System.out.println("LeaderBoardFileManager save");
        try {
            Files.writeString(path, leaderBoard.toString());
            System.out.println("LeaderBoardFileManager save" + leaderBoard.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load leaderboard data from the file
    public void load() {
        try {
            String data = Files.readString(path);
            LeaderBoard newLeaderBoard = LeaderBoard.fromString(data);
            leaderBoard.setItems(newLeaderBoard.getItems());
        } catch (Exception e) {
            System.out.println("Could not load leaderboard data");
        }
    }

}


