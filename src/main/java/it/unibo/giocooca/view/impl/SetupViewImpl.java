package it.unibo.giocooca.view.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.giocooca.controller.SetupController;
import it.unibo.giocooca.model.Player;
import it.unibo.giocooca.model.impl.PieceImpl;
import it.unibo.giocooca.model.impl.PlayerImpl;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.SetupView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Implementazione grafica JavaFX della schermata di setup della partita.
 *
 */
//TODO modificare addPlayer dove al momento è usato un valore fisso embeddato.
public final class SetupViewImpl implements SetupView {
    private static final int PLAYERS_WIDTH = 500;
    private static final int PLAYERS_HEIGHT = 150;
    private static final int BUTTON_WIDTH = 200;
    private final SceneManager sceneManager;
    private final SetupController controller;
    private final List<Player> players = new ArrayList<>();

    /**
     * Costruttore della grafica di setup.
     *
     * @param sceneManager il navigator usato per disegnare la schermata sullo stage
     * @param controller   gestione delle azioni dell'utente
     */
    public SetupViewImpl(final SceneManager sceneManager, final SetupController controller) {
        this.sceneManager = sceneManager;
        this.controller = controller;

    }
    private String getFirstCapital(String s){
        if(s == null || s.isBlank()){
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
    @Override
    public void show() {
        final boolean debug = false;
        final String styleDebugBox = "-fx-border-color: red; -fx-border-width: 2px; -fx-border-style: solid;";
        final Label title = new Label("Setup Gioco");
        title.setStyle("fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        final Label lbNumPlayers = new Label("Numero di giocatori: ");
        final ComboBox<Integer> cmbNumPlayers = new ComboBox<>();
        cmbNumPlayers.getItems().addAll(2, 3, 4);
        cmbNumPlayers.setValue(2);

        final HBox numPlayersBox = new HBox(15, lbNumPlayers, cmbNumPlayers);
        numPlayersBox.setAlignment(Pos.CENTER);
        if (debug) {
            numPlayersBox.setStyle(styleDebugBox);
        }

        final Label lbPlayerNames = new Label("Nome giocatore:");
        final TextField txtPlayerName = new TextField();

        final Label lbPieceColor = new Label("Colore pedina:");
        final ComboBox<String> cmbPieceColor = new ComboBox<>();
        cmbPieceColor.getItems().addAll("Rosso", "Verde", "Blu", "Giallo");
        cmbPieceColor.setValue("Rosso");

        final Button btnAddPlayer = new Button("Aggiungi giocatore");

        final HBox playerSetupBox = new HBox(15, lbPlayerNames, txtPlayerName, lbPieceColor, cmbPieceColor, btnAddPlayer);
        playerSetupBox.setAlignment(Pos.CENTER);
        if (debug) {
            playerSetupBox.setStyle(styleDebugBox);
        }

        final Label lbPlayers = new Label("Giocatori attuali: ");
        final VBox boxPlayersList = new VBox(5, lbPlayers);
        boxPlayersList.setPrefWidth(PLAYERS_WIDTH);
        boxPlayersList.setPrefHeight(PLAYERS_HEIGHT);
        boxPlayersList.setAlignment(Pos.CENTER);
        if (debug) {
            boxPlayersList.setStyle(styleDebugBox);
        }
        final Button btnBack = new Button("Indietro");
        btnBack.setStyle("-fx-font-size: 18px; -fx-padding: 10px 30px;");
        btnBack.setPrefWidth(BUTTON_WIDTH);
        btnBack.setOnAction(event -> this.controller.onBackToMenu());
        final Button btnStartGame = new Button("Avvia Gioco");
        btnStartGame.setStyle("-fx-font-size: 18px; -fx-padding: 10px 30px;");
        btnStartGame.setDisable(true);
        btnStartGame.setPrefWidth(BUTTON_WIDTH);
        btnStartGame.setOnAction(event -> this.controller.onStartGame(players));
        // l'action del btnAddPlayer setta delle variabili che vengono create dopo questo elemento quindi ho spostato qui
        btnAddPlayer.setOnAction(event -> {
            final String playerName = getFirstCapital(txtPlayerName.getText().trim());
            final String pieceColor = cmbPieceColor.getValue();
            if (!playerName.isBlank() && pieceColor != null) {
                final String temporaryNameEmbedded = "Cane";
                final Player player = new PlayerImpl(playerName, new PieceImpl(temporaryNameEmbedded, pieceColor));
                players.add(player);
                boxPlayersList.getChildren().add(new Label(players.size() + ". " + playerName + " (" + pieceColor + ")"));
                txtPlayerName.clear();
                txtPlayerName.requestFocus();
                cmbPieceColor.getItems().remove(pieceColor);
                if (!cmbPieceColor.getItems().isEmpty()) {
                    cmbPieceColor.setValue(cmbPieceColor.getItems().get(0));
                }
                if (!players.isEmpty()) {
                    cmbNumPlayers.setDisable(true);
                }
                if (players.size() == cmbNumPlayers.getValue()) {
                    btnAddPlayer.setDisable(true);
                    txtPlayerName.setEditable(false);
                    
                    btnStartGame.setDisable(false);
                }
            }
        });
        final HBox buttonsBox = new HBox(15, btnBack, btnStartGame);
        buttonsBox.setAlignment(Pos.CENTER);
        if (debug) {
            buttonsBox.setStyle(styleDebugBox);
        }

        final VBox allElements = new VBox(20, title, numPlayersBox, playerSetupBox, boxPlayersList, buttonsBox);
        allElements.setAlignment(Pos.CENTER);
        if (debug) {
            allElements.setStyle(styleDebugBox);
        }

        final StackPane background = new StackPane(allElements);

        sceneManager.render(background, "Setup Gioco");
    }
}
