package com.example.model.leaderboard;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {

    public static final int MAX_SIZE = 10;
    public static final String DEFAULT_FILE_NAME = "leaderboard.csv";

    // Constructs a LeaderBoard object from a csv string
    public static LeaderBoard fromString(String s) {
        LeaderBoard leaderBoard = new LeaderBoard();
        String[] parts = s.split("\n");
        for (String part : parts) {
            leaderBoard.addItem(LeaderBoardItem.fromString(part));
        }
        return leaderBoard;
    }

    private List<LeaderBoardItem> items;
    private LeaderBoardFileManager fileManager;

    public LeaderBoard() {
        this.items = new ArrayList<LeaderBoardItem>(MAX_SIZE + 1);
        this.fileManager = null;
    }

    // Initializes the fileManager for this leaderBoard and attempts to load the data from given filename
    public void initializeFileManager(String filename) {
        fileManager = new LeaderBoardFileManager(filename, this);
        fileManager.load();
    }

    // Initialize with default filename
    public void initializeFileManager() {
        initializeFileManager(DEFAULT_FILE_NAME);
    }

    public void addItem(LeaderBoardItem item) {
        items.add(item);
        sortLimitAndSave();
    }

    public void setItems(List<LeaderBoardItem> items) {
        this.items = items;
        sortLimitAndSave();
    }

    public List<LeaderBoardItem> getItems() {
        return items;
    }

    // Sorts the items based on score, removes the last item if the list is too long and saves the data if possible
    private void sortLimitAndSave() {
        sort();
        removeLast();
        if(fileManager != null) {
            fileManager.save();
        }
    }

    // Sorts the items based on score
    private void sort() {
        items.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
    }

    // Removes the last item, if the list is too long
    private void removeLast() {
        if (items.size() > MAX_SIZE) {
            items.remove(items.size() - 1);
        }
    }

    // Parses the LeaderBoard to a csv string (multyline)
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        items.forEach(builder::append);
        return builder.toString();
    }

}

