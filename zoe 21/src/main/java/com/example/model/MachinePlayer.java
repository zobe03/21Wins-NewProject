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
            depth = 8;
        } else {
            depth = 4;
        }
        System.out.println("Depth: " + depth);
    }

    public static int makeMove(int currentRoundNr, Stack<Integer> currentStack) {

        // don't calculate scores in the first two rounds, play a random number instead
        if (currentRoundNr < 3) {
            return ((int) (Math.random() * 9 + 1));
        }

        // initialize current position as root-node for algorithm
        Position root = new Position(currentStack, currentRoundNr, true, -1);
        int highestPossibleScore = minimaxEvaluation(root, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

        // iterating over all possible next positions
        for (Position p : root.getPossibleNextPositions()) {
            // if position is among top-rated...
            if (p.getPositionScore() == highestPossibleScore) {
                // ...add the move that lead to it to list of best moves
                root.getHighestRatedMoves().add(p.getLastMoveMade());

                // the reason for this implementation (saving all the best moves to an instance of Position instead of inside this method)
                // is for a not-yet-implemented expansion where the AI saves the results of calculations to be reused and improve performance

                System.out.println("Move: " + p.getLastMoveMade() + " Eval: " + p.getPositionScore());
            }
        }
        System.out.println("Valid Moves: " + root.getHighestRatedMoves());

        if (!root.getHighestRatedMoves().isEmpty()) {
            return root.getHighestRatedMoves().get((int) (Math.random() * root.getHighestRatedMoves().size()));
        } else {
            System.out.println("Error: No move found. Returning Random.");
            return (int) (Math.random() * 9 + 1);
        }
    }

    public static int minimaxEvaluation(Position pos, int depth, int alphaPruning, int betaPruning, boolean maximizingPlayer) {
        // Position pos:                current game-state as position object
        // int depth:                   search depth in moves (2 moves = 1 round)
        // boolean maximizingPlayer:    whether the current player aims to maximize or minimize the position's evaluation
        //                               - P1 aims to maximize
        //                               - P2 aims to minimize (computer is always P2)

        int score = pos.getPositionScore();
        if (depth == 0 || score != 0) {
            // if maximum search depth is reached or an inevitable loss/win is found:
            // pass position's score upwards
            return score;
        } else {
            pos.simulatePossibleMoves();
        }

        // search tree for best possible path
        // iterate over all generated possible next positions
        // recursively call minimax until score is returned
        if (maximizingPlayer) {
            int scoreMax = Integer.MIN_VALUE;
            for (Position child : pos.getPossibleNextPositions()) {
                score = minimaxEvaluation(child, depth - 1, alphaPruning, betaPruning, false);
                scoreMax = Math.max(scoreMax, score);
                alphaPruning = Math.max(alphaPruning, score);
                if (betaPruning <= alphaPruning) {
                    break;
                }
            }
            return scoreMax;
        } else {
            int scoreMin = Integer.MAX_VALUE;
            for (Position child : pos.getPossibleNextPositions()) {
                score = minimaxEvaluation(child, depth - 1, alphaPruning, betaPruning, true);
                scoreMin = Math.min(scoreMin, score);
                betaPruning = Math.max(betaPruning, score);
                if (alphaPruning <= betaPruning) {
                    break;
                }
            }
            return scoreMin;
        }
    }
}
