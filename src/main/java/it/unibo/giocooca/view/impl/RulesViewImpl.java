package it.unibo.giocooca.view.impl;

import it.unibo.giocooca.controller.RulesController;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.RulesView;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Implementazione grafica della schermata delle regole.
 */
public final class RulesViewImpl implements RulesView {
    private static final int ICON_SIZE = 64;
    private static final int CARD_ICON_SIZE = 28;
    private static final int GRID_COLUMNS = 2;
    private static final double COLUMN_PERCENT_WIDTH = 100.0 / GRID_COLUMNS;
    private static final int CARD_MAX_WIDTH = 480;
    private static final Image diceIcon = new Image("/images/diceIcon.png");
    private static final Image starIcon = new Image("/images/starIcon.png");
    private static final Image flagIcon = new Image("/images/flagIcon.png");
    private static final Image targetIcon = new Image("/images/targetIcon.png");

    private static final String CARD_STYLE =
            "-fx-background-color: white;"
            + "-fx-background-radius: 12;"
            + "-fx-padding: 20;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0, 0, 2);";
    private final SceneManager sceneManager;
    private final RulesController controller;

    /**
     * Costruttore della view delle regole.
     * 
     * @param sceneManager manager per disegnare su stage
     * @param controller il controller associato
     */
    public RulesViewImpl(final SceneManager sceneManager, final RulesController controller) {
        this.sceneManager = sceneManager;
        this.controller = controller;
    }

    @Override
    public void show() {
        final BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:#ecf0f1;");

        root.setTop(buildHeader());
        root.setCenter(buildRuleCards());
        root.setBottom(buildFooter());

        sceneManager.render(root, "Gioco dell'OCA - Regole");
    }

    private VBox buildHeader() {
        final ImageView icon = new ImageView(new Image("/images/OcaLogo.png"));
        icon.setFitWidth(ICON_SIZE);
        icon.setFitHeight(ICON_SIZE);
        icon.setPreserveRatio(true);

        final Label titleLabel = new Label("Regole del Gioco dell'OCA");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        final VBox topBox = new VBox(10, icon, titleLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20));
        return topBox;
    }

    private GridPane buildRuleCards() {
        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(10, 50, 20, 50));

        for (int i = 0; i < GRID_COLUMNS; i++) {
            final ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(COLUMN_PERCENT_WIDTH);
            column.setHalignment(HPos.CENTER);
            grid.getColumnConstraints().add(column);
        }

        final VBox goalCard = createRuleCard(targetIcon, "Scopo del gioco",
                "Arrivare per primi all'ultima casella del tabellone.");
        final VBox turnsCard = createRuleCard(diceIcon, "Svolgimento",
                "A turno, i giocatori lanciano il dado e muovono la propria pedina "
                + "di un numero di caselle pari al risultato del lancio.");
        final VBox specialCard = createRuleCard(starIcon, "Caselle Speciali e Prigione",
                "Chi finisce su una casella 'Speciale' ottiene un bonus (o un malus) "
                + "e avanza (o retrocede) di ulteriori caselle. Chi finisce nella casella "
                + "'Prigione' salta un turno.");
        final VBox bounceCard = createRuleCard(flagIcon, "Rimbalzo finale",
                "Per vincere bisogna arrivare esattamente sull'ultima casella. "
                + "Se il numero ottenuto col dado e' maggiore di quello necessario, "
                + "la pedina rimbalza all'indietro per i punti in eccesso.");

        grid.add(goalCard, 0, 0);
        grid.add(turnsCard, 1, 0);
        grid.add(specialCard, 0, 1);
        grid.add(bounceCard, 1, 1);

        return grid;
    }

    private VBox createRuleCard(final Image icon, final String title, final String description) {
        final ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(CARD_ICON_SIZE);
        iconView.setFitHeight(CARD_ICON_SIZE);
        iconView.setPreserveRatio(true);

        final Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        final Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");

        final VBox card = new VBox(10, iconView, titleLabel, descriptionLabel);
        card.setStyle(CARD_STYLE);
        card.setMaxWidth(CARD_MAX_WIDTH);
        card.setAlignment(Pos.TOP_LEFT);
        return card;
    }

    private VBox buildFooter() {
        final Button btnBack = new Button("Indietro");
        btnBack.setStyle("-fx-font-size: 18px; -fx-padding: 10px 30px;");
        btnBack.setOnAction(e -> this.controller.onBackToMenu());

        final VBox bottomBox = new VBox(btnBack);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));
        return bottomBox;
    }
}
