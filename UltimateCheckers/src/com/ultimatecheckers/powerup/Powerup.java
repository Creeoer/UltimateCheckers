package com.ultimatecheckers.powerup;

import com.ultimatecheckers.checkers.Checker;
import com.ultimatecheckers.main.Tile;

import javafx.scene.shape.Circle;

public interface Powerup {


    public void use(Tile target);

    public Circle getElement();
    
}
