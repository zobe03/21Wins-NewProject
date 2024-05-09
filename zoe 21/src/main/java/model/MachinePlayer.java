package model;

import java.util.Stack;

// von MachinePlayer können weitere Unterklassen mit unterschiedlichem Schwierigkeitsgrad/Eigenschaften erstellt werden (sonst final setzen)
public final class MachinePlayer extends Player {
    @Override
    public String getName() {
        return "Computer";
    }

    private int difficulty;
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    public int searchDepth() {
        return switch (difficulty) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            default -> 1;
        };
    }

    public int makeMove(int currentRoundNr, Stack<Integer> currentStack) {

        Position root = new Position(currentStack, currentRoundNr, true, -1);
        int bestEval = minimax(root, searchDepth(), true);
        for (Position child : root.getChildren()) {
            if (child.getEvaluation() == bestEval) {
                return child.getMoveMade();
            }
        }

        return -1;
    }

    public int minimax(Position pos, int depth, boolean machinesMove) {
    // machinesMove is somewhat equivalent to 'maximizingPlayer', since the COM is always maximizing

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


