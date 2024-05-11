package com.example.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Position {

    private final Stack<Integer> positionStack;
    private final int positionRoundNr;
    private final boolean activePlayerIsMachine;
    private final int moveMade;
    private final int positionEvaluation;
    public Position(Stack<Integer> currentStack, int roundNr, boolean activePlayerIsMachine, int lastMoveMade) {
        this.positionStack = currentStack;
        this.positionRoundNr = roundNr;
        this.activePlayerIsMachine = activePlayerIsMachine;
        this.moveMade = lastMoveMade;
        this.positionEvaluation = positionEvaluation(currentStack, !activePlayerIsMachine);
    }

    private int positionEvaluation(Stack<Integer> s, boolean max) {
        // +/-1000 = winning/losing position within one turn
        // +/-500  = winning/losing position within two turns

        if (s.isEmpty()) return 0;

        int top = s.peek();
        if (top >= 21) {
            // check if position is already final (already >= 21)
            return max ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }

        if (s.size() >= 2 && this.positionRoundNr >= 4) {
            // check if stack has at least 2 elements and is at least round 4 to create sum
            int sum = top + s.get(s.size() - 2);
            if (sum >= 21) {
                return max ? 1000 : -1000;
                // win by combining the two top elements to sum
            }

            if (s.size() >= 6) {
                // check if stack is full
                if (top >= 12) {
                    return max ? 1000 : -1000;
                    // win by adding large enough number on top
                }
                if (top == 11) {
                    return max ? -500 : 500;
                    // cannot prevent enemy from winning on their next turn
                }
                if (sum == 20) {
                    return max ? -500 : 500;
                    // cannot prevent enemy from winning on their next turn
                }
            }
            // otherwise return 0 (neutral rating)
        }
        return 0;
    }

//    private int positionEvaluation(Stack<Integer> stack, boolean max) {
//        if (!stack.isEmpty()) {
//            if (stack.peek() >= 21) {
//                if (max) {
//                    return Integer.MAX_VALUE;
//                } else {
//                    return Integer.MIN_VALUE;
//                }
//
//            } else if (stack.size() >= 2) {
//
//                if (stack.peek() + stack.get(stack.size() - 2) >= 21) {
//                    if (max) {
//                        return 1000;
//                    } else {
//                        return -1000;
//                    }
//                }
//
//                if (stack.size() >= 6) {
//
//                    if (stack.peek() >= 12) {
//                        if (max) {
//                            return 1000;
//                        } else {
//                            return -1000;
//                        }
//                    }
//
//                    if (stack.peek() == 11 && stack.peek() + stack.get(stack.size() - 2) < 21) {
//                        if (max) {
//                            return -500;
//                        } else {
//                            return 500;
//                        }
//                    }
//
//                    if (stack.peek() + stack.get(stack.size() - 2) == 20) {
//                        if (max) {
//                            return -500;
//                        } else {
//                            return 500;
//                        }
//                    }
//                }
//            }
//        }
//        return 0;
//    }

    private final ArrayList<Integer> validMoves = new ArrayList<>();
    private final Set<Position> children = new HashSet<>();

    public ArrayList<Integer> getValidMoves() {
        return validMoves;
    }
    public Set<Position> getChildren() {
        return children;
    }
    public int getMoveMade() {
        return moveMade;
    }
    public int getPositionEvaluation() {
        return positionEvaluation;
    }

    public void generateChildren() {
        int newRoundNr = this.activePlayerIsMachine ? this.positionRoundNr + 1: this.positionRoundNr;
        for (int move = 0; move < 10; move++) {
            Stack<Integer> newStack = (Stack<Integer>) this.positionStack.clone();

            if (move == 0 && newStack.size() > 1 && this.positionRoundNr >= 4) {
                newStack.push(newStack.pop() + newStack.pop());
                Position child = new Position(newStack, newRoundNr, !this.activePlayerIsMachine, move);
                children.add(child);

            } else if (move >= 1) {
                if (newStack.size() == 6) {
                    int stackNr1 = newStack.pop();
                    newStack.push(stackNr1 + move);
                } else if (!newStack.isEmpty() && newStack.size() < 6) {
                    newStack.push(move);
                }

            Position child = new Position(newStack, newRoundNr, !this.activePlayerIsMachine, move);
            children.add(child);

            }
        }
    }
}
