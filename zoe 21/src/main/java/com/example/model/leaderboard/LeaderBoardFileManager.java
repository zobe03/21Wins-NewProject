package com.example.model.leaderboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LeaderBoardFileManager {

    private Path path;
    private LeaderBoard leaderBoard;

    public LeaderBoardFileManager(String filename, LeaderBoard leaderBoard) {
        this.path = Path.of(filename);
        this.leaderBoard = leaderBoard;
    }

    public Path getPath() {
        return path;
    }

    public void save() {
        try {
            Files.writeString(path, leaderBoard.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            String data = Files.readString(path);
            LeaderBoard newLeaderBoard = LeaderBoard.fromString(data);
            leaderBoard.setItems(newLeaderBoard.getItems());
        } catch (Exception  e) {
            // If the file can not be found, or contains invalid data, ignore it quietly and continue with an empty leaderboard
        }
    }

}
