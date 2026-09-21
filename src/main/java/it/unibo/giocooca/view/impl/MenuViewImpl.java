package it.unibo.giocooca.view.impl;

import it.unibo.giocooca.controller.MenuController;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.MenuView;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

/**
 * Implementazione grafica JavaFX del menu principale.
 */
public final class MenuViewImpl implements MenuView {
    private static final String BUTTON_STYLE =
            "-fx-font-size: 18px; " +
                    "-fx-padding: 10px 20px; " +
                    "-fx-background-color: #9cb596;" +
                    "-fx-text-fill: white;" +
                    "-fx-background-radius: 8;" +
                    "-fx-cursor: hand;";
    private static final int BUTTON_WIDTH = 220;
    private final SceneManager sceneManager;
    private final MenuController controller;

    /**
     * Costruttore della grafica del menu.
     *
     * @param sceneManager il navigator usato per disegnare la schermata sullo stage
     * @param controller   gestione delle azioni dell'utente
     */
    public MenuViewImpl(final SceneManager sceneManager, final MenuController controller) {
        this.sceneManager = sceneManager;
        this.controller = controller;
    }

    @Override
    public void show() {
        final ImageView logo = new ImageView(new Image("/images/ocaLogo.png"));
        logo.setFitHeight(128);
        logo.setPreserveRatio(true);
        final Label titleLabel = new Label("Gioco dell'OCA");
        titleLabel.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: #5b4b3e;");

        final Label subTitleLabel = new Label("Menu principale");
        subTitleLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #5b4b3e");

        final VBox titleBox = new VBox(5);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(logo, titleLabel, subTitleLabel);

        final Button btnStart = createButton("Nuova partita", "/images/icons/playIcon.png", this.controller::onStartNewGame);
        final Button btnSettings = createButton("Impostazioni", "/images/icons/settingsIcon.png", this.controller::onOpenSettings);
        final Button btnRules = createButton("Regole", "/images/icons/rulesIcon.png", this.controller::onShowRules);
        final Button btnQuit = createButton("Esci", "/images/icons/quitIcon.png", this.controller::onQuit);

        final VBox buttonsBox = new VBox(15);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(btnStart, btnSettings, btnRules, btnQuit);
        buttonsBox.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.4); " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-radius: 15; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.6); " +
                        "-fx-border-width: 2; " +
                        "-fx-padding: 30;"
        );

        buttonsBox.setMaxWidth(300);

        final VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().addAll(titleBox, buttonsBox);

        final StackPane background = new StackPane();
        background.getChildren().add(menuBox);

        sceneManager.render(background, "Gioco dell'OCA - Menu' principale");
    }

    private Button createButton(final String text, final String iconPath, Runnable action) {
        final Button button = new Button(text);
        button.setStyle(BUTTON_STYLE);
        button.setPrefWidth(BUTTON_WIDTH);
        button.setAlignment(Pos.CENTER_LEFT);
        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitWidth(30);
        icon.setFitHeight(30);
        button.setGraphic(icon);
        button.setGraphicTextGap(15);

        button.setOnAction(event -> action.run());
        return button;
    }
}
