/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cjwgr5battleship;

import java.awt.Color;
import static java.awt.Color.WHITE;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Christopher
 */
public class BattleShipFXMLController implements Initializable {
    
    private int currShip = 5;
    private int textNumb = 0;
    private Stage stage; 
    private ArrayList<Integer> picks; 
    private TextField choiceResultTextField; 
    private Game game;
    @FXML
    private GridPane board1;
    @FXML
    private GridPane board2;
    @FXML
    private Label instructions;
    @FXML
    private TextFlow recentMoves; 
    @FXML 
    private Label humanShipLabel; 
    @FXML 
    private Label aiShipLabel; 
    
    
    public void ready(Stage stage, Difficulty difficulty) {
        this.stage = stage;
        picks = new ArrayList<>();
        game = new Game(picks, difficulty);
        game.readFileGameWinInfo();
        stage.setResizable(false);
        
        instructions.setText("Welcome! Please begin by choosing 5 consecutive squares either vertically or horizontally to place your carrier.");
        Label label1;
        Label label2;
        for(int i=0; i < 10; i++) {
            for(int j = 0; j <10; j++) {
               label1 = new Label();
               label1.setPrefSize(50.0, 50.0);
               label1.setOnMousePressed((MouseEvent me) -> {
                   int location = Game.determineGridLocation(me.getSceneX(), me.getSceneY());
                   board1.getChildren().get(location).setId("picked");
                   picks.add(location);
                   
                   if(picks.size() == currShip) {
                       if(game.checkPicks()) {
                           for(int k=0; k<picks.size(); k++) {
                               board1.getChildren().get(picks.get(k)).setId("picked");
                               board1.getChildren().get(picks.get(k)).setOnMousePressed((MouseEvent ev) -> {
                                 return; 
                              });
                           }
                           currShip = game.getNextShipLength();
                           picks.clear();
                       }
                       else {
                           for(int k=0; k<picks.size(); k++) {
                               board1.getChildren().get(picks.get(k)).setId(null);
                               
                           }
                            picks.clear();
                       }
                   }
                   if(currShip == -1) {
                      switchBoards(); 
                      instructions.setText("Game Start. It is your turn. Choose a grid space to try and sink the AI's ships.");
                      
                   }
                   else {
                   int picksLeft = currShip - picks.size();
                   instructions.setText("Choose " + picksLeft + " more continuous squares.");
                   }
               });
               label2 = new Label();
               label2.setPrefSize(50.0, 50.0);
               board1.add(label1, i, j); 
               board2.add(label2, i, j);
            }
        }
     instructions.setText("Welcome! Please begin by choosing 5 consecutive squares either vertically or horizontally to place your carrier.");
        
        
    }
    
    
    //switches the labels in the Grid plane from their placing ship functionality to the actual playing of the game functionality. 
    //Also moves the Human Positions to the smaller gridpane. 
    private void switchBoards() {
        ArrayList<Integer>  pos = game.getHumanPositions();
        
        for(int k =0; k< pos.size(); k++) {
            board1.getChildren().get(pos.get(k)).setId(null);
            board2.getChildren().get(pos.get(k)).setId("picked");
        }
        for(int k=0; k<=100; k++) {
            board1.getChildren().get(k).setOnMousePressed((MouseEvent me) -> {
                Text text;
                int location = Game.determineGridLocation(me.getSceneX(), me.getSceneY());
                if(game.isHitAI(location)) {
                    board1.getChildren().get(location).setId("hit");
                    
                    text = new Text("You: Hit at " + Game.determineCoordinates(location)+"\n");
                    text.setId("moves");
                    if(textNumb > 9) {
                        recentMoves.getChildren().remove(0);
                    }
                    textNumb++; 
                    recentMoves.getChildren().add(text);
                    
                    
                }
                else {
                    board1.getChildren().get(location).setId("miss");
                    text = new Text("You: Miss at " + Game.determineCoordinates(location)+"\n");
                    text.setId("moves");
                    if(textNumb > 9) {
                        recentMoves.getChildren().remove(0);
                    }
                    textNumb++; 
                    recentMoves.getChildren().add(text);

                }
                board1.getChildren().get(location).setOnMousePressed((MouseEvent ev) -> {
                    instructions.setText("You already chose this square. Please choose another.");
                });
                int aiLoc = game.aiTurn();
                if((aiLoc) > 200) {
                    aiLoc = aiLoc -200;
                    board2.getChildren().get(aiLoc).setId("miss");
                    text = new Text("AI: Miss at " + Game.determineCoordinates(aiLoc)+"\n");
                    text.setId("moves");
                    if(textNumb > 9) {
                        recentMoves.getChildren().remove(0);
                    }
                    textNumb++; 
                    recentMoves.getChildren().add(text);
                }
                else {
                    aiLoc = aiLoc -100;
                    board2.getChildren().get(aiLoc).setId("aihit");
                    text = new Text("AI: Hit at " + Game.determineCoordinates(aiLoc)+"\n");
                    text.setId("moves");
                    if(textNumb > 9) {
                        recentMoves.getChildren().remove(0);
                    }
                    textNumb++; 
                    recentMoves.getChildren().add(text);
                }
                instructions.setText("Pick an unchosen square to sink the AI's ships.");
                int aiShips = game.checkAIGameOver();
                aiShipLabel.setText("AI Ships Remaining: " + aiShips); 
                if(aiShips == 0) {
                    instructions.setText("Congratulations! You sank all the AI's ships. Bully for you!");
                    gameOver(0);
                }
                int humanShips = game.checkHumanGameOver();
                humanShipLabel.setText("Human Ships Remaining: " + humanShips); 
                if(humanShips == 0) {
                    instructions.setText("The AI sank all of your ships. Game Over.");
                    gameOver(1); 
                }
                setAIShipId();
                setHumanShipId();
            });
        }
       
    }
    //sets the labels representing the ships to sunk if they are sunk
    private void setAIShipId() {
        int shipNumb = 0; 
        GridPane board = board1; 
        ArrayList<Ship> ships = game.getAI().getShips(); 
        ArrayList<Integer> numbs = new ArrayList<>();
        for(Ship ship: ships) {
            if(ship.getSunk() == true) {
                switch(shipNumb) {
                    case 0:
                        for(int j = 0; j<5; j++) {
                            numbs.add(game.getFinalAiPositions().get(j));
                        }
                        break;
                    case 1:
                        for(int j = 5; j<9; j++) {
                            numbs.add(game.getFinalAiPositions().get(j));
                        }
                        break;
                    case 2:
                        for(int j= 9; j<12; j++) {
                            numbs.add(game.getFinalAiPositions().get(j));    
                        }
                        break;
                    case 3:
                        for(int j= 12; j<15; j++) {
                            numbs.add(game.getFinalAiPositions().get(j));
                        }
                        break;
                    case 4:
                        for(int j= 15; j<17; j++) {
                            numbs.add(game.getFinalAiPositions().get(j));
                        }
                        break;
                    default: 
                        break; 
                }
                for(Integer num: numbs) {
                    board.getChildren().get(num).setId("sunk");
                }
            }
            shipNumb++;
        }
        
    } 

    private void setHumanShipId() {
        int shipNumb = 0; 
        GridPane board = board2; 
        
        ArrayList<Integer> numbs = new ArrayList<>();
        for(Ship ship: game.getHuman().getShips()) {
            if(ship.getSunk() == true) {
                switch(shipNumb) {
                    case 0:
                        for(int j = 0; j<5; j++) {
                            numbs.add(game.getFinalHumanPositions().get(j));
                        }
                        break;
                    case 1:
                        for(int j = 5; j<9; j++) {
                            numbs.add(game.getFinalHumanPositions().get(j));
                        }
                        break;
                    case 2:
                        for(int j= 9; j<12; j++) {
                            numbs.add(game.getFinalHumanPositions().get(j));    
                        }
                        break;
                    case 3:
                        for(int j= 12; j<15; j++) {
                            numbs.add(game.getFinalHumanPositions().get(j));
                        }
                        break;
                    case 4:
                        for(int j= 15; j<17; j++) {
                            numbs.add(game.getFinalHumanPositions().get(j));
                        }
                        break;
                    default: 
                        break; 
                }
                for(Integer num: numbs) {
                    board.getChildren().get(num).setId("sunk");
                }
            }
            shipNumb++;
        }
        
    } 
    
    //handles the end of the game.
    private void gameOver(int winner) {
        
        String win;
        if(winner == 0) {
            win = "You are ";
        }
        else {
            win = "AI is ";
        }
        
        game.printGameInfo(winner);
        
        
        List<String> choices = new ArrayList<>();
        choices.add("Start New Game");
        choices.add("Exit");


        ChoiceDialog<String> dialog = new ChoiceDialog<>("Exit", choices);
        dialog.setTitle(win + "the winner!");
        dialog.setHeaderText(win + "the winner.");
        dialog.setContentText("Would you like to exit or start a new Game");

        Optional<String> result = dialog.showAndWait();
        
        
        if (result.isPresent()){
            if(result.get().equals("Start New Game")) {
                try {
                    startNewGame();
                } catch (IOException ex) {
                    Logger.getLogger(BattleShipFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                stage.close();
                System.exit(0);
            }
            //choiceResultTextField.setText(result.get());
        }

    }
        
    @FXML
    private void handleAbout(Event event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("CS 3330 BattleShip Final Project");
        alert.setContentText("This application was developed by Christopher Whetsel for CS3330 as his finalproject at the University of Missouri. It was developed using Java 8 SE, JavaFX, and Scenebuilder. This is the classic game of battleship played against a basic ai. You begin by placing your ships on the board. Then proceed to geuss grid locations to try and hit the AI's ships. ");
        alert.showAndWait();
    }
    @FXML
    private void handleExitWarning(ActionEvent event) {
        
        List<String> choices = new ArrayList<>();
        choices.add("Yes");
        choices.add("No");


        ChoiceDialog<String> dialog = new ChoiceDialog<>("Exit", choices);
        dialog.setTitle("Exit Warning");
        dialog.setHeaderText("Make a Choice");
        dialog.setContentText("Are you sure you wish to exit?");

        Optional<String> result = dialog.showAndWait();
        
        // Traditional way to get the response value.
        if (result.isPresent()){
            if(result.get().equals("No")) {
                dialog.close();
            }
            else {
                dialog.close();
                stage.close();
            }

        }
    }
   
    @FXML
    private void handleNewGamePress(ActionEvent event) throws IOException {
        startNewGame();
    }
    
    //opens the oriiginal stage for choosing difficulty and closes the game board. 
    private void startNewGame() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BattleShipGameStart.fxml"));
        Parent root;
        root = (Parent)loader.load();
        BattleShipGameStartController controller = (BattleShipGameStartController)loader.getController();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        this.stage.close();
        stage.show();
        controller.ready(stage);
    }
    @FXML
    private void handleClosePress(ActionEvent event) {
        stage.close();
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
