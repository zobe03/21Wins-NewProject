package model;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Position {

    private final Stack<Integer> positionStack;
    private final int positionRoundNr;
    private final boolean machinesMove;

    private final int moveMade;
    public int getMoveMade() {
        return moveMade;
    }

    private final int evaluation;
    public int getEvaluation() {
        return evaluation;
    }

    public Position(Stack<Integer> currentStack, int roundNr, boolean machinesMove, int moveMade) {
        this.positionStack = currentStack;
        this.positionRoundNr = roundNr;
        this.machinesMove = machinesMove;
        this.moveMade = moveMade;

        int nr1 = currentStack.pop();
        int nr2 = currentStack.pop();
        boolean stackFull = currentStack.size() >= 6;
        boolean canCombine = roundNr >= 4 && currentStack.size() > 1;

        if (nr1 >= 12 && stackFull || nr1 + nr2 >= 21 && canCombine) {
            this.evaluation = 1000;
        } else if (nr1 == 11 && stackFull && nr1 + nr2 < 21 || nr1 + nr2 == 20 && stackFull) {
            this.evaluation = -1000;
        } else {
            this.evaluation = 0;
        }
    }

    private final Set<Position> children = new HashSet<>();
    public Set<Position> getChildren() {
        return children;
    }

//    public Position generateChild(int move, Position parent) {
//
//        Stack<Integer> childStack = parent.positionStack;
//        int childRound = parent.machinesMove ? parent.positionRoundNr + 1: parent.positionRoundNr;
//
//        if (move == 0 && childStack.size() > 1 && childRound >= 4) {
//            childStack.push(childStack.pop() + childStack.pop());
//        } else if (move <= 9 && move >= 1) {
//            if (childStack.size() == 6) {
//                int stackNr1 = childStack.pop();
//                childStack.push(stackNr1 + move);
//            } else if (!childStack.isEmpty() && childStack.size() < 6) {
//                childStack.push(move);
//            }
//        }
//
//        return new Position(childStack, childRound, !parent.machinesMove, move);
//    }

    public void generateChildren() {
        for (int move = 0; move < 10; move++) {
            Stack<Integer> childStack = this.positionStack;
            int childRound = this.machinesMove ? this.positionRoundNr + 1: this.positionRoundNr;

            if (move == 0 && childStack.size() > 1 && childRound >= 4) {
                childStack.push(childStack.pop() + childStack.pop());
            } else if (move >= 1) {
                if (childStack.size() == 6) {
                    int stackNr1 = childStack.pop();
                    childStack.push(stackNr1 + move);
                } else if (!childStack.isEmpty() && childStack.size() < 6) {
                    childStack.push(move);
                }
            }

            Position child = new Position(childStack, childRound, !this.machinesMove, move);
            children.add(child);
        }
    }


}
