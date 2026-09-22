package it.unibo.giocooca.view.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.unibo.giocooca.controller.SetupController;
import it.unibo.giocooca.model.Player;
import it.unibo.giocooca.model.impl.PieceImpl;
import it.unibo.giocooca.model.impl.PlayerImpl;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.SetupView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Implementazione grafica JavaFX della schermata di setup della partita.
 */
public final class SetupViewImpl implements SetupView {
    private static final int CONTENT_MAX_WIDTH = 600;
    private static final int LABEL_WIDTH = 180;
    private static final int INPUT_WIDTH = 200;
    private static final int COLOR_COMBO_WIDTH = 110;
    private static final int HEADER_ICON_SIZE = 32;
    private static final int LOGO_SIZE = 100;
    private static final int SPACING_SMALL = 10;
    private static final int SPACING_DEFAULT = 15;
    private static final int SPACING_LARGE = 20;
    private static final int PADDING_HEADER = 25;
    private static final int PADDING_FOOTER = 25;
    private static final Image CARD_ICON = new Image("/images/icons/starIcon.png");

    private static final String TITLE_STYLE =
            "-fx-font-size: 34px; -fx-font-weight: bold; -fx-text-fill: #144C30;";
    private static final String CARD_STYLE =
            "-fx-background-color: white;"
                    + "-fx-background-radius: 14;"
                    + "-fx-padding: 25;"
                    + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0, 0, 3);";
    private static final String SECTION_TITLE_STYLE =
            "-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #144C30;";
    private static final String SUBTITLE_STYLE =
            "-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #144C30;";
    private static final String ROW_LABEL_STYLE = "-fx-font-size: 15px;";
    private static final String PLACEHOLDER_STYLE =
            "-fx-font-size: 13px; -fx-text-fill: #7f8c8d; -fx-font-style: italic;";
    private static final String PLAYER_LABEL_STYLE =
            "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #144C30;";
    private static final String PLAYER_ITEM_STYLE =
            "-fx-background-color: #f7faf7; "
                    + "-fx-background-radius: 6; "
                    + "-fx-padding: 6px 12px; "
                    + "-fx-border-color: #dce6dc; "
                    + "-fx-border-radius: 6; "
                    + "-fx-border-width: 1px;";
    private static final String BUTTON_STYLE =
            "-fx-font-size: 18px; "
                    + "-fx-padding: 10px 20px; "
                    + "-fx-background-color: #9cb596;"
                    + "-fx-text-fill: white;"
                    + "-fx-background-radius: 8;"
                    + "-fx-cursor: hand;";
    private static final String ACTION_BUTTON_STYLE =
            "-fx-font-size: 14px; "
                    + "-fx-padding: 6px 14px; "
                    + "-fx-background-color: #9cb596;"
                    + "-fx-text-fill: white;"
                    + "-fx-background-radius: 6;"
                    + "-fx-cursor: hand;";

    private final SceneManager sceneManager;
    private final SetupController controller;
    private final List<Player> players = new ArrayList<>();
    private final Button btnStartGame = new Button("Avvia Gioco");

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

    private String getFirstCapital(final String s) {
        if (s == null || s.isBlank()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase(Locale.ROOT) + s.substring(1).toLowerCase(Locale.ROOT);
    }

    @Override
    public void show() {
        final BorderPane root = new BorderPane();

        root.setTop(buildHeader());
        root.setCenter(buildContent());
        root.setBottom(buildFooter());

        sceneManager.render(root, "Gioco dell'Oca - Setup Gioco");
    }

    private VBox buildHeader() {
        final ImageView logo = new ImageView(new Image("/images/ocaLogo.png"));
        logo.setFitWidth(LOGO_SIZE);
        logo.setFitHeight(LOGO_SIZE);
        logo.setPreserveRatio(true);

        final Label title = new Label("Setup Gioco");
        title.setStyle(TITLE_STYLE);

        final VBox header = new VBox(SPACING_SMALL, logo, title);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(PADDING_HEADER, 0, 0, 0));
        return header;
    }

    private VBox buildContent() {
        final ComboBox<Integer> cmbNumPlayers = new ComboBox<>();
        cmbNumPlayers.getItems().addAll(2, 3, 4);
        cmbNumPlayers.setValue(2);
        cmbNumPlayers.setPrefWidth(INPUT_WIDTH);

        final HBox numPlayersRow = buildRow("Numero di giocatori", cmbNumPlayers);

        final TextField txtPlayerName = new TextField();
        txtPlayerName.setPromptText("Es. Mario");
        txtPlayerName.setPrefWidth(INPUT_WIDTH);

        final HBox playerNameRow = buildRow("Nome giocatore", txtPlayerName);

        final ComboBox<String> cmbPieceColor = new ComboBox<>();
        cmbPieceColor.getItems().addAll("Rosso", "Verde", "Blu", "Giallo");
        cmbPieceColor.setValue("Rosso");
        cmbPieceColor.setPrefWidth(COLOR_COMBO_WIDTH);

        final Button btnAddPlayer = new Button("Aggiungi");
        btnAddPlayer.setStyle(ACTION_BUTTON_STYLE);

        final HBox pieceColorRow = buildRow("Colore pedina", cmbPieceColor, btnAddPlayer);

        final Label statusLabel = new Label("Giocatori registrati (0 di 2):");
        statusLabel.setStyle(SUBTITLE_STYLE);

        cmbNumPlayers.valueProperty().addListener((obs, oldVal, newVal) -> {
            statusLabel.setText("Giocatori registrati (" + players.size() + " di " + newVal + "):");
        });

        final VBox playersList = new VBox(SPACING_SMALL);
        final Label emptyLabel = new Label("Nessun giocatore registrato. Compila i campi sopra.");
        emptyLabel.setStyle(PLACEHOLDER_STYLE);
        playersList.getChildren().add(emptyLabel);

        final Runnable addPlayerAction = () -> {
            final String playerName = getFirstCapital(txtPlayerName.getText().trim());
            final String pieceColor = cmbPieceColor.getValue();
            if (!playerName.isBlank() && pieceColor != null) {
                final String temporaryNameEmbedded = "Cane";
                final Player player = new PlayerImpl(playerName, new PieceImpl(temporaryNameEmbedded, pieceColor));
                players.add(player);

                if (players.size() == 1) {
                    playersList.getChildren().clear();
                }

                final Label playerLabel = new Label(players.size() + ". " + playerName + " (Pedina: " + pieceColor + ")");
                playerLabel.setStyle(PLAYER_LABEL_STYLE);
                final HBox playerItem = new HBox(SPACING_SMALL, playerLabel);
                playerItem.setStyle(PLAYER_ITEM_STYLE);
                playerItem.setAlignment(Pos.CENTER_LEFT);
                playersList.getChildren().add(playerItem);

                statusLabel.setText("Giocatori registrati (" + players.size() + " di " + cmbNumPlayers.getValue() + "):");

                txtPlayerName.clear();
                txtPlayerName.requestFocus();
                cmbPieceColor.getItems().remove(pieceColor);
                if (!cmbPieceColor.getItems().isEmpty()) {
                    cmbPieceColor.setValue(cmbPieceColor.getItems().get(0));
                }
                cmbNumPlayers.setDisable(true);

                if (players.size() == cmbNumPlayers.getValue()) {
                    btnAddPlayer.setDisable(true);
                    txtPlayerName.setDisable(true);
                    cmbPieceColor.setDisable(true);
                    btnStartGame.setDisable(false);
                }
            }
        };

        btnAddPlayer.setOnAction(event -> addPlayerAction.run());
        txtPlayerName.setOnAction(event -> addPlayerAction.run());

        final ImageView cardIcon = new ImageView(CARD_ICON);
        cardIcon.setFitWidth(HEADER_ICON_SIZE);
        cardIcon.setFitHeight(HEADER_ICON_SIZE);

        final VBox card = buildCard(
                cardIcon,
                "Configurazione Giocatori",
                numPlayersRow,
                playerNameRow,
                pieceColorRow,
                new javafx.scene.control.Separator(),
                statusLabel,
                playersList
        );

        final VBox content = new VBox(card);
        content.setMaxWidth(CONTENT_MAX_WIDTH);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(0, SPACING_LARGE, SPACING_LARGE, SPACING_LARGE));

        final VBox centerWrapper = new VBox(content);
        centerWrapper.setAlignment(Pos.CENTER);
        return centerWrapper;
    }

    private VBox buildFooter() {
        btnStartGame.setStyle(BUTTON_STYLE);
        btnStartGame.setDisable(true);
        btnStartGame.setOnAction(event -> this.controller.onStartGame(players));

        final Button btnBack = new Button("Indietro");
        btnBack.setStyle(BUTTON_STYLE);
        btnBack.setOnAction(event -> this.controller.onBackToMenu());

        final HBox buttonsBox = new HBox(SPACING_DEFAULT, btnStartGame, btnBack);
        buttonsBox.setAlignment(Pos.CENTER);

        final VBox footer = new VBox(buttonsBox);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(0, 0, PADDING_FOOTER, 0));
        return footer;
    }

    private VBox buildCard(final Node icon, final String titleText, final Node... rows) {
        final Label sectionTitle = new Label(titleText);
        sectionTitle.setStyle(SECTION_TITLE_STYLE);

        final HBox headerRow = new HBox(SPACING_DEFAULT, icon, sectionTitle);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        final VBox card = new VBox(SPACING_LARGE, headerRow);
        card.getChildren().addAll(rows);
        card.setStyle(CARD_STYLE);
        card.setMaxWidth(CONTENT_MAX_WIDTH);
        return card;
    }

    private HBox buildRow(final String labelText, final Node... nodes) {
        final Label label = new Label(labelText);
        label.setMinWidth(LABEL_WIDTH);
        label.setStyle(ROW_LABEL_STYLE);

        final HBox row = new HBox(SPACING_SMALL);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getChildren().add(label);
        row.getChildren().addAll(nodes);
        return row;
    }
}
