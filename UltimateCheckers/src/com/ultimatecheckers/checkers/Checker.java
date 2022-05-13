package com.ultimatecheckers.checkers;

import javafx.event.EventTarget;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class Checker {

    private Color color;
    //row column or column row?
    private int row, column;
    private Circle checkerPiece;

    private double anchorX, anchorY;

    public Checker(Color color, int column, int row){
        this.color = color;
        this.column = column;
        this.row = row;
        checkerPiece = new Circle(15);
        checkerPiece.setFill(color);

        initDraggableHandler();
    }
    
    /*
    When dragged on a piece it gets the node below it, and sets the circle to that node location.

    */

    private void initDraggableHandler(){
        //Handle dragging

        /* Replace drag handler with mouse pressed handler, mouse released handler, 
        and 

        */

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
    checkerPiece.setOnMouseDragReleased(event -> {
        System.out.println("ENTERED A NEW TARGET!");

    });

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
        checkerPiece.setFill(color);
      
    }

    private void move(int x, int y){
    
    }

    public Circle getElement(){
        return checkerPiece;
    }

}
