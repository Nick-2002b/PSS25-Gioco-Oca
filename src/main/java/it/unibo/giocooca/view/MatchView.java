package it.unibo.giocooca.view;

import it.unibo.giocooca.model.Player;

public interface MatchView {
    void showMessage(String message);

    void showDiceResult(int result);
    
    void showWinner(Player winner);

    void showCurrentTurn(Player p);
}
