package it.unibo.giocooca.view.impl;

import it.unibo.giocooca.controller.SettingsController;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.SettingsView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Implementazione grafica JavaFX della schermata delle impostazioni.
 */
public final class SettingsViewImpl implements SettingsView {
    private static final int SLIDER_WIDTH = 250;
    private static final int MIN_SPECIAL = 1;
    private static final int CONTENT_MAX_WIDTH = 600;
    private static final int LABEL_WIDTH = 180;
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
        final Label title = new Label("Impostazioni");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // --- Audio Section ---
        final Label audioTitle = new Label("Audio");
        audioTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

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

        //--- Game Section ---
        final Label gameTitle = new Label("Partita");
        gameTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        final Spinner<Integer> specialSpinner = new Spinner<>(MIN_SPECIAL, controller.getMaxSpecialCells(), controller.getNumSpecialCells());
        specialSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            controller.onNumSpecialCellsChanger(newVal);
        });

        final HBox specialRow = buildRow("Caselle Speciali", specialSpinner);

        final ToggleGroup placementGroup = new ToggleGroup();
        final RadioButton randomBtn = new RadioButton("Casuale");
        final RadioButton fixedBtn = new RadioButton("Frequenza fissa");
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

        // --- Buttons ---
        final Button saveBtn = new Button("Salva");
        saveBtn.setStyle("-fx-font-size: 15px; -fx-padding: 10px 30px;");
        saveBtn.setOnAction(x -> controller.onSave());

        final Button backBtn = new Button("Indietro");
        backBtn.setStyle("-fx-font-size: 15px; -fx-padding: 10px 30px;");
        backBtn.setOnAction(x -> controller.onBack());

        final HBox buttons = new HBox(10, saveBtn, backBtn);
        buttons.setAlignment(Pos.CENTER);

        // --- Main Layout ---
        final VBox content = new VBox(10,
                title,
                new Separator(),
                audioTitle, musicRow, sfxRow,
                new Separator(),
                gameTitle, specialRow, placementRow,
                new Separator(),
                buttons
        );

        content.setMaxWidth(CONTENT_MAX_WIDTH);
        content.setAlignment(Pos.CENTER);

        final StackPane root = new StackPane(content);
        root.setStyle("-fx-background-color: #ecf0f1;");

        StackPane.setAlignment(content, Pos.CENTER);

        sceneManager.render(root, "Gioco dell'Oca - Impostazioni");
    }

    private String toPrecent(final double value) {
        return (int) (value * 100) + "%";
    }

   private HBox buildRow(final String labelText, final Node... nodes) {
        final Label label = new Label(labelText);
        label.setMinWidth(LABEL_WIDTH);

        final HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getChildren().add(label);
        row.getChildren().addAll(nodes);
        return row;
    }
}
