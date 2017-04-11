/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cjwgr5battleship;

import static cjwgr5battleship.Difficulty.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Christopher
 */
public class BattleShipGameStartController implements Initializable {
    @FXML 
    private Button easy;
    @FXML 
    private Button medium;
    @FXML 
    private Button impossible;
    @FXML 
    private Label easyLabel; 
    @FXML 
    private Label mediumLabel;
    @FXML 
    private Label impossibleLabel;
    
    private Game game; 
    private Stage prevStage; 
    
    public void ready(Stage stage) {
        this.prevStage = stage;
        game = new Game();
        handlePercentLabels(); 
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage = new Stage(); 
     
     //create a new scene with root and set the stage

        //stage=(Stage) medium.getScene().getWindow();
      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BattleShipFXML.fxml"));
        Parent root;
        root = (Parent)loader.load();
        BattleShipFXMLController controller = (BattleShipFXMLController)loader.getController();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        prevStage.close();
        stage.show();
        
        if(event.getSource()==easy){
            controller.ready(stage, EASY);
        }
        else if(event.getSource()==medium) {
             controller.ready(stage, MEDIUM);
        }
        else {
            controller.ready(stage, IMPOSSIBLE);
        }
        
    }
    
   
    private void handlePercentLabels() {
        double easy = (game.getEasyPercentage() * 100);
        double medium = (game.getMediumPercentage() * 100);
        double impossible = (game.getImpossiblePercentage() * 100);
        easyLabel.setText(easy + "%");
        mediumLabel.setText(medium + "%");
        impossibleLabel.setText(impossible + "%");
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
