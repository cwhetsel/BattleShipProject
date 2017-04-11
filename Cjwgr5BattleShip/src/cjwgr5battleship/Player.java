/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cjwgr5battleship;

import java.util.ArrayList;

/**
 *
 * @author Christopher
 */

//abstract class that implements methods that all players should have. It is abstract because 
//i dont want a player, I only want Ai's and Humans to be able to be created
public abstract class Player {
    private static final int SHIPCAP = 5; 
    private static final int POSCAP = 17;
    private ArrayList<Ship> ships;
    protected ArrayList<Integer> positions; 
    private int shipsRemaining = 5;
    
    
    public Player() {
        setUpShips();
        positions = new ArrayList<>(POSCAP);
    }
    
    private void setUpShips() {
        ships = new ArrayList<>(SHIPCAP); 
        ships.add(new Ship("Carrier", 5)); 
        ships.add(new Ship("Battleship", 4));
        ships.add(new Ship("Cruiser", 3));
        ships.add(new Ship("Submarine", 3));
        ships.add(new Ship("Destroyer", 2));
    }
    
    //checks how many ships are sunk 
    public void checkShipsSunk() {
        int sunkships = 0;
        int pos = 0; 
        Ship ship;
        for(int i =0; i<5; i++) {
            ship = ships.get(i); 
            if(ship.getSunk() == true) {
                sunkships++;
                pos = pos + ship.getLength(); 
            }
            else{
                ship.setSunk(isSunk(ship.getLength(), pos));
                pos = pos + ship.getLength(); 
                if(ship.getSunk() == true) {
                    sunkships++;
                }
            }
        }
        shipsRemaining = 5 - sunkships; 
    }
    
    private boolean isSunk( int length, int pos) {
        int yes =0;
        for(int i = 0; i<length; i++) {
            if(positions.get(i + pos) == -2) {
                yes++; 
            }
        }
        return yes == length; 
    }
    
    public int getShipsRemaining() {
        return shipsRemaining; 
    }
    public ArrayList<Ship> getShips() {
        return ships;
    }
    public ArrayList<Integer> getPositions() {
        return positions;
    }
    
    public void addPosistions(ArrayList<Integer> pos) {
        positions.addAll(pos);
    }
}
