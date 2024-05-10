package model;

import java.util.ArrayList;
import java.util.Stack;

// von MachinePlayer können weitere Unterklassen mit unterschiedlichem Schwierigkeitsgrad/Eigenschaften erstellt werden (sonst final setzen)
public final class MachinePlayer extends Player {
    @Override
    public String getName() {
        return "Computer";
    }

    int depth;
    public void setDifficulty(int difficulty) {
        if (difficulty == 1) {
            this.depth = 0;
        } else if (difficulty == 2) {
            this.depth = 3;
        } else if (difficulty == 3) {
            this.depth = 7;
        } else {
            this.depth = 10;
        }
    }

    public int makeMove(int currentRoundNr, Stack<Integer> currentStack) {

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

    public int minimax(Position pos, int depth, boolean machinesMove) {

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