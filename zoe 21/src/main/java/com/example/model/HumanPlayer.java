package com.example.model;

import java.util.Stack;

// es können keine weiteren Unterklassen erstellt werden
public final class HumanPlayer extends Player {
    private static int currentID = 1;
    private final String name;
    public HumanPlayer(String name) {
        super();
        this.name = name;
        currentID++;
    }
    @Override
    public String getName() {
        return name;
    }

    public static int makeMove(int currentRoundNr, Stack<Integer> currentStack) {
        return 0;
    }
}

