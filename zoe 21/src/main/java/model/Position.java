package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Position {

    private ArrayList<Integer> validMoves = new ArrayList<>();
    public ArrayList<Integer> getValidMoves() {
        return validMoves;
    }
    public void setValidMoves(ArrayList<Integer> validMoves) {
        this.validMoves = validMoves;
    }

    private final Set<Position> children = new HashSet<>();
    public Set<Position> getChildren() {
        return children;
    }

    private final int moveMade;
    public int getMoveMade() {
        return moveMade;
    }

    private final int evaluation;
    public int getEvaluation() {
        return evaluation;
    }

    private final Stack<Integer> positionStack;
    private final int positionRoundNr;
    private final boolean computersTurn;

    public Position(Stack<Integer> currentStack, int roundNr, boolean computersTurn, int moveMade) {
        this.positionStack = currentStack;
        this.positionRoundNr = roundNr;
        this.computersTurn = computersTurn;
        this.moveMade = moveMade;

        if (!currentStack.isEmpty()) {
            if (currentStack.peek() >= 21) {
                if (computersTurn) {
                    this.evaluation = -1000;
                } else {
                    this.evaluation = 1000;
                }
            } else this.evaluation = 0;
        } else this.evaluation = 0;
    }

    public void generateChildren() {
        for (int move = 0; move < 10; move++) {
            Stack<Integer> childStack = this.positionStack;
            int childRound = this.computersTurn ? this.positionRoundNr + 1: this.positionRoundNr;

            if (move == 0 && childStack.size() > 1 && childRound >= 4) {
                childStack.push(childStack.pop() + childStack.pop());
                Position child = new Position(childStack, childRound, !this.computersTurn, move);
                children.add(child);
            } else if (move >= 1) {
                if (childStack.size() == 6) {
                    int stackNr1 = childStack.pop();
                    childStack.push(stackNr1 + move);
                } else if (!childStack.isEmpty() && childStack.size() < 6) {
                    childStack.push(move);
                }
            Position child = new Position(childStack, childRound, !this.computersTurn, move);
            children.add(child);
            }
        }
    }
}
