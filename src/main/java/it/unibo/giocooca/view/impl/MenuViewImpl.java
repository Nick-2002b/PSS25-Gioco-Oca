package it.unibo.giocooca.view.impl;

import it.unibo.giocooca.controller.MenuController;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.MenuView;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

/**
 * Implementazione grafica JavaFX del menu principale.
 */
public final class MenuViewImpl implements MenuView {
    private static final String BUTTON_STYLE = "-fx-font-size: 18px; -fx-padding: 10px 30px;";
    private static final int BUTTON_WIDTH = 200;
    private final SceneManager sceneManager;
    private final MenuController controller;

   /**
    * Costruttore della grafica del menu.
    *
    * @param sceneManager il navigator usato per disegnare la schermata sullo stage
    * @param controller gestione delle azioni dell'utente
    */
   public MenuViewImpl(final SceneManager sceneManager, final MenuController controller) {
        this.sceneManager = sceneManager;
        this.controller = controller;
    }

    @Override
    public void show() {
        final Label titleLabel = new Label("Gioco dell'OCA");
        titleLabel.setStyle("fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        final Button btnStart = new Button("Nuova partita");
        btnStart.setStyle(BUTTON_STYLE);
        btnStart.setPrefWidth(BUTTON_WIDTH);
        btnStart.setOnAction(event -> this.controller.onStartNewGame());

        final Button btnSettings = new Button("Impostazioni");
        btnSettings.setStyle(BUTTON_STYLE);
        btnSettings.setPrefWidth(BUTTON_WIDTH);
        btnSettings.setOnAction(event -> this.controller.onOpenSettings());

        final Button btnRules = new Button("Regole");
        btnRules.setStyle(BUTTON_STYLE);
        btnRules.setPrefWidth(BUTTON_WIDTH);
        btnRules.setOnAction(event -> this.controller.onShowRules());

        final Button btnQuit = new Button("Esci");
        btnQuit.setStyle(BUTTON_STYLE);
        btnQuit.setPrefWidth(BUTTON_WIDTH);
        btnQuit.setOnAction(event -> this.controller.onQuit());

        final VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().addAll(titleLabel, btnStart, btnSettings, btnRules, btnQuit);

        final StackPane background = new StackPane();
        background.setStyle("-fx-background-color:#ecf0f1;");
        background.getChildren().add(menuBox);

        sceneManager.render(background, "Gioco dell'OCA - Menu' principale");
    }
}
