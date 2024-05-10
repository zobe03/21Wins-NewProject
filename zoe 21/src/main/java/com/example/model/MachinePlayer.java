package com.example.model;

import java.util.Stack;

public final class MachinePlayer extends Player {
    private static int difficulty = 1; // Default difficulty level

    public static void setDifficulty(int newDifficulty) {
        difficulty = newDifficulty;
    }

    @Override
    public String getName() {
        return "Computer";
    }

    public static int makeMove(int currentRoundNr, Stack<Integer> currentStack) {
        int number;
        System.out.println("Difficulty: " + difficulty);
        switch (difficulty) {
            case 1, 2, 3:
                if (currentRoundNr < 4) {
                    number = (int) (Math.random() * 9 + 1);
                } else {
                    number = (int) (Math.random() * 10);
                }
                break;
            default:
                number = (int) (Math.random() * 10);
                break;
        }
        System.out.println("Number: " + number);
        return number;
    }
}