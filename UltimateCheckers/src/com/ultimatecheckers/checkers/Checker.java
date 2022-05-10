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

    public Checker(Color color, int row, int column){
        this.color = color;
        this.row = row;
        this.column = column;
        checkerPiece = new Circle(15);
        checkerPiece.setFill(color);

        initDraggableHandler();
    }
    

    private void initDraggableHandler(){
        //Handle dragging

     checkerPiece.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            //This is done so that when the mouse is released, it sees the tile under the checker piece
        //    checkerPiece.setMouseTransparent(true);
        }
        );

        checkerPiece.setOnMouseDragged(event -> {
            checkerPiece.setTranslateX(event.getSceneX() - anchorX);
            checkerPiece.setTranslateY(event.getSceneY() - anchorY);
        
            anchorX = checkerPiece.getTranslateX();
            anchorY = checkerPiece.getTranslateY();
        //    checkerPiece.startFullDrag();
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

    public Circle getElement(){
        return checkerPiece;
    }

}
