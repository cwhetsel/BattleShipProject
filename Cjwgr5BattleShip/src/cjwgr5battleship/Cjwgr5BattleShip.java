/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cjwgr5battleship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Christopher
 */
public class Cjwgr5BattleShip extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("BattleShipFXML.fxml"));
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("BattleShipFXML.fxml"));
        Parent root = (Parent)loader.load();
        BattleShipFXMLController controller = (BattleShipFXMLController)loader.getController();*/
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BattleShipGameStart.fxml"));
        Parent root = (Parent)loader.load();
        BattleShipGameStartController controller = (BattleShipGameStartController)loader.getController();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        controller.ready(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
