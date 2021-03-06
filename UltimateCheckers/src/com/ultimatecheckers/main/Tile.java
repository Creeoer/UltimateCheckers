package com.ultimatecheckers.main;

import com.ultimatecheckers.checkers.Checker;
import com.ultimatecheckers.powerup.Powerup;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
 
    
    /*
    The purpose of this class is store the piece it has on it 
    */

    private Checker checker;
    public int row, col;
    private Powerup powerup;

    public Tile(int row, int col){
        this.row = row;
        this.col = col;
        setWidth(50);
        setHeight(50);
        setStroke(Color.WHITE);
    }

    public void setColor(Color color){
        setFill(color);
    }


    public void setChecker(Checker checker){
        this.checker = checker;
    }

    public void removeChecker(){
        this.checker = null;
    }

    public boolean hasChecker(){
        return checker != null;
    }

    public Checker getChecker(){
        return checker;
    }

    public boolean hasPowerup(){
        return powerup != null;
    }

    public Powerup getPowerup(){
        return this.powerup;
    }

    public void setPowerup(Powerup powerup){
        this.powerup = powerup;
    }


}
