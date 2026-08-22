package it.unibo.giocooca.controller.impl;

import it.unibo.giocooca.audio.SoundEffect;
import it.unibo.giocooca.audio.SoundManager;
import it.unibo.giocooca.controller.SetupController;
import it.unibo.giocooca.controller.MenuController;
import it.unibo.giocooca.model.Settings;
import it.unibo.giocooca.model.impl.SettingsManager;
import it.unibo.giocooca.view.MenuView;
import it.unibo.giocooca.view.impl.MenuViewImpl;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MenuControllerImpl implements MenuController{
    private final Stage stage;
    private final MenuView view;
    private final Settings settings;


    public MenuControllerImpl(Stage stage){
        this.stage = stage;
        this.settings = new SettingsManager().load();
        SoundManager.getInstance().setMusicVolume(settings.getMusicVolume());
        SoundManager.getInstance().setSfxVolume(settings.getSfxVolume());
        this.view = new MenuViewImpl(stage, this);           
    }
    @Override
    public void start(){
        SoundManager.getInstance().playMusic(SoundEffect.BACKGROUND_MUSIC);
        this.view.show();
    }

    @Override
    public void onStartNewGame(){
        SetupController setupController = new SetupControllerImpl(this.stage);
        setupController.start();
    }

    @Override
    public void onOpenSettings() {
        new SettingsControllerImpl(stage, settings, this).show();
    }

    @Override
    public void onQuit(){
        Platform.exit();
    }

}

