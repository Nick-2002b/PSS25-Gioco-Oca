package it.unibo.giocooca.model;

public interface Match {
    int rollDice();

    void moveCurrentPlayer(int steps);

    void applyCurrentCellEffect();

    void nextTurn();

    boolean isGameOver();
}
