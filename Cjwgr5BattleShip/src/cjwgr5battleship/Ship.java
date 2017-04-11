/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cjwgr5battleship;

/**
 *
 * @author Christopher
 */
public class Ship {
    private final String name; 
    private final int length; 
    private boolean sunk; 
    
    public Ship(String name, int length) {
        this.name = name;
        this.length = length; 
        this.sunk = false; 
    }
    
    public String getName() {
        return name;
    }
    public int getLength() {
        return length; 
    }
    
    public boolean getSunk() {
        return sunk; 
    }
    
    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }
}
