package it.unibo.giocooca.controller.impl;

import it.unibo.giocooca.controller.SetupController;
import it.unibo.giocooca.view.SetupView;
import it.unibo.giocooca.view.impl.SetupViewImpl;
import it.unibo.giocooca.model.Player;
import it.unibo.giocooca.model.GameConfig;
import it.unibo.giocooca.model.Board;
import it.unibo.giocooca.model.Dice;
import it.unibo.giocooca.model.impl.BoardImpl;
import it.unibo.giocooca.model.impl.DiceImpl;
import it.unibo.giocooca.model.impl.MatchImpl;
import javafx.stage.Stage;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementazione del controller della schermata di setup della partita.
 */
public final class SetupControllerImpl implements SetupController {
    private static final Logger LOGGER = Logger.getLogger(SetupControllerImpl.class.getName());
    private static final int DEFAULT_SPECIAL_CELLS = 6;

    private final Stage stage;
    private final SetupView setupView;

    /**
     * Crea il controller di setup della partita.
     *
     * @param stage la finestra principale dell'applicazione
     */
    public SetupControllerImpl(final Stage stage) {
        this.stage = stage;
        this.setupView = new SetupViewImpl(stage, this);
    }

    @Override
    public void start() {
        this.setupView.show();
    }

    @Override
    public void onStartGame(final List<Player> players) {
        LOGGER.log(Level.INFO, "La partita e'' iniziata con n. {0} giocatori", players.size());
        final GameConfig config = GameConfig.defaultConfig(DEFAULT_SPECIAL_CELLS);
        final Board board = new BoardImpl(config);
        final Dice dice = new DiceImpl();
        new MatchImpl(players, board, dice);
        //TODO creare l'implementazione dell'interfaccia MatchView
        //TODO creare l'implementazione dell'interfaccia MatchController
    }

    @Override
    public void onBackToMenu() {
        new MenuControllerImpl(this.stage).start();
    }
}
