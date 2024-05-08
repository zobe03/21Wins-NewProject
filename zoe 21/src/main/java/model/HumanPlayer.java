package model;

import java.util.Stack;

// es können keine weiteren Unterklassen erstellt werden
public final class HumanPlayer extends Player {
    private static int currentID = 1;
    private final String name;
    //private String username;

    public HumanPlayer(String name) { // new Player is being created
        super();
        this.name = name;
        currentID++;
        //this.username = this.playerID+"-"+this.name; // username for leaderboard (or optional)
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int makeMove(int currentRoundNr, Stack<Integer> currentStack) {
        // No move needed for HumanPlayer
        return 0;
    }
    //public String getUsername(){return this.username;}

}

