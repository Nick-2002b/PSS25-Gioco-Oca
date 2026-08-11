package it.unibo.giocooca.model;

import java.util.List;

public interface Match {
    int rollDice();

    void moveCurrentPlayer(int steps);

    void applyCurrentCellEffect();

    void nextTurn();

    boolean isGameOver();

    Player getCurrentPlayer();
    
    Player getWinner();

    List<Player> getPlayers();

    Board getBoard();
}
