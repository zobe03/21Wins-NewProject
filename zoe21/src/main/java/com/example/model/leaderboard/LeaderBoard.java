package com.example.model.leaderboard;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {

    public static final int MAX_SIZE = 10;
    public static final String DEFAULT_FILE_NAME = "leaderboard.csv";

    // Constructs a LeaderBoard object from a csv string
    public static LeaderBoard fromString(String s) {
        System.out.println("fromString" + s);
        LeaderBoard leaderBoard = new LeaderBoard();
        String[] parts = s.split("\n");
        System.out.println(parts);
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
        System.out.println("initializeFileManager" + filename);
        fileManager = new LeaderBoardFileManager(filename, this);
        System.out.println("initialize" + fileManager);
        fileManager.load();
    }

    // Initialize with default filename
    public void initializeFileManager() {
        System.out.println("initializeFileManager" + DEFAULT_FILE_NAME);
        initializeFileManager(DEFAULT_FILE_NAME);
    }

    public void addItem(LeaderBoardItem item) {
        System.out.println("addItem" + item);
        items.add(item);
        sortLimitAndSave();
    }

    public void setItems(List<LeaderBoardItem> items) {
        System.out.println("setItems" + items);
        this.items = items;
        sortLimitAndSave();
    }

    public List<LeaderBoardItem> getItems() {
        System.out.println("getItems" + items);
        return items;
    }

    // Sorts the items based on score, removes the last item if the list is too long and saves the data if possible
    private void sortLimitAndSave() {
        System.out.println("sortLimitAndSave" + items);
        sort();
        System.out.println("sortLimitAndSave" + items.size() + " von " + MAX_SIZE);
        removeLast();
        if(fileManager != null) {
            fileManager.save();
            System.out.println(" save " + fileManager + " items " + items);
        }
    }

    // Sorts the items based on score
    private void sort() {
        System.out.println("sort");
        items.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
    }

    // Removes the last item, if the list is too long
    private void removeLast() {
        System.out.println("removeLast");
        if (items.size() > MAX_SIZE) {
            items.removeLast();
        }
    }

    // Parses the LeaderBoard to a csv string (multyline)
    @Override
    public String toString() {
        System.out.println("toString" );
        StringBuilder builder = new StringBuilder();
        items.forEach(builder::append);
        return builder.toString();
    }

}

