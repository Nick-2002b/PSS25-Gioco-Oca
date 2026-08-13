package it.unibo.giocooca.view;

public interface MatchView {
    void showMessage(String message);

    void showDiceResult(int result);
    
    void showWinner(String winner);

    void showCurrentTurn(String player);
}
