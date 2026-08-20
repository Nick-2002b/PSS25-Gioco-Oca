package it.unibo.giocooca.controller;
import it.unibo.giocooca.model.Player;
import java.util.List;


public interface SetupController {

   void start();

   void onStartGame(List<Player> players);

   void onBackToMenu();
   
}
