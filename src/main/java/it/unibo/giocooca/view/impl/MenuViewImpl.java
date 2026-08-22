package it.unibo.giocooca.view.impl;

import it.unibo.giocooca.controller.MenuController;
import it.unibo.giocooca.view.MenuView;

import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MenuViewImpl implements MenuView {
    private static final String BUTTON_STYLE = "-fx-font-size: 18px; -fx-padding: 10px 30px;";
    private static final int BUTTON_WIDTH = 200;
    private final Stage stage;
    private final MenuController controller;

   /**
    * Costruttore della grafica del menu.
    *
    * @param stage      la finiestra principale contenitore
    * @param controller gestione delle azioni dell'utente
    */
   public MenuViewImpl(final Stage stage, final MenuController controller) {
        this.stage = stage;
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

        final Button btnQuit = new Button("Esci");
        btnQuit.setStyle(BUTTON_STYLE);
        btnQuit.setPrefWidth(BUTTON_WIDTH);
        btnQuit.setOnAction(event -> this.controller.onQuit());

        final VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().addAll(titleLabel, btnStart, btnSettings, btnQuit);

        final StackPane background = new StackPane();
        background.setStyle("-fx-background-color:#ecf0f1;");
        background.getChildren().add(menuBox);

        this.stage.setTitle("Gioco dell'OCA - Menu\' principale");
        if (this.stage.getScene() == null) {
            final Scene scene = new Scene(background, 800, 600);
            this.stage.setScene(scene);
            this.stage.setMaximized(true);
        } else {
            this.stage.getScene().setRoot(background);
        }
        this.stage.show();

    }
}
