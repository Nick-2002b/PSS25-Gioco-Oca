package it.unibo.giocooca;
import it.unibo.giocooca.model.*;
import it.unibo.giocooca.model.impl.*;
import java.util.List;

public class TestFunction {
    public static void main(String[] args){
        Player p1 = new PlayerMock("Pippo");
        Player p2 = new PlayerMock("Pluto");
        Board board = new BoardMock(20);
        Dice dice = new DiceMock();
        
        Match match = new MatchImpl(List.of(p1,p2), board, dice);

        System.out.println("----Inizio test partita----");
        System.out.println("Giocatore " + p1.getNickName() + " e giocatore " + p2.getNickName());
        System.out.println("Numero caselle " + board.getSize());
        for (int i = 1; i <=3; i++){
            System.out.println("\n Turno n. " + i);
            Player current = match.getCurrentPlayer();
            System.out.println("Gioca: " + current.getNickName() + " (Psosizione attuale: " + current.getPosition());
            int roll = match.rollDice();
            System.out.println("Risultato del dado " + roll);
            match.moveCurrentPlayer(roll);
            System.out.println("Nuova posizione di " + current.getNickName() + " casella " + current.getPosition());
            match.nextTurn();
        }
    System.out.println("\nProssimo giocatore di turno: " + match.getCurrentPlayer().getNickName());
    System.out.println("----Fine test partita----");
    }
}
