package it.unibo.giocooca.view.impl;

import it.unibo.giocooca.controller.MenuController;
import it.unibo.giocooca.view.MenuView;

import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MenuViewImpl implements MenuView{
    private final Stage stage;
    private final MenuController controller;

    /**
     * Costruttore della grafica del menu
     * @param stage la finiestra principale contenitore
     * @param controller gestione delle azioni dell'utente
     */
    public MenuViewImpl(Stage stage, MenuController controller){
        this.stage = stage;
        this.controller = controller;
    }

    @Override
    public void show(){
        Label titleLabel = new Label("Gioco dell'OCA");
        titleLabel.setStyle("fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        Button btnStart = new Button("Nuova partita");
        btnStart.setStyle("-fx-font-size: 18px; -fx-padding: 10px 30px;");
        btnStart.setOnAction(event -> this.controller.onStartNewGame());
        Button btnQuit = new Button("Esci");
        btnQuit.setStyle("-fx-font-size: 18px; -fx-padding: 10px 30px;");
        btnQuit.setOnAction(event -> this.controller.onQuit());

        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().addAll(titleLabel, btnStart, btnQuit);

        StackPane background = new StackPane();
        background.setStyle("-fx-background-color:#ecf0f1;");
        background.getChildren().add(menuBox);

        Scene scene = new Scene(background, 800, 600);
        this.stage.setTitle("Gioco dell'OCA - Menù principale");
        this.stage.setScene(scene);

        this.stage.setMaximized(true);
        this.stage.show();
    
    }
}
