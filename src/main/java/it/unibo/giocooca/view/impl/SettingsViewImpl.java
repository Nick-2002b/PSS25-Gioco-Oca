package it.unibo.giocooca.view.impl;

import it.unibo.giocooca.controller.SettingsController;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.SettingsView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Implementazione grafica JavaFX della schermata delle impostazioni.
 */
public final class SettingsViewImpl implements SettingsView {
    private static final int SLIDER_WIDTH = 250;
    private static final int MIN_SPECIAL = 1;
    private static final int CONTENT_MAX_WIDTH = 600;
    private static final int LABEL_WIDTH = 180;
    private static final int HEADER_ICON_SIZE = 32;
    private static final int LOGO_SIZE = 100;
    private static final Image diceIcon = new Image("/images/diceIcon.png");
    private static final Image speakerIcon = new Image("/images/speakerIcon.png");

    private static final String CARD_STYLE =
            "-fx-background-color: white;"
            + "-fx-background-radius: 14;"
            + "-fx-padding: 25;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0, 0, 3);";
    private static final String SECTION_TITLE_STYLE =
            "-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #144C30;";
    private static final String ROW_LABEL_STYLE = "-fx-font-size: 15px;";
    private static final String BUTTON_STYLE = "-fx-font-size: 15px; -fx-padding: 10px 30px;";

    private final SceneManager sceneManager;
    private final SettingsController controller;

    /**
     * Costruttore della grafica delle impostazioni.
     *
     * @param sceneManager il navigator usato per disegnare la schermata sullo stage
     * @param controller   gestione delle azioni dell'utente
     */
    public SettingsViewImpl(final SceneManager sceneManager, final SettingsController controller) {
        this.sceneManager = sceneManager;
        this.controller = controller;
    }

    @Override
    public void show() {
        final BorderPane root = new BorderPane();

        root.setTop(buildHeader());
        root.setCenter(buildContent());
        root.setBottom(buildFooter());

        sceneManager.render(root, "Gioco dell'Oca - Impostazioni");
    }

    private VBox buildHeader() {
        final ImageView logo = new ImageView(new Image("/images/ocaLogo.png"));
        logo.setFitWidth(LOGO_SIZE);
        logo.setFitHeight(LOGO_SIZE);

        final Label title = new Label("Impostazioni");
        title.setStyle("-fx-font-size: 34px; -fx-font-weight: bold; -fx-text-fill: #144C30;");

        final VBox header = new VBox(10, logo, title);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(25, 0, 0, 0));
        return header;
    }

    private VBox buildContent() {
        // --- Audio Section ---
        final Slider musicSlider = new Slider(0.0, 1.0, controller.getMusicVolume());
        musicSlider.setPrefWidth(SLIDER_WIDTH);
        final Label musicValueLabel = new Label(toPrecent(controller.getMusicVolume()));

        musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            musicValueLabel.setText(toPrecent(newVal.doubleValue()));
            controller.onMusicVolumeChanger(newVal.doubleValue());
        });

        final HBox musicRow = buildRow("Volume Musica", musicSlider, musicValueLabel);

        final Slider sfxSlider = new Slider(0.0, 1.0, controller.getSfxVolume());
        sfxSlider.setPrefWidth(SLIDER_WIDTH);
        final Label sfxValueLabel = new Label(toPrecent(controller.getSfxVolume()));

        sfxSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            sfxValueLabel.setText(toPrecent(newVal.doubleValue()));
            controller.onSfxVolumeChanged(newVal.doubleValue());
        });
        final HBox sfxRow = buildRow("Volume Effetti", sfxSlider, sfxValueLabel);

        final ImageView speaker = new ImageView(speakerIcon);
        speaker.setFitWidth(HEADER_ICON_SIZE);
        speaker.setFitHeight(HEADER_ICON_SIZE);
        final VBox audioCard = buildCard(speaker, "Impostazioni Audio", musicRow, sfxRow);

        // --- Game Section ---
        final Spinner<Integer> numDiceSpinner =
                new Spinner<>(1, 2, controller.getNumDice());
        numDiceSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            controller.onNumDiceChanged(newVal);
        });
        final HBox numDiceRow = buildRow("Numero di dadi", numDiceSpinner);

        final Spinner<Integer> specialSpinner =
                new Spinner<>(MIN_SPECIAL, controller.getMaxSpecialCells(), controller.getNumSpecialCells());

        specialSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            controller.onNumSpecialCellsChanger(newVal);
        });

        final HBox specialRow = buildRow("Caselle Speciali", specialSpinner);

        final ToggleGroup placementGroup = new ToggleGroup();
        final RadioButton randomBtn = new RadioButton("Casuale");
        randomBtn.setStyle(ROW_LABEL_STYLE);
        final RadioButton fixedBtn = new RadioButton("Frequenza fissa");
        fixedBtn.setStyle(ROW_LABEL_STYLE);
        randomBtn.setToggleGroup(placementGroup);
        fixedBtn.setToggleGroup(placementGroup);

        if (controller.isFixedPlacement()) {
            fixedBtn.setSelected(true);
        } else {
            randomBtn.setSelected(true);
        }

        placementGroup.selectedToggleProperty().addListener((obs, oldT, newT) -> {
            controller.onPlacementChanged(newT == fixedBtn);
        });

        final HBox placementRow = buildRow("Tipo Piazzamento", randomBtn, fixedBtn);

        final ImageView gameIcon = new ImageView(diceIcon);
        gameIcon.setFitWidth(HEADER_ICON_SIZE);
        gameIcon.setFitHeight(HEADER_ICON_SIZE);

        final VBox gameCard = buildCard(gameIcon, "Impostazioni di Gioco", numDiceRow, specialRow, placementRow);

        final VBox content = new VBox(25, audioCard, gameCard);
        content.setMaxWidth(CONTENT_MAX_WIDTH);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(0, 20, 20, 20));

        final VBox centerWrapper = new VBox(content);
        centerWrapper.setAlignment(Pos.CENTER);
        return centerWrapper;
    }

    private VBox buildCard(final Node icon, final String titleText, final Node... rows) {
        final Label sectionTitle = new Label(titleText);
        sectionTitle.setStyle(SECTION_TITLE_STYLE);

        final HBox headerRow = new HBox(15, icon, sectionTitle);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        final VBox card = new VBox(20, headerRow);
        card.getChildren().addAll(rows);
        card.setStyle(CARD_STYLE);
        card.setMaxWidth(CONTENT_MAX_WIDTH);
        return card;
    }

    private VBox buildFooter() {
        final Button saveBtn = new Button("Salva");
        saveBtn.setStyle(BUTTON_STYLE);
        saveBtn.setOnAction(x -> controller.onSave());

        final Button backBtn = new Button("Indietro");
        backBtn.setStyle(BUTTON_STYLE);
        backBtn.setOnAction(x -> controller.onBack());

        final HBox buttons = new HBox(15, saveBtn, backBtn);
        buttons.setAlignment(Pos.CENTER);

        final VBox footer = new VBox(buttons);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(0, 0, 25, 0));
        return footer;
    }

    private String toPrecent(final double value) {
        return (int) (value * 100) + "%";
    }

    private HBox buildRow(final String labelText, final Node... nodes) {
        final Label label = new Label(labelText);
        label.setMinWidth(LABEL_WIDTH);
        label.setStyle(ROW_LABEL_STYLE);

        final HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getChildren().add(label);
        row.getChildren().addAll(nodes);
        return row;
    }
}
