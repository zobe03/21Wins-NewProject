package com.example.model;

import java.util.ArrayList;
import java.util.Stack;

// von MachinePlayer können weitere Unterklassen mit unterschiedlichem Schwierigkeitsgrad/Eigenschaften erstellt werden (sonst final setzen)
public final class MachinePlayer extends Player {
    @Override
    public String getName() {
        return "Computer";
    }
    static int depth;
    public static void setDifficulty(int difficulty) {
        System.out.println("Difficulty: " + difficulty);
        if (difficulty == 1) {
            depth = 0;
            System.out.println("Depth: " + depth);
        } else if (difficulty == 2) {
            depth = 3;
            System.out.println("Depth: " + depth);
        } else if (difficulty == 3) {
            depth = 7;
            System.out.println("Depth: " + depth);
        } else {
            depth = 10;
            System.out.println("Depth: " + depth);
        }
    }

    public static int makeMove(int currentRoundNr, Stack<Integer> currentStack) {

        if (currentRoundNr < 3) {
            return ((int) (Math.random() * 9 + 1));
        }

        Position root = new Position(currentStack, currentRoundNr, true, -1);

        ArrayList<Integer> validMoves = new ArrayList<>();

        int bestEval = minimax(root, depth, true);
        for (Position child : root.getChildren()) {
            if (child.getEvaluation() == bestEval) {
                System.out.println("child.getMoveMade() = " + child.getMoveMade());
                validMoves.add(child.getMoveMade());
            }
        }

        root.setValidMoves(validMoves);
        if (!validMoves.isEmpty()) {
            return validMoves.get((int) (Math.random() * validMoves.size()));
        } else {
            System.out.println("Error: No move found. Returning Random.");
            return (int) (Math.random() * 9 + 1);
        }
    }

    public static int minimax(Position pos, int depth, boolean machinesMove) {

        int eval = pos.getEvaluation();
        if (depth == 0 || eval >= 1000 || eval <= -1000) {
            return eval;
        } else {
            pos.generateChildren();
        }

        if (machinesMove) {
            int maxEval = -1000;
            for (Position child : pos.getChildren()) {
                eval = minimax(child, depth - 1, false);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = 1000;
            for (Position child : pos.getChildren()) {
                eval = minimax(child, depth - 1, true);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }
}
