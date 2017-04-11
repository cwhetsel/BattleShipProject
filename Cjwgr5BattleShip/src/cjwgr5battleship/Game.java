/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cjwgr5battleship;

import static cjwgr5battleship.Difficulty.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author Christopher
 */

//Class that handles the Human and AI objects as the play the game of BattleShip, loads the games Won 
//info from gameInfo.txt, and prints the result of the game to gameInfo.txt. Also handles the placing of human ships and checking for when the game is over. 
public class Game {
    private static  final double GRIDWIDTH = 40.0; 
    private static final double GRIDWIDTHSTART = 283.0;
    private static final double GRIDHEIGHT = 45.0;
    private static final double GRIDHEIGHTSTART = 84.0;
    private static final int GRIDSIZE = 10;
    private static final String FILE = "gameInfo.txt";
    
    private int impossibleAiWins; 
    private int impossibleHumanWins;
    private int mediumAiWins; 
    private int mediumHumanWins; 
    private int easyAiWins; 
    private int easyHumanWins; 
    private double easyPercentage; 
    private double mediumPercentage;
    private double impossiblePercentage; 
    
    private Human human; 
    private AI ai; 
    private ArrayList<Integer> humanPositions;
    private ArrayList<Integer> aiPositions;
    private ArrayList<Integer> humanFinalPositions;
    private ArrayList<Integer> aiFinalPositions;
    private ArrayList<Integer> picks; 
    private int checkShipNumber = 0;
    
    public Game() {
        readFileGameWinInfo(); 
    }
    
    public Game(ArrayList<Integer> picks, Difficulty difficulty) {
        human = new Human(); 
        ai = new AI(difficulty, null);
        humanPositions = human.getPositions();
        aiPositions = ai.getPositions();
        aiFinalPositions = new ArrayList<>(); 
        aiFinalPositions.addAll(ai.getPositions());
        this.picks = picks;
        
    }
    

    public boolean checkPicks() {
        picks.sort(null);
        int size = picks.size();
        int diff = picks.get(1) - picks.get(0); 
        if(diff == 0) {
            
            return false;
        }
        if(size == 2) {
            if(diff != 1 && diff != 10) {
                
                return false;
            }
        }
        for(int i=1; i < size-1; i++) {
            if(picks.get(i+1) - picks.get(i) != diff) {
                
                return false;
            }
        }
        human.addPosistions(picks);
        humanFinalPositions = new ArrayList<>();
        humanFinalPositions.addAll(human.getPositions());
        checkShipNumber++;
        if(checkShipNumber == 5) {
            ai.setHumanPositions(human.getPositions());
        }
        return true; 
    }
    
    public boolean isHitAI(int location) {
        int index = aiPositions.indexOf(location); 
        if(index == -1) {
            return false; 
        }
        else {
            aiPositions.set(index, -2); 
            return true; 
        }
    }
    private boolean isHitHuman(int location) {
         int index = humanPositions.indexOf(location); 
        if(index == -1) {
            return false; 
        }
        else {
            humanPositions.set(index, -2); 
            return true; 
        }
    }
    
    public int aiTurn() {
        int loc; 
        if(isHitHuman(loc = ai.nextFireLocation())) {
            ai.setResult(true);
            loc = loc + 100;
        } 
        else {
            ai.setResult(false);
            loc = loc + 200;
        }
        return loc; 
    }
    public static int determineGridLocation(double x, double y) {
        int location; 
        double xloc = x-GRIDWIDTHSTART;
        double yloc = y-GRIDHEIGHTSTART;
        int xpos = (int)(xloc / GRIDWIDTH); 
        int ypos = (int)(yloc / GRIDHEIGHT); 
        location = ((xpos)*10) + ypos +1;
        if(location > 100) {
            location -= 10; 
        }
        return location; 
    }
    
    public static String determineCoordinates(int location) {
        String coord;
        int locmod = location%GRIDSIZE;
        int y = (int)(location/GRIDSIZE); 
        switch(locmod) {
            case(1):
                coord = "A";
                break;
            case(2):
                coord = "B";
                break;
            case(3):
                coord = "C";
                break;
            case(4):
                coord = "D";
                break;
            case(5):
                coord = "E";
                break;
            case(6):
                coord = "F";
                break;
            case(7):
                coord = "G";
                break;
            case(8):
                coord = "H";
                break;
            case(9):
                coord = "I";
                break;
            case(0):
                coord = "J";
                break;
            default: 
                coord = "default";
                break;
        }
        return coord + y; 
    }
    
    public final void readFileGameWinInfo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
             String line;
             for(int i = 1; i <= 6; i++) {
                 line = reader.readLine();
                 switch(i) {
                     case 1: 
                        easyHumanWins = Integer.parseInt(line);
                        break;
                    case 2: 
                        easyAiWins = Integer.parseInt(line);
                        break;
                    case 3: 
                        mediumHumanWins = Integer.parseInt(line);
                        break;
                    case 4: 
                        mediumAiWins = Integer.parseInt(line);
                        break;
                    case 5: 
                        impossibleHumanWins = Integer.parseInt(line);
                        break;
                    case 6: 
                        impossibleAiWins = Integer.parseInt(line);
                        break;
                    default: 
                        break;
                }
                 
             }
             reader.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        finally {
            impossiblePercentage = (double)impossibleHumanWins /(impossibleHumanWins+impossibleAiWins);
             mediumPercentage = (double)mediumHumanWins/(mediumHumanWins+mediumAiWins);
             easyPercentage = (double)easyHumanWins/(easyHumanWins+easyAiWins);
             
        }
       
    }
    
    public void printGameInfo(int winner) {
        //human winner
        if(winner == 0) {
            switch(ai.getDifficulty()) {
                     case EASY: 
                        easyHumanWins++;
                        break;
                    case MEDIUM: 
                        mediumHumanWins++;
                        break;
                    case IMPOSSIBLE: 
                        impossibleHumanWins++;
                        break;
                    default: 
                        break;
            }
        }
        //ai winner
        else {
            switch(ai.getDifficulty()) {
                    case EASY: 
                        easyAiWins++;
                        break;
                    case MEDIUM: 
                        mediumAiWins++;
                        break;
                    case IMPOSSIBLE: 
                        impossibleAiWins++;
                        break;
                    default: 
                        break;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
            String line = easyHumanWins + "\n" + easyAiWins +"\n" +mediumHumanWins + "\n" + mediumAiWins + "\n"+impossibleHumanWins+ "\n"+ impossibleAiWins;
            System.out.println(line);
            writer.write(line);
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
    
    public int checkHumanGameOver() {
        human.checkShipsSunk();
        return human.getShipsRemaining();
    }
    public int checkAIGameOver() {
        ai.checkShipsSunk();
        return ai.getShipsRemaining();
    }
    
    public ArrayList<Integer> getFinalAiPositions() {
        return aiFinalPositions;
    }
    public ArrayList<Integer> getFinalHumanPositions() {
        return humanFinalPositions;
    }
    
    public double getImpossiblePercentage() {
        return impossiblePercentage; 
    }
    public double getMediumPercentage() {
        return mediumPercentage;
    }
    public double getEasyPercentage() {
        return easyPercentage;
    }
   
    public int getNextShipNumber() {
        return checkShipNumber; 
    }
    public int getNextShipLength() {
        if(checkShipNumber == 5) {
            return -1;
        }
        return human.getShips().get(checkShipNumber).getLength();
    }
    public Human getHuman() {
        return human; 
    }
    public AI getAI() {
        return ai; 
    }
    public ArrayList<Integer> getHumanPositions() {
        return humanPositions; 
    }
}
