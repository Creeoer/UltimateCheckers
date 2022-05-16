package com.ultimatecheckers.powerup;

import java.util.ArrayList;
import java.util.List;

import com.ultimatecheckers.main.Tile;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bomb extends Circle implements Powerup {

    private int SIZE;
    private int row, column;
    private Color color;
    private GridPane checkerPane;
    private Tile[][] board;
    // private Circle checkerPiece;

    public Bomb(int column, int row, Tile[][] board, int boardSize, GridPane checkerPane) {
        setRadius(10);
        this.column = column;
        this.row = row;
        this.board = board;
        this.SIZE = boardSize;
        this.checkerPane = checkerPane;
        setFill(Color.GREENYELLOW);

    }

    @Override
    public void use(Tile target) {
        // get all tiles around target
        List<Tile> bombTiles = new ArrayList<>();

        int col = target.col;
        int row = target.row;

        // Check left and right diagnol top and bottom
        /*
        */
        // right
        if (col + 1 < SIZE && row - 1 >= 0) {
            Tile rightDiagnol = board[col + 1][row - 1];
            bombTiles.add(rightDiagnol);
        }

        // left
        if (col - 1 >= 0 && row - 1 >= 0) {
            Tile leftDiagnol = board[col - 1][row - 1];
            bombTiles.add(leftDiagnol);
        }

        // left bottom
        if (col - 1 >= 0 && row + 1 < SIZE) {
            Tile leftBottom = board[col - 1][row + 1];
            bombTiles.add(leftBottom);
        }

        // right bottom
        if (col + 1 < SIZE && row + 1 < SIZE) {
            Tile rightBottom = board[col + 1][row + 1];
            bombTiles.add(rightBottom);
        }

        // Now do top, left, rigth, bottom tiles
        // top
        if (row - 1 >= 0) {
            Tile top = board[col][row - 1];
            bombTiles.add(top);
        }

        // left
        if (col - 1 >= 0) {
            Tile left = board[col - 1][row];
            bombTiles.add(left);
        }

        // right
        if (col + 1 < SIZE) {
            Tile right = board[col + 1][row];
            bombTiles.add(right);
        }

        // bottom
        if (row + 1 < SIZE) {
            Tile bottom = board[col][row + 1];
            bombTiles.add(bottom);
        }

        //If there is a piece in any of the tiles, remove the peice
        bombTiles.forEach(tile ->{
            if(tile.hasChecker()){
                checkerPane.getChildren().remove(new StackPane(tile.getChecker()));
                tile.removeChecker();
            }
        });

    }

    @Override
    public Circle getElement() {
        return this;
    }

}
