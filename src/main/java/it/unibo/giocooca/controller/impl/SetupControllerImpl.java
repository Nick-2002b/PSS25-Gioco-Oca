package it.unibo.giocooca.controller.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

import it.unibo.giocooca.controller.SetupController;
import it.unibo.giocooca.model.Board;
import it.unibo.giocooca.model.Dice;
import it.unibo.giocooca.model.GameConfig;
import it.unibo.giocooca.model.Match;
import it.unibo.giocooca.model.PlacementStrategy;
import it.unibo.giocooca.model.Player;
import it.unibo.giocooca.model.Settings;
import it.unibo.giocooca.model.impl.BoardImpl;
import it.unibo.giocooca.model.impl.DiceImpl;
import it.unibo.giocooca.model.impl.FixedFrequencyPlacementStrategy;
import it.unibo.giocooca.model.impl.MatchImpl;
import it.unibo.giocooca.model.impl.RandomPlacementStrategy;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.SetupView;
import it.unibo.giocooca.view.impl.SetupViewImpl;

/**
 * Implementazione del controller della schermata di setup della partita.
 */
public final class SetupControllerImpl implements SetupController {
    private static final Logger LOGGER = Logger.getLogger(SetupControllerImpl.class.getName());

    private final SceneManager sceneManager;
    private final Settings settings;
    private final SetupView setupView;

    /**
     * Crea il controller di setup della partita.
     *
     * @param sceneManager il navigator dell'applicazione, usato per tornare al menu
     * @param settings     impostazioni correnti da applicare alla nuova partita
     */
    public SetupControllerImpl(final SceneManager sceneManager, final Settings settings) {
        this.sceneManager = sceneManager;
        this.settings = settings;
        this.setupView = new SetupViewImpl(sceneManager, this);
    }

    @Override
    public void start() {
        this.setupView.show();
    }

    @Override
    public void onStartGame(final List<Player> players) {
        LOGGER.log(Level.INFO, "La partita e'' iniziata con n. {0} giocatori", players.size());

        final PlacementStrategy strategy = settings.isFixedPlacement()
                ? new FixedFrequencyPlacementStrategy()
                : new RandomPlacementStrategy();

        final GameConfig config = GameConfig.defaultConfig(settings.getNumSpecialCells(), strategy);
        final Board board = new BoardImpl(config);
        final List<Dice> diceList = new ArrayList<>();
        for (int i = 0; i < settings.getNumDice(); i++) {
            diceList.add(new DiceImpl());
        }
        final Match match = new MatchImpl(players, board, diceList);
        this.sceneManager.showMatch(match);
    }

    @Override
    public void onBackToMenu() {
        sceneManager.showMenu();
    }
}
