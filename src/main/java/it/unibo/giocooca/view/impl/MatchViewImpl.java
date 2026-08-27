package it.unibo.giocooca.view.impl;

import it.unibo.giocooca.controller.MatchController;
import it.unibo.giocooca.navigation.SceneManager;
import it.unibo.giocooca.view.MatchView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Implementazione grafica della partita.
 */
public class MatchViewImpl implements MatchView{
   private static final int PADDING = 20;
   private static final int SPACING = 15;
   private static final int SIDEBAR_WIDTH = 280;
   private static final int BUTTON_WIDTH = 220;
   private static final int LOG_AREA_HEIGHT = 200;

   private final SceneManager sceneManager;
   private final MatchController controller;


   private Label lblCurrentTurn;
   private Label lblDiceResult;
   private TextArea txtLogArea;
   private Button btnRollDice;

    /**
     * @param sceneManager navigatore usato per visualizzare le view
     * @param controller controller centrale della partita
     */
   public MatchViewImpl(final SceneManager sceneManager, final MatchController controller){
      if (sceneManager == null) {
         throw new IllegalArgumentException("SceneManager non puo' essere null");
       }
      if (controller == null) {
         throw new IllegalArgumentException("MatchController non puo' essere null");
      }

      this.sceneManager = sceneManager;
      this.controller = controller;
   }
   @Override
   public void show(){
      final BorderPane rootLayout = new BorderPane();
      rootLayout.setPadding(new Insets(PADDING));
      rootLayout.setStyle("-fx-background-color: #ecf0f1;");

      // --- Area Centrale: Tabellone  ---
      final StackPane centralBoard = new StackPane();
      centralBoard.setStyle("-fx-background-color: #ffffff; -fx-border-color: #bdc3c7; -fx-border-width: 2px; -fx-border-radius: 5px;");
      final Label boardLabel = new Label("Area Tabellone Grafico");
      boardLabel.setStyle("-fx-font-size: 20px; -fx-text-alignment: center; -fx-text-fill: #7f8c8d;");
      centralBoard.getChildren().add(boardLabel);

      rootLayout.setCenter(centralBoard);

      // --- Area Destra: Pannello Informazioni e Controlli ---
      this.lblCurrentTurn = new Label("Turno: -");
      this.lblCurrentTurn.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

      this.lblDiceResult = new Label("Ultimo lancio: -");
      this.lblDiceResult.setStyle("-fx-font-size: 16px; -fx-text-fill: #34495e;");

      this.txtLogArea = new TextArea();
      this.txtLogArea.setEditable(false);
      this.txtLogArea.setWrapText(true);
      this.txtLogArea.setPrefHeight(LOG_AREA_HEIGHT);

      this.btnRollDice = new Button("Lancia Dado");
      this.btnRollDice.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-font-weight: bold;");
      this.btnRollDice.setPrefWidth(BUTTON_WIDTH);
      this.btnRollDice.setOnAction(event -> this.controller.rollDice());

      final Button btnQuit = new Button("Esci al Menu");
      btnQuit.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px;");
      btnQuit.setPrefWidth(BUTTON_WIDTH);
      btnQuit.setOnAction(event -> this.controller.quitMatch());

      final Label logTitle = new Label("Cronologia eventi:");
      logTitle.setStyle("-fx-font-weight: bold;");

      final VBox controlPanel = new VBox(SPACING);
      controlPanel.setPrefWidth(SIDEBAR_WIDTH);
      controlPanel.setPadding(new Insets(0, 0, 0, PADDING));
      controlPanel.setAlignment(Pos.TOP_CENTER);
      controlPanel.getChildren().addAll(
         this.lblCurrentTurn,
         this.btnRollDice,
         this.lblDiceResult,
         logTitle,
         this.txtLogArea,
         btnQuit
      );

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
   public void showDiceResult(final int result) {
      if (this.lblDiceResult != null) {
         this.lblDiceResult.setText("Ultimo lancio: " + result);
      }
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
   public void showCurrentTurn(final String player) {
      if (this.lblCurrentTurn != null) {
         this.lblCurrentTurn.setText("Turno di: " + player);
      }
   }
}