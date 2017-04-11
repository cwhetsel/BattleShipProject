/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cjwgr5battleship;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
/**
 *
 * @author Christopher
 */

//handles the choosing of AI ship locations and determining where the AI is to fire next. 
public class AI extends Player{
    private static final int MIN = 1; 
    private static final int MAX = 100;
    private ArrayList<Integer> hits; 
    private ArrayList<Integer> misses; 
    private ArrayList<Integer> unknown; 
    private ArrayList<Integer> nextMoves;
    private ArrayList<Integer> lastFireAdj; 
    private ArrayList<Integer> humanPositions; 
    private boolean result = false; 
    private Difficulty difficulty; 
    private int turn = 0;
    private int lastFireLocation;
    
    public AI(Difficulty difficulty, ArrayList<Integer> humanPositions) {
        super(); 
        setShipPositions();
        this.difficulty = difficulty;
        hits = new ArrayList<>(17);
        misses = new ArrayList<>();
        nextMoves = new ArrayList<>();
        lastFireAdj = new ArrayList<>();
        setUpUnknown(); 
    }
    
    private void setUpUnknown() {
        unknown = new ArrayList<>(100);
        for(int i =1; i<= 100; i++) {
            unknown.add(i);
        }
    }
    
    private void setShipPositions() {
        int randDir;
        int randPos;
        for(int k = 1; k<6; k++) {
            randDir = ThreadLocalRandom.current().nextInt(1, 3);
            randPos = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
            super.positions.addAll(findPositions(randDir, randPos, k));
        }
        
        
    }
    //gets postions for AI ships
    private ArrayList<Integer> findPositions(int dir, int pos, int shipNum) {
            ArrayList<Integer> numbs = new ArrayList<>();
             
           
            int lastPos = pos; 
            if(dir == 1) {
                int posMod = (pos-1) % 10;
                switch(shipNum) {       
                    case 1:   
                        if(posMod > 5) {
                                while((pos%10) > 5) {
                                  pos--; 
                                }
                        }
                        for(int k = 0; k<5; k++) {
                            
                            if((positions.indexOf(pos) != -1)) {
                                numbs.clear();
                                pos = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
                                posMod = pos % 10; 
                                if(posMod > 5) {
                                while((pos%10) > 5) {
                                  pos--; 
                                }
                                }
                                k = -1;
                            }
                            else {
                                numbs.add(pos);
                                pos = pos+1; 
                            }
                        } 
                        break; 
                    case 2: 
                        if(posMod > 6) {
                                while((pos%10) > 6) {
                                  pos--; 
                                }
                        }
                        for(int k = 0; k<4; k++) {
                            
                            if((positions.indexOf(pos) != -1)) {
                                numbs.clear();
                                pos = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
                                posMod = pos % 10; 
                                if(posMod > 6) {
                                    while((pos%10) > 6) {
                                        pos--; 
                                    }
                                }
                                k = -1;
                            }
                            else {
                                numbs.add(pos);
                                pos = pos+1; 
                            }
                        } 
                        break; 
                    case 3:
                    case 4:
                        if(posMod > 7) {
                                while((pos%10) > 7) {
                                  pos--; 
                                }
                            }
                        for(int k = 0; k<3; k++) {
                            
                            if((positions.indexOf(pos) != -1)) {
                                numbs.clear();
                                pos = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
                                posMod = (pos-1) % 10; 
                                if(posMod > 7) {
                                while((pos%10) > 7) {
                                  pos--; 
                                }
                                }
                                k = -1;
                            }
                            else {
                                numbs.add(pos);
                                pos = pos+1; 
                            }
                        } 
                        break;
                    case 5: 
                        if(posMod > 8) {
                                while((pos%10) > 8) {
                                  pos--; 
                                }
                            }
                        for(int k = 0; k<2; k++) {
                            
                            if((positions.indexOf(pos) != -1)) {
                                numbs.clear();
                                pos = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
                                posMod = (pos-1) % 10; 
                                if(posMod > 8) {
                                while((pos%10) > 8) {
                                  pos--; 
                                }
                                }
                                k = -1;
                            }
                            else {
                                numbs.add(pos);
                                pos = pos+1; 
                            }
                        }
                        break; 
                    default:
                        System.out.println("Defaulted in AI set up ships method");
                        break; 
                        
                }
            }
            //direction is horizontal
            else {
                int posDiv = pos/10; 
                switch(shipNum) {       
                    case 1: 
                        if(posDiv > 5) {
                                while((pos/10) > 5) {
                                  pos = pos-10; 
                                }
                        }
                        for(int k = 0; k<5; k++) {
                            
                            if((positions.indexOf(pos) != -1)) {
                                numbs.clear();
                                pos = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
                                posDiv = pos / 10; 
                                if(posDiv > 5) {
                                while((pos/10) > 5) {
                                  pos = pos-10; 
                                }
                                }
                                k = -1;
                            }
                            else {
                                numbs.add(pos);
                                pos = pos+10; 
                            }
                        } 
                        break; 
                    case 2: 
                        if(posDiv > 6) {
                                while((pos/10) > 6) {
                                  pos = pos-10; 
                                }
                            }
                        for(int k = 0; k<4; k++) {
                            
                            if((positions.indexOf(pos) != -1)) {
                                numbs.clear();
                                pos = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
                                posDiv = pos / 10; 
                                if(posDiv > 6) {
                                while((pos/10) > 6) {
                                  pos = pos-10; 
                                }
                                }
                                k = -1;
                            }
                            else {
                                numbs.add(pos);
                                pos = pos+10; 
                            }
                        } 
                        break; 
                    case 3:
                    case 4:
                        if(posDiv > 7) {
                                while((pos/10) > 7) {
                                  pos = pos-10; 
                                }
                            }
                        for(int k = 0; k<3; k++) {
                            
                            if((positions.indexOf(pos) != -1)) {
                                numbs.clear();
                                pos = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
                                posDiv = pos / 10; 
                                if(posDiv > 7) {
                                while((pos/10) > 7) {
                                  pos = pos-10; 
                                }
                                }
                                k = -1;
                            }
                            else {
                                numbs.add(pos);
                                pos = pos+10; 
                            }
                        } 
                        break; 
                    case 5: 
                        if(posDiv > 8) {
                                while((pos/10) > 8) {
                                  pos = pos-10; 
                                }
                            }
                        for(int k = 0; k<2; k++) {
                            
                            if((positions.indexOf(pos) != -1)) {
                                numbs.clear();
                                pos = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
                                posDiv = pos / 10; 
                                if(posDiv > 8) {
                                while((pos/10) > 8) {
                                  pos = pos-10; 
                                }
                                }
                                k = -1;
                            }
                            else {
                                numbs.add(pos);
                                pos = pos+10; 
                            }
                        } 
                        break; 
                    default:
                        System.out.println("Defaulted in AI set up ships method");
                        break; 
                        
                }
            }
            return numbs; 
    }
    //controller for finding the next shot based on difficulty
    public int nextFireLocation() {
        
        switch(difficulty) {
            case EASY:
                return easyTurn();
            case MEDIUM:
                return mediumTurn();
            case IMPOSSIBLE:
                return impossibleTurn();
            default:
                return easyTurn(); 
        }
    }
    
    private int easyTurn() {
        int next = ThreadLocalRandom.current().nextInt(0, unknown.size());
        lastFireLocation = unknown.remove(next);
        return lastFireLocation;
    } 
    
    private int mediumTurn() {
        unknown.remove((Integer)lastFireLocation);
        while(nextMoves.remove((Integer)lastFireLocation) == true) {
        
        } 
        
        if(result == true) {
            findLastFireAdj();
            hits.add(lastFireLocation);
            nextMoves.addAll(lastFireAdj);
            lastFireLocation = nextMoves.remove(nextMoves.size()-1);
            lastFireAdj.clear();
        }
        else { 
            misses.add(lastFireLocation);
            
            if( nextMoves.isEmpty()) {
                int next = ThreadLocalRandom.current().nextInt(0, unknown.size());
                lastFireLocation = unknown.remove(next);
            }
            else {
                lastFireLocation = nextMoves.remove(0);
            }
        }
        return lastFireLocation; 
    }
    
    private int impossibleTurn() {
        int next = humanPositions.get(turn);
        turn++;
        return next; 
    }
    //finds the tiles adjacent to the hit so they can be added to the next moves ArrayList
    private void findLastFireAdj() {
        int posDiv = (lastFireLocation-1) / 10; 
        int posMod = lastFireLocation % 10; 
        
        if(posDiv == 0) {
            if(posMod == 1) {
                lastFireAdj.add(lastFireLocation+10);
                lastFireAdj.add(lastFireLocation+1);
            }
            else if(posMod == 0) {
                lastFireAdj.add(lastFireLocation+10);
                lastFireAdj.add(lastFireLocation-1);
            }
            else {
                lastFireAdj.add(lastFireLocation+10);
                lastFireAdj.add(lastFireLocation+1);
                lastFireAdj.add(lastFireLocation-1);
            }
        }
        else if(posDiv == 9) {
             if(posMod == 1) {
                lastFireAdj.add(lastFireLocation-10);
                lastFireAdj.add(lastFireLocation+1);
            }
            else if(posMod == 0) {
                lastFireAdj.add(lastFireLocation-10);
                lastFireAdj.add(lastFireLocation-1);
            }
            else {
                lastFireAdj.add(lastFireLocation-10);
                lastFireAdj.add(lastFireLocation+1);
                lastFireAdj.add(lastFireLocation-1);
            }
        }
        else {
            if(posMod == 1) {
                lastFireAdj.add(lastFireLocation-10);
                lastFireAdj.add(lastFireLocation+10);
                lastFireAdj.add(lastFireLocation +1);
            }
            else if(posMod == 0) {
                lastFireAdj.add(lastFireLocation-10);
                lastFireAdj.add(lastFireLocation+10);
                lastFireAdj.add(lastFireLocation-1);
            }
            else {
                lastFireAdj.add(lastFireLocation-10);
                lastFireAdj.add(lastFireLocation+10);
                lastFireAdj.add(lastFireLocation-1);
                lastFireAdj.add(lastFireLocation+1);
            }
        }
        
        
        ArrayList<Integer> removal = new ArrayList<>();
        for(Integer num: lastFireAdj) {
            if(misses.indexOf(num) != -1 || hits.indexOf(num) != -1) {
                removal.add(num);
            } 
        }
        if(!removal.isEmpty()) {
            lastFireAdj.removeAll(removal); 
        }
        
    }
    public Difficulty getDifficulty() {
        return difficulty; 
    }
    public void setHumanPositions(ArrayList<Integer> pos) {
        humanPositions = pos; 
    }
    
    //restult is the result of the last shot: a hit or miss
    public void setResult(boolean result) {
        this.result = result;
    }
}
