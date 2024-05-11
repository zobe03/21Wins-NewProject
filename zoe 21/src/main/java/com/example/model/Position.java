package com.example.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Position {

    private final Stack<Integer> currentStack;
    private final int roundNumber;
    private final boolean isMaximizingPlayer;
    private final int lastMoveMade;
    private final int positionScore;

    public int getLastMoveMade() {
        return lastMoveMade;
    }
    public int getPositionScore() {
        return positionScore;
    }
    
    public Position(Stack<Integer> currentStack, int roundNr, boolean activePlayerIsMachine, int lastMoveMade) {
        this.currentStack = currentStack;
        this.roundNumber = roundNr;
        this.isMaximizingPlayer = activePlayerIsMachine;
        this.lastMoveMade = lastMoveMade; // move made (by opponent) leading to this position
        this.positionScore = calculateScore_heuristic(currentStack, !activePlayerIsMachine);
    }

    // calculates score of current position
    // if it inevitably/heuristically-predictably leads to a win/loss:
    //  - MAX/MIN if game is already in end-position
    //  - +/-1000 if game will end after one move
    //  - +/-500 if game will end after two moves
    // positive numbers indicate the maximizing player has advantage (and the opposite)
    // if no heuristic evaluation is possible, return 0 as a neutral rating
    private int calculateScore_heuristic(Stack<Integer> s, boolean max) {

        if (s.isEmpty()) return 0; // check if stack is empty, return 0 as neutral score if so
        int top = s.peek(); // since stack has been verified as not empty, get stack's top number

        if (top >= 21) {
            // check if position is already final (already >= 21)
            return max ? 1 : -1;
        }

        if (s.size() >= 2 && this.roundNumber >= 4) {
            int sum = top + s.get(s.size() - 2); // since stack has at least 2 elements and is at least round 4, create sum from top two numbers

            if (sum >= 21) {
                // win by combining the two top elements to sum
                return max ? 1 : -1;
            }

            // check if stack is full
            if (s.size() >= 6) {
                if (top >= 12) {
                    // win by adding large enough number on top
                    return max ? 1 : -1;
                }
                if (top == 11) {
                    // cannot prevent enemy from winning on their next turn
                    return max ? -1 : 1;
                }
                if (sum == 20) {
                    // cannot prevent enemy from winning on their next turn
                    return max ? -1 : 1;
                }
            }
        }
        // otherwise return 0 (neutral rating)
        return 0;
    }

    private final ArrayList<Integer> validMoves = new ArrayList<>();
    private final Set<Position> children = new HashSet<>();

    public ArrayList<Integer> getHighestRatedMoves() {
        return validMoves;
    }
    public Set<Position> getPossibleNextPositions() {
        return children;
    }


    public void simulatePossibleMoves() {
        // if current player is not maximizing player (if it is player two) increment the round number for next position
        int newRoundNr = this.isMaximizingPlayer ? this.roundNumber + 1: this.roundNumber;

        for (int move = 0; move < 10; move++) {
            // clone current stack before making changes
            Stack<Integer> newStack = (Stack<Integer>) this.currentStack.clone();

            // simulate changes to stack depending on move made
            if (move == 0 && newStack.size() > 1 && this.roundNumber >= 4) {
                newStack.push(newStack.pop() + newStack.pop());
                Position child = new Position(newStack, newRoundNr, !this.isMaximizingPlayer, move);
                children.add(child);

            } else if (move >= 1) {
                if (newStack.size() == 6) {
                    int stackNr1 = newStack.pop();
                    newStack.push(stackNr1 + move);
                } else if (!newStack.isEmpty() && newStack.size() < 6) {
                    newStack.push(move);
                } // else { continue; }

            Position child = new Position(newStack, newRoundNr, !this.isMaximizingPlayer, move);
            children.add(child);

            }
        }
    }
}
