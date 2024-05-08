package model;

import java.util.Stack;

public abstract class Player {
    protected String name;
    public abstract int makeMove(int currentRoundNr, Stack<Integer> currentStack);
    public abstract String getName();

}