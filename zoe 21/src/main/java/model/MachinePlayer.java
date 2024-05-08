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
    // dummy method that @skyler and @yana can override ;)
    public int makeMove(int currentRoundNr, Stack<Integer> currentStack) {
        if (difficulty == 1) {
            if (currentRoundNr < 4) {
                return ((int) (Math.random() * 9 + 1));
            } else {
                return ((int) (Math.random() * 10));
            }
        } else if (difficulty == 2) {
            if (currentRoundNr < 4) {
                return ((int) (Math.random() * 9 + 1));
            } else {
                return ((int) (Math.random() * 10));
            }
        } else if (difficulty == 3) {
            if (currentRoundNr < 4) {
                return ((int) (Math.random() * 9 + 1));
            } else {
                return ((int) (Math.random() * 10));
            }
        } else {
            return ((int) (Math.random() * 9 + 1));
        }
    }
}


