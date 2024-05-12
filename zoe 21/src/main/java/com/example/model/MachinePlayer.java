package com.example.model;

import java.util.Stack;

// THIS CODE WAS WRITTEN WITH EXPANDABILITY IN MIND
// IT CAN BE SIMPLIFIED MUCH MORE AND BE DESIGNED MORE ELEGANTLY AT THIS STAGE
// HOWEVER, THE WAY IT IS WRITTEN IS FULLY FUNCTIONAL AND LEAVES SPACE FOR LARGER IMPROVEMENTS TO BE IMPLEMENTED EASIER

public final class MachinePlayer extends Player {
    @Override
    public String getName() {
        return "Computer";
    }

    private static int depth;
    private static int difficulty;
    public static void setDifficulty(int diff) {
        System.out.println("Difficulty: " + diff);

        // easy (1) & medium (2) AIs don't simulate moves, only check for 'instant winning' moves
        // the depth is only set for diff 3, which is the only one that actually searches.
        if (diff == 1) {
            difficulty = diff;
            depth = 0;
        } else if (diff == 2) {
            difficulty = diff;
            depth = 0;
        } else if (diff == 3) {
            difficulty = diff;
            depth = 11;
        } else {
            // "Easter-Egg"-Difficulty if choosing a difficulty is skipped - literally unbeatable AI
            // depth is set to 0 since this AI is based on a specific strategy whether than looking ahead
            difficulty = 4;
            depth = 0;
            System.out.println("\n\n !!! ACTIVATED UNBEATABLE MATH-MODE !!! \n\n");
        }
    }

    public static int makeMove(int currentRoundNr, Stack<Integer> currentStack) {



        // instant reactions
        if (currentRoundNr <= 3) {

            if (difficulty == 4) {
                // round 3, on the second player's (the AI's) turn, '1' is the strongest move - therefore the unbeatable AI plays it always
                return 1;
            }

            // the other AI's just all play random moves in the first three rounds
            return (int) (Math.random() * 9 + 1);

        } else if (difficulty == 1) {

            // easy ai (1) has a 70% chance of just outputting a random number and therefore missing a win.
            if ((int) (Math.random() * 100) <= 70) {
                return (int) (Math.random() * 10);
            }


        } else if (currentStack.size() >= 2) {

            // if a win/loss is detected inevitably in the next move/round
            int top = currentStack.peek();
            // since stack has at least 2 elements and is at least round 4, create sum from top two numbers
            int sum = top + currentStack.get(currentStack.size() - 2);

            if (sum >= 21) {
                // win by combining the two top elements to sum
                return 0;
            }

            // check if stack is full
            if (currentStack.size() >= 6) {
                if (top >= 12) {
                    // win by adding large enough number on top to exactly reach 21
                    return 21 - top;
                }

                if (top == 11 || sum == 20) {
                    // cannot prevent enemy from winning on their next turn, so just return random
                    // (in order to skip searching possible positions and save resources)
                    return (int) (Math.random() * 10);
                }
            }

        }

        // (only for "Hard" AI (difficulty 3)
        // if game is yet undetermined, search possible paths (using minimax-algorithm with alpha-beta branch-pruning)
        // initialize current position as root-node for algorithm ('lastMoveMade' is passed -1 since just as a filler, this variable is not relevant for the root Position)
        Position root = new Position(currentStack, currentRoundNr, true, -1);
        // minimax is called from the root position, with 'alpha = -infinity/min', 'beta = infinity/max' and 'maximizingPlayer = true' (since its called from MachinePlayer's perspective)
        int highestPositionScore = minimaxAlphaBeta(root, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        // note: depth is always either 0 or 11. there are, of course, many more elegantly seeming solutions here at first
        // (like defaulting depth to 11 and only letting Hard AI call it) but most of this code was written with expandability in mind.

        // iterating over all possible next positions
        for (Position p : root.getPossibleNextPositions()) {
            // if position is among top-rated...
            if (p.getPositionScore() == highestPositionScore) {
                // ...add the move that lead to it to list of best moves
                root.getHighestRatedMoves().add(p.getLastMoveMade());

                // the reason for this implementation (saving all the best moves to an instance of Position instead of inside this method)
                // is for a not-yet-implemented idea where the AI saves the results of calculations to be reused and improve performance
            }
        }

        // return one of the moves among the list of best ones randomly
        if (!root.getHighestRatedMoves().isEmpty()) {
            return root.getHighestRatedMoves().get((int) (Math.random() * root.getHighestRatedMoves().size()));
        } else {
            System.out.println("Error: No move found. Returning Random.");
            return (int) (Math.random() * 9 + 1);
        }
    }

    public static int minimaxAlphaBeta(Position pos, int depth, int alpha, int beta, boolean maximizingPlayer) {
        // Position pos:                current game-state as instance of Position-Class
        // int depth:                   search depth in moves (2 moves = 1 round)
        // boolean maximizingPlayer:    whether the current player aims to maximize or minimize the position's evaluation
        //                               - Player 2, the AI, always assumes itself to be the maximizing player

        int score = pos.getPositionScore();
        if (depth == 0 || score != 0) {
            // if maximum search depth is reached or an inevitable loss/win is found:
            // --> pass position's score upwards
            return score;
        } else {
            pos.simulatePossibleMoves();
        }

        // search tree for best possible path
        if (maximizingPlayer) {
            // if its MachinePlayer's turn;
            int scoreMax = Integer.MIN_VALUE;
            // iterate over all generated possible next positions
            for (Position child : pos.getPossibleNextPositions()) {
                // recursively call minimax until score is returned
                score = minimaxAlphaBeta(child, depth - 1, alpha, beta, false);
                // check whether it is higher than the previous maximum or alpha-value
                scoreMax = Math.max(scoreMax, score);
                alpha = Math.max(alpha, score);
                // determine whether tree-branch is worth exploring (if it even can contain better outcomes for us)
                if (beta <= alpha) {
                    break;
                }
            }
            return scoreMax;
        } else {
            // same as above, but for Player's turns
            int scoreMin = Integer.MAX_VALUE;
            for (Position child : pos.getPossibleNextPositions()) {
                score = minimaxAlphaBeta(child, depth - 1, alpha, beta, true);
                scoreMin = Math.min(scoreMin, score);
                beta = Math.min(beta, score);
                if (beta <= alpha) {
                    break;
                }
            }
            return scoreMin;
        }
    }
}
