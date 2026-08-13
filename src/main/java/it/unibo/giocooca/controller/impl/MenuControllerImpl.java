package it.unibo.giocooca.controller.impl;

import it.unibo.giocooca.controller.MenuController;
import it.unibo.giocooca.view.MenuView;
import it.unibo.giocooca.view.impl.MenuViewImpl;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MenuControllerImpl implements MenuController{
    private final Stage stage;
    private final MenuView view;

    public MenuControllerImpl(Stage stage){
        this.stage = stage;
        this.view = new MenuViewImpl(stage, this);           
        
    }
    public void start(){
        this.view.show();
    }

    @Override
    public void onStartNewGame(){
        System.out.println("Partita iniziata");
    }

    @Override
    public void onQuit(){
        Platform.exit();
    }

}

