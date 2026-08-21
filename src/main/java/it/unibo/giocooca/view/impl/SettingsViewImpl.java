package it.unibo.giocooca.view.impl;

import it.unibo.giocooca.audio.SoundManager;
import it.unibo.giocooca.controller.MenuController;
import it.unibo.giocooca.model.Settings;
import it.unibo.giocooca.model.impl.SettingsManager;
import it.unibo.giocooca.view.SettingsView;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsViewImpl implements SettingsView {
    private static final int SLIDER_WIDTH = 250;
    private static final int MIN_SPECIAL = 1;
    private static final int MAX_SPECIAL = 20;
    private final Stage stage;
    private final MenuController controller;
    private final SettingsManager settingsManager;
    private final Settings currentSettings;

    public SettingsViewImpl(Stage stage, MenuController controller) {
        this.stage = stage;
        this.controller = controller;
        this.settingsManager = new SettingsManager();
        this.currentSettings = settingsManager.load();
    }

    @Override
    public void show() {
        final Label title = new Label("Impostazioni");

        // --- Audio Section ---
        final Label audioTitle = new Label("Audio");

        final Slider musicSlider = new Slider(0.0, 1.0, currentSettings.getMusicVolume());
        musicSlider.setPrefWidth(SLIDER_WIDTH);
        final Label musicValueLabel = new Label(toPrecent(currentSettings.getMusicVolume()));

        musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            musicValueLabel.setText(toPrecent(newVal.doubleValue()));
            SoundManager.getInstance().setMusicVolume(newVal.doubleValue());
        });

        final HBox musicRow = buildRow("Volume Musica", musicSlider, musicValueLabel);

        final Slider sfxSlider = new Slider(0.0, 1.0, currentSettings.getSfxVolume());
        sfxSlider.setPrefWidth(SLIDER_WIDTH);
        final Label sfxValueLabel = new Label(toPrecent(currentSettings.getSfxVolume()));

        sfxSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            sfxValueLabel.setText(toPrecent(newVal.doubleValue()));
            SoundManager.getInstance().setSfxVolume(newVal.doubleValue());
        });
        final HBox sfxRow = buildRow("Volume Effetti", sfxSlider, sfxValueLabel);

        //--- Game Section ---
        final Label gameTitle = new Label("Partita");

        final Spinner<Integer> specialSpinner = new Spinner<>(MIN_SPECIAL, MAX_SPECIAL, currentSettings.getNumSpecialCells());
        specialSpinner.setEditable(true);
        final HBox specialRow = buildRow("Caselle Speciali", specialSpinner);

        // --- Buttons ---
        final Button saveBtn = new Button("Salva");
        saveBtn.setOnAction(x -> onSave(musicSlider, sfxSlider, specialSpinner));

        final Button backBtn = new Button("Indietro");
        backBtn.setOnAction(x -> controller.start());

        final HBox buttons = new HBox(10, saveBtn, backBtn);
        buttons.setAlignment(Pos.CENTER);

        // --- Main Layout ---
        final VBox content = new VBox(10,
                title,
                new Separator(),
                audioTitle, musicRow, sfxRow,
                new Separator(),
                gameTitle, specialRow,
                new Separator(),
                buttons
        );
        content.setAlignment(Pos.CENTER);

        final StackPane root = new StackPane(content);
        StackPane.setAlignment(content, Pos.CENTER);

        if (stage.getScene() == null) {
            stage.setScene(new Scene(root, 800, 600));
        } else {
            stage.getScene().setRoot(root);
        }
        stage.setTitle("Gioco dell'Oca - Impostazioni");
    }

    private void onSave(Slider musicSlider, Slider sfxSlider, Spinner<Integer> specialSpinner) {
        currentSettings.setMusicVolume(musicSlider.getValue());
        currentSettings.setSfxVolume(sfxSlider.getValue());
        currentSettings.setNumSpecialCells(specialSpinner.getValue());
        settingsManager.save(currentSettings);
        controller.start();
    }

    private String toPrecent (double value) {
        return (int) (value * 100) + "%";
    }

    private HBox buildRow(String labelText, Node... nodes) {
        final Label label = new Label(labelText);

        final HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getChildren().add(label);
        row.getChildren().addAll(nodes);
        return row;
    }
}
