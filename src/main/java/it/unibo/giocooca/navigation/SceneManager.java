package it.unibo.giocooca.navigation;

import it.unibo.giocooca.model.Match;
import javafx.scene.Parent;

/**
 * Application Controller responsabile della navigazione tra le schermate.
 * Ogni ControllerImpl dipende solo da questa interfaccia.
 */
public interface SceneManager {

    /**
     * Naviga verso il menu principale.
     */
    void showMenu();

    /**
     * Naviga verso la schermata delle impostazioni.
     */
    void showSettings();

    /**
     * Naviga verso la schermata di setup di una nuova partita.
     */
    void showSetup();

    /**
     * Naviga verso la schermata delle regole.
     */
    void showRules();

    /**
     * Naviga verso la schermata del gioco
     */
    void showMatch(Match match);

    /**
     * Disegna la radice della scena corrente sullo stage
     *
     * @param root  la radice grafica della schermata da mostrare
     * @param title il titolo da assegnare alla finestra
     */
    void render(Parent root, String title);
}
