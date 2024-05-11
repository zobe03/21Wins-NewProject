package com.example.model;

import java.util.Stack;

public final class MachinePlayer extends Player {
    @Override
    public String getName() {
        return "Computer";
    }

    private static int depth;
    public static void setDifficulty(int difficulty) {
        System.out.println("Difficulty: " + difficulty);
        if (difficulty == 1) {
            depth = 2;
        } else if (difficulty == 2) {
            depth = 4;
        } else if (difficulty == 3) {
            depth = 6;
        } else {
            depth = 4;
        }
        System.out.println("Depth: " + depth);
    }

    public static int makeMove(int currentRoundNr, Stack<Integer> currentStack) {

        if (currentRoundNr < 3) {
            return ((int) (Math.random() * 9 + 1));
        }

        Position root = new Position(currentStack, currentRoundNr, true, -1);
        int bestEval = minimaxEvaluation(root, depth, false);

        for (Position child : root.getChildren()) {
            if (child.getPositionEvaluation() == bestEval) {
                root.getValidMoves().add(child.getMoveMade());
                System.out.println("Move: " + child.getMoveMade() + " Eval: " + child.getPositionEvaluation());
                System.out.println("Valid Moves: " + root.getValidMoves());
            }
        }


        if (!root.getValidMoves().isEmpty()) {
            return root.getValidMoves().get((int) (Math.random() * root.getValidMoves().size()));
        } else {
            System.out.println("Error: No move found. Returning Random.");
            return (int) (Math.random() * 9 + 1);
        }
    }

    public static int minimaxEvaluation(Position pos, int depth, boolean maximizingPlayer) {
        // Position pos:                current game-state as position object
        // int depth:                   search depth in moves (2 moves = 1 round)
        // boolean maximizingPlayer:    whether the current player aims to maximize or minimize the position's evaluation
        //                               - P1 aims to maximize
        //                               - P2 aims to minimize (computer is always P2)

        int eval = pos.getPositionEvaluation();
        if (depth == 0 || eval != 0) {
            return eval;
        } else {
            pos.generateChildren();
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Position child : pos.getChildren()) {
                eval = minimaxEvaluation(child, depth - 1, false);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Position child : pos.getChildren()) {
                eval = minimaxEvaluation(child, depth - 1, true);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }
}
