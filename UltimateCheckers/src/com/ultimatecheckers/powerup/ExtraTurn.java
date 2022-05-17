package com.ultimatecheckers.powerup;

import com.ultimatecheckers.main.Tile;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ExtraTurn extends Circle implements Powerup {


    public ExtraTurn(){
        setRadius(10);
        setFill(Color.CYAN);

    }
    @Override
    public void use(Tile target) {
        //There does not need to be any logic in here
        
    }

    
    @Override
    public Circle getElement() {
        return this;
    }
    



}
