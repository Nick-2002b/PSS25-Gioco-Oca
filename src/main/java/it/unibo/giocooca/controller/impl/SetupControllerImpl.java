package it.unibo.giocooca.controller.impl;

import it.unibo.giocooca.controller.SetupController;
import it.unibo.giocooca.view.SetupView;
import it.unibo.giocooca.view.impl.SetupViewImpl;
import it.unibo.giocooca.model.Player;
import it.unibo.giocooca.model.GameConfig;
import it.unibo.giocooca.model.Board;
import it.unibo.giocooca.model.Dice;
import it.unibo.giocooca.model.Match;
import it.unibo.giocooca.model.impl.BoardImpl;
import it.unibo.giocooca.model.impl.DiceImpl;
import it.unibo.giocooca.model.impl.MatchImpl;
import javafx.stage.Stage;
import java.util.List;

public class SetupControllerImpl implements SetupController{
    private final Stage stage;
    private final SetupView setupView;

    public SetupControllerImpl(final Stage stage){
        this.stage = stage;
        this.setupView = new SetupViewImpl(stage, this);
    }

    @Override
    public void start(){
        this.setupView.show();
    }

    @Override
    public void onStartGame(final List<Player> players){
        System.out.println("La partita e\' iniziata con n. " + players.size() + " giocatori");
        final GameConfig config = GameConfig.defaultConfig(6);
        final Board board = new BoardImpl(config);
        final Dice dice = new DiceImpl();
        final Match match = new MatchImpl(players, board, dice);
        /**
         * Creare la implementazione della interfaccia MatchView
         * Creare l'implementazione dell'interfaccia MatchController
         */
    }

    @Override
    public void onBackToMenu() {
        new MenuControllerImpl(this.stage).start();
    }   
}
