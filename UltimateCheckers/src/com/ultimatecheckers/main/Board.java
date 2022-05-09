package com.ultimatecheckers.main;

import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Board {
    
    private int SIZE;
    private int[][] board;

    private GridPane checkerBoard;

    public Board(int size){

        SIZE = size;
        

        GridPane checkerBoard = new GridPane();


        int counter = 0;
        for(int i = 0; i < SIZE; i++) {
            for(int k = 0; k < SIZE; k++){
                Rectangle tile = new Rectangle(50, 50);
                //Circle checkerPiece = new Circle(15);
                //change
                tile.setStroke(Color.WHITE);
                if(counter == 0) {
                tile.setFill(Color.BLACK);
                //checkerPiece.setFill(Color.RED);
                counter = 1; 
                } else {
                    tile.setFill(Color.WHITE);
                    //checkerPiece.setFill(Color.WHITE);
                    counter = 0;
                }
                checkerBoard.add(new StackPane(tile), i, k);
                //checkerBoard.add(new StackPane(checkerPiece), i, k);
            }
        }
        for(int x = 0; x < SIZE; x++){
            for(int a = 0; a < SIZE; a++){
                Circle checkerPiece = new Circle(15);
                if(a < SIZE/3){
                    if(counter == 0) {
                        checkerPiece.setFill(Color.RED);
                        counter = 1; 
                    }
                    else {
                        //checkerPiece.setFill(Color.BLACK);
                        counter = 0;
                    }
                    checkerBoard.add(new StackPane(checkerPiece), x, a);
                }
            }
        }
        for(int x = 0; x < SIZE; x++){
            for(int a = 0; a < SIZE; a++){
                Circle checkerPiece = new Circle(15);
                if(a > 7){
                    if(counter == 1) {
                        checkerPiece.setFill(Color.BLACK);
                        counter = 0; 
                    }
                    else {
                        //checkerPiece.setFill(Color.BLACK);
                        counter = 1;
                    }
                    checkerBoard.add(new StackPane(checkerPiece), x, a);
                }
            }
        }
        this.checkerBoard = checkerBoard;
    }

    public Parent getBoard(){
        return checkerBoard;
    }
}
