package com.ultimatecheckers.checkers;

import com.ultimatecheckers.main.BoardUtils;

import javafx.event.EventTarget;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public abstract class Checker extends Circle{

    private Color color;
    //row column or column row?
    private int row, column;
    //private Circle checkerPiece;

    private double anchorX, anchorY;

    public Checker(Color color, int column, int row){
        setRadius(15);

        this.color = color;
        this.column = column;
        this.row = row;
        setFill(color);

        initDraggableHandler();
    }
    
    /*
    When dragged on a piece it gets the node below it, and sets the circle to that node location.

    */

    private void initDraggableHandler(){

        setOnMousePressed(event -> {
            //Print out checker piece's location
            double sceneX = event.getSceneX();
            double sceneY = event.getSceneY();
            Circle tile = ((Circle) event.getTarget());

            System.out.println("color: " + color);
        //    System.out.println("Checker X location" + BoardUtils.convertToBoardLoc(checkerPiece.getLayoutX()));
      //      System.out.println("Checker Y location" + BoardUtils.convertToBoardLoc(checkerPiece.getLayoutY()));
            System.out.println("Checker X location" + getLayoutX());
            System.out.println("Checker Y location" + getLayoutY());

        });

        //Handle dragging

        /* Replace drag handler with mouse pressed handler, mouse released handler, 
        and 

        */

        /*

    checkerPiece.setOnMouseClicked(evenet -> {

    });


     checkerPiece.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
       //     event.setDragDetect(true);
            System.out.println("PRESSED ON PIECE!");
            //This is done so that when the mouse is released, it sees the tile under the checker piece
        //    checkerPiece.setMouseTransparent(true);
     }
        );

    checkerPiece.setOnMouseDragged(event -> {
        checkerPiece.setMouseTransparent(true);
        checkerPiece.setTranslateX(event.getSceneX() - anchorX);
        checkerPiece.setTranslateY(event.getSceneY() - anchorY);
        System.out.println("NODE ENTERED");
    });

 /*   checkerPiece.setOnDragDetected(event -> {
            checkerPiece.startFullDrag();  
        });

        */
        /*
    checkerPiece.setOnMouseDragReleased(event -> {
        System.out.println("ENTERED A NEW TARGET!");

    });
*/
        
/*
         checkerPiece.set(event -> {
             StackPane tile = ((StackPane) event.getTarget());
             checkerPiece.setTranslateX(tile.getTranslateX());
             checkerPiece.setTranslateY(tile.getTranslateY());
             
             //Update anchors
           //  anchorX = tile.getTranslateX();
          //   anchorY = tile.getTranslateY();
             checkerPiece.setMouseTransparent(false);
         });

*/
    }


    public void setColorAndFill(Color color){
        this.color = color;
        setFill(color);
      
    }

    public void move(int newCol, int newRow){
        this.column = newCol;
        this.row = newRow;
    
    }

    public Color getColor(){
        return this.color;
    }

 

}
