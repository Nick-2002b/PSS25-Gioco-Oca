package it.unibo.giocooca.view.impl;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import it.unibo.giocooca.controller.MatchController;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.BoardView;
import it.unibo.giocooca.view.MatchView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * Implementazione grafica della partita.
 */
public class MatchViewImpl implements MatchView {
    private static final int PADDING = 20;
    private static final int SPACING = 15;
    private static final int SIDEBAR_WIDTH = 280;
    private static final int BUTTON_WIDTH = 220;
    private static final int LOG_AREA_HEIGHT = 200;
    private static final int LOGO_SIZE = 100;

    private static final String BUTTON_STYLE =
            "-fx-font-size: 18px; " +
                    "-fx-padding: 10px 20px; " +
                    "-fx-background-color: #9cb596;" +
                    "-fx-text-fill: white;" +
                    "-fx-background-radius: 8;" +
                    "-fx-cursor: hand;";

    private final SceneManager sceneManager;
    private final MatchController controller;
    private final BoardView boardView;
    private final Random random = new Random();
    private Label lblCurrentTurn;
    private Label lblDiceResult;
    private TextArea txtLogArea;
    private Button btnRollDice;
    private Image diceFaceOne;
    private Image diceFaceTwo;
    private Image diceFaceThree;
    private Image diceFaceFour;
    private Image diceFaceFive;
    private Image diceFaceSix;
    private ImageView diceView1;
    private ImageView diceView2;
    private HBox diceBox;
    private List<Image> diceFaces;

    /**
     * @param sceneManager navigatore usato per visualizzare le view
     * @param controller   controller centrale della partita
     * @param board        griglia di gioco
     */
    public MatchViewImpl(final SceneManager sceneManager, final MatchController controller, final BoardView board) {
        if (sceneManager == null) {
            throw new IllegalArgumentException("SceneManager non puo' essere null");
        }
        if (controller == null) {
            throw new IllegalArgumentException("MatchController non puo' essere null");
        }

        this.sceneManager = sceneManager;
        this.controller = controller;
        this.boardView = board;
    }

    @Override
    public void show() {
        final BorderPane rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: #ecf0f1;");

        // --- Area Centrale: Tabellone  ---
        final StackPane centralBoard = new StackPane();
        centralBoard.getChildren().add(boardView.getBoard());

        final Image boardBackground = new Image("/images/boardBackground.png");
        centralBoard.setBackground(new Background(new BackgroundImage(
                boardBackground,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        )));

        rootLayout.setCenter(centralBoard);

        // --- Area Destra: Pannello Informazioni e Controlli ---
        this.lblCurrentTurn = new Label("Turno: -");
        this.lblCurrentTurn.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        this.lblDiceResult = new Label("Ultimo lancio: -");
        this.lblDiceResult.setStyle("-fx-font-size: 16px; -fx-text-fill: #34495e;");

        final Label titleLabel = new Label("Gioco dell'OCA");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #144C30;");

        this.txtLogArea = new TextArea();
        this.txtLogArea.setEditable(false);
        this.txtLogArea.setWrapText(true);
        this.txtLogArea.setPrefHeight(LOG_AREA_HEIGHT);

        this.diceFaceOne = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice/diceOne.png")));
        this.diceFaceTwo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice/diceTwo.png")));
        this.diceFaceThree = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice/diceThree.png")));
        this.diceFaceFour = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice/diceFour.png")));
        this.diceFaceFive = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice/diceFive.png")));
        this.diceFaceSix = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice/diceSix.png")));

        final ImageView logo = new ImageView(new Image("/images/ocalogo.png"));
        logo.setFitWidth(LOGO_SIZE);
        logo.setFitHeight(LOGO_SIZE);

        final VBox topBox = new VBox(10, logo, titleLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(PADDING));

        this.diceFaces = List.of(diceFaceOne, diceFaceTwo, diceFaceThree, diceFaceFour, diceFaceFive, diceFaceSix);

        this.diceView1 = new ImageView(this.diceFaceOne);
        this.diceView1.setFitWidth(45);
        this.diceView1.setPreserveRatio(true);

        this.diceView2 = new ImageView(this.diceFaceOne);
        this.diceView2.setFitWidth(45);
        this.diceView2.setPreserveRatio(true);
        final boolean twoDice = this.controller.getDiceNumber() > 1;
        this.diceView2.setVisible(twoDice);
        this.diceView2.setManaged(twoDice);

        this.diceBox = new HBox(8, this.diceView1, this.diceView2);
        this.diceBox.setAlignment(Pos.CENTER);

        this.btnRollDice = new Button(twoDice ? "Lancia Dadi" : "Lancia Dado");
        this.btnRollDice.setStyle("-fx-cursor: hand; -fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-font-weight: bold;");
        this.btnRollDice.setPrefWidth(BUTTON_WIDTH);
        this.btnRollDice.setGraphic(this.diceBox);
        this.btnRollDice.setOnAction(event -> this.controller.rollDice());

        final Button btnQuit = new Button("Esci al Menu");
        btnQuit.setStyle(BUTTON_STYLE);
        btnQuit.setPrefWidth(BUTTON_WIDTH);
        btnQuit.setOnAction(event -> this.controller.quitMatch());

        rootLayout.setStyle("-fx-background-color: #f4f6f8;");

        String cardStyle = "-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 2);";

        VBox topCard = new VBox(SPACING);
        topCard.setAlignment(Pos.CENTER);
        topCard.setStyle(cardStyle);
        topCard.getChildren().addAll(
                this.btnRollDice,
                this.lblCurrentTurn,
                this.lblDiceResult
        );

        final Label logTitle = new Label("Cronologia eventi");
        logTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #2c3e50;");

        this.txtLogArea.setStyle(
                "-fx-focus-color: transparent; " +
                        "-fx-faint-focus-color: transparent; " +
                        "-fx-text-box-border: transparent;"
        );
        this.txtLogArea.setFocusTraversable(false);

        VBox bottomCard = new VBox(10);
        bottomCard.setAlignment(Pos.TOP_LEFT);
        bottomCard.setStyle(cardStyle);
        bottomCard.getChildren().addAll(logTitle, this.txtLogArea);

//      btnQuit.setStyle("-fx-background-color: white; " +
//              "-fx-border-color: #bdc3c7; " +
//              "-fx-border-radius: 5; " +
//              "-fx-background-radius: 5; " +
//              "-fx-font-size: 14px; " +
//              "-fx-padding: 8px 16px; " +
//              "-fx-cursor: hand;");

        final VBox controlPanel = new VBox(SPACING);
        controlPanel.setPrefWidth(SIDEBAR_WIDTH);
        controlPanel.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        controlPanel.setAlignment(Pos.TOP_CENTER);

        controlPanel.getChildren().addAll(topBox, topCard, bottomCard, btnQuit);

        rootLayout.setRight(controlPanel);

        this.sceneManager.render(rootLayout, "Gioco dell'Oca - Partita in corso");
    }

    @Override
    public void showMessage(final String message) {
        if (this.txtLogArea != null) {
            this.txtLogArea.appendText(message + "\n");
        }
    }

    @Override
    public void showDiceResult(final List<Integer> results) {
        if (this.btnRollDice != null) {
            this.btnRollDice.setDisable(true);
        }
        final boolean twoDice = results.size() > 1;
        this.diceView2.setVisible(twoDice);
        this.diceView2.setManaged(twoDice);
        final Timeline diceAnimation = new Timeline(
                new KeyFrame(Duration.millis(100), event -> {
                    final int randomIndex1 = this.random.nextInt(this.diceFaces.size());
                    this.diceView1.setImage(this.diceFaces.get(randomIndex1));
                    if (twoDice) {
                        final int randomIndex2 = this.random.nextInt(this.diceFaces.size());
                        this.diceView2.setImage(this.diceFaces.get(randomIndex2));
                    }
                })
        );
        diceAnimation.setCycleCount(10);
        diceAnimation.setOnFinished(event -> {
            final int firstDiceResult = results.get(0);
            if (firstDiceResult >= 1 && firstDiceResult <= this.diceFaces.size()) {
                this.diceView1.setImage(this.diceFaces.get(firstDiceResult - 1));
            }
            if (twoDice) {
                final int secondDiceResult = results.get(1);
                if (secondDiceResult >= 1 && secondDiceResult <= this.diceFaces.size()) {
                    this.diceView2.setImage(this.diceFaces.get(secondDiceResult - 1));
                }
                final int totalResult = firstDiceResult + secondDiceResult;
                if (this.lblDiceResult != null) {
                    this.lblDiceResult.setText("Ultimo lancio: " + firstDiceResult + " + " + secondDiceResult + " = " + totalResult);
                }
            } else {
                if (this.lblDiceResult != null) {
                    this.lblDiceResult.setText("Ultimo lancio: " + firstDiceResult);
                }
            }
        });
        diceAnimation.play();
    }

    @Override
    public void showWinner(final String winner) {
        if (this.btnRollDice != null) {
            this.btnRollDice.setDisable(true);
        }
        if (this.lblCurrentTurn != null) {
            this.lblCurrentTurn.setText("Partita Terminata!");
        }

        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vittoria!");
        alert.setHeaderText("Abbiamo un vincitore!");
        alert.setContentText("Complimenti a " + winner + ", ha vinto la partita!");
        alert.showAndWait();
    }

    @Override
    public void showCurrentTurn(final String nickNamePlayer) {
        if (this.lblCurrentTurn != null) {
            this.lblCurrentTurn.setText("Turno di: " + nickNamePlayer);
        }
        if (this.btnRollDice != null) {
            this.btnRollDice.setDisable(false);
        }
    }
}