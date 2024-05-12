package com.example.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

// THIS CODE WAS WRITTEN WITH EXPANDABILITY IN MIND
// IT CAN BE SIMPLIFIED MUCH MORE AND BE DESIGNED MORE ELEGANTLY AT THIS STAGE
// HOWEVER, THE WAY IT IS WRITTEN IS FULLY FUNCTIONAL AND LEAVES SPACE FOR LARGER IMPROVEMENTS TO BE IMPLEMENTED EASIER

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

    private final ArrayList<Integer> validMoves = new ArrayList<>();
    private final Set<Position> children = new HashSet<>();

    public ArrayList<Integer> getHighestRatedMoves() {
        return validMoves;
    }
    public Set<Position> getPossibleNextPositions() {
        return children;
    }

    public Position(Stack<Integer> currentStack, int roundNr, boolean activePlayerIsMachine, int lastMoveMade) {
        this.currentStack = currentStack;
        this.roundNumber = roundNr;
        this.isMaximizingPlayer = activePlayerIsMachine;
        this.lastMoveMade = lastMoveMade; // move made (by opponent) leading to this position
        this.positionScore = calculateScore_absolute(currentStack, activePlayerIsMachine);
    }

    // calculates score of current position if it inevitably/heuristically-predictably [i hope that even is a word] leads to a win/loss
    // positive numbers indicate the maximizing player has advantage (and the opposite)
    // if no heuristic evaluation is possible, return 0 as a neutral rating
    private int calculateScore_absolute(Stack<Integer> s, boolean isMaximizingPlayersTurn) {
        int top;
        if (!s.isEmpty()) {
            top = s.peek();
            if (top >= 21) {
                // check if position is already final (already >= 21)
                return isMaximizingPlayersTurn ? -1000 : 1000;
            }
            if (s.size() >= 2 && this.roundNumber >= 4) {
                // since stack has at least 2 elements and is at least round 4, create sum from top two numbers
                int sum = top + s.get(s.size() - 2);
                if (sum >= 21) {
                    // win by combining the two top elements to sum
                    return isMaximizingPlayersTurn ? 100 : -100;
                }
                // check if stack is full
                if (currentStack.size() >= 6) {
                    if (top >= 12) {
                        // win by adding large enough number on top
                        return isMaximizingPlayersTurn ? 100 : -100;
                    }
                    if (top == 11 || sum == 20) {
                        // cannot prevent enemy from winning on their next turn
                        return isMaximizingPlayersTurn ? -10 : 10;
                    }
                }
            }
        }
        // otherwise return 0 (neutral rating)
        return 0;
    }

    public void simulatePossibleMoves() {
        // if current player is maximizing player (if it is player two, the AI) increment the round number for next position
        int newRoundNr = this.isMaximizingPlayer ? this.roundNumber + 1: this.roundNumber;
        // iterate over all potentially possible moves
        for (int move = 0; move < 10; move++) {
            // clone current stack before making changes
            Stack<Integer> newStack = (Stack<Integer>) this.currentStack.clone();
            // simulate changes to stack depending on move made
            if (move == 0 && newStack.size() > 1 && this.roundNumber >= 4) {
                newStack.push(newStack.pop() + newStack.pop());
                children.add(new Position(newStack, newRoundNr, !this.isMaximizingPlayer, move));
            } else if (move >= 1) {
                if (newStack.size() == 6) {
                    int stackNr1 = newStack.pop();
                    newStack.push(stackNr1 + move);
                } else if (!newStack.isEmpty() && newStack.size() < 6) {
                    newStack.push(move);
                }
                children.add(new Position(newStack, newRoundNr, !this.isMaximizingPlayer, move));
            }
        }
    }
}
