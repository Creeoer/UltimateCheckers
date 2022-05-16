package com.ultimatecheckers.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ultimatecheckers.checkers.Checker;
import com.ultimatecheckers.checkers.Pawn;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape3D;

public class Board {
    
    private int SIZE;
    private Tile[][] board;
    private boolean previewMode;

    private GridPane checkerBoard;

    private Button newGame;
	private Button loadGame;
	private Button saveGame;


    public Board(int size){
        newGame = new Button("New Game");
        loadGame = new Button("Load Game");
        saveGame = new Button("Save Game");
        
        previewMode = false;
        SIZE = size;
        board = new Tile[SIZE][SIZE];

        GridPane checkerBoard = new GridPane();

        int counter = 0;
        // i is column, k is row
        for(int i = 0; i < SIZE; i++) {
            for(int k = 0; k < SIZE; k++){
                Tile tile = new Tile(k, i);

                if(counter == 0) {
                tile.setFill(Color.BLACK);
                counter = 1; 
                } else {
                    tile.setFill(Color.WHITE);
                    counter = 0;
                }

                board[i][k] = tile;
                checkerBoard.add(new StackPane(tile), i, k);
                //checkerBoard.add(new StackPane(checkerPiece), i, k);

                //Adds red checker pieces to top of board, it goes col row
                if(k < SIZE/3 && counter == 0){
                        Checker checkerPiece = new Pawn(Color.RED, i, k);
                        tile.setChecker(checkerPiece);
                        checkerBoard.add(new StackPane(checkerPiece), i, k);
                //if k is greater than 7, we know that all the pieces below black                  
                } else if (k > 7 && counter == 0){
                    Checker checkerPiece = new Pawn(Color.BLACK, i, k);
                    tile.setChecker(checkerPiece);
                    checkerBoard.add(new StackPane(checkerPiece), i, k);
            
                }

            }
        }

        this.checkerBoard = checkerBoard;
        

        checkerBoard.setOnMousePressed(event -> {
            int col = (int) ((event.getX() -2) / 50);
            int row = (int) ((event.getY() -2) / 50);

            // If the user clicks and is alerady in preview mdoe, we have to cleanup the
            // circles
            if (previewMode) {
                System.out.println("IN PREVIEW MODE!");

                removePreviewCircles();

/*
                checkerBoard.getChildren().forEach(node -> {
                    if(node instanceof StackPane){
                        //System.out.println("CIRCLE DETECTED");
                        StackPane pane = (StackPane) node;


                        pane.getChildren().forEach(innerNode -> {
                            if(innerNode instanceof Circle){
                                Circle preview = (Circle) innerNode;
                                if(preview.getFill() == Color.LIGHTGRAY){
                                    innerNode = null;
                                  //  checkerBoard.getChildren().remove(pane);
                                }
                            }
                        
                        });
                    }
            });
            */
/*

                        if(pane.getShape() instanceof Circle) {
                            System.out.println("DETECTED CIRCLES");
                        Circle previewCircle = (Circle) pane.getShape();

                        if(previewCircle.getFill() == Color.LIGHTGRAY) {
                            System.out.println("LIGHT GRAY CIRCLE DETECTED");
                            checkerBoard.getChildren().remove(previewCircle);
                        }
                    }
                }
                });
*/
                previewMode = false;
            }

            
            System.out.println("Col: " + col);
            System.out.println("Row: " + row);
            Tile tile = board[col][row];




            if(tile.hasChecker()) {
                //Make sure piece belongs to player
                Checker checker = tile.getChecker();

                if(checker.getColor() != Color.BLACK)
                    return;

                previewMode = true;

                //Create preview spots: SIZE
                List<Tile> possibleTiles = new ArrayList<>();
               //Check for left diagonol spot then rigth
               //If this works then it's valid
                if(col - 1 >= 0 && row - 1 >= 0) {
                    Tile leftDiagnol = board[col - 1][row - 1];

                    //Make sure our own piece is not in that spot!
                    if(leftDiagnol.hasChecker() && leftDiagnol.getChecker().getColor() == Color.BLACK)  
                         return;

                    possibleTiles.add(leftDiagnol);
                }

                //Now see if right diagnol tile is in game
                if(col + 1 < SIZE  && row - 1 >= 0) {
                    Tile rightDiagnol = board[col + 1][row - 1];

                    if(rightDiagnol.hasChecker() && rightDiagnol.getChecker().getColor() == Color.BLACK)  
                        return;

                    possibleTiles.add(rightDiagnol);


                }

                if(!possibleTiles.isEmpty()){

                    possibleTiles.forEach(t -> {
                        addPreviewCircle(tile, t);
                        System.out.println("There are possible tiles!");
                    });
            
                }


                /* Draw two temporary circles to represent where the player can go of size 9.

                Steps:
                1) First make sure the player clicks a piece that belongs to them. For now, they will be black
                2) If they clicked on a black checker piece, we have to generate preview circles. To do this , we have to generate get both the left and right diagonal spot
                Once we determined where those are, we have to make sure they're actually valid spots in the game. We also have to make sure there is no black piece on it!
                3) Once those both have been validated, we have to add mouse pressing listeners on both of those grey circles. If it's clicked, we move the piece
                4)Moving the piece is done by taking the checker object and removing it from the tile + board , then re-drawing it on the board + adding it to a new tile.
                */
                System.out.println("There is a piece here!");
                /*
                Checker piece = tile.getChecker();

                Circle previewMove = new Circle(5);
                previewMove.setFill(Color.LIGHTGRAY);

                checkerBoard.add(new StackPane(previewMove), col + 1, row - 1);
                //remove test
                checkerBoard.getChildren().remove(new StackPane(piece));

*/

            //    System.out.println(piece.getFill());
            }
            
                
        });
    
        
    }
/*
    private void moveChecker(Checker checkertoMove, Tile from, Tile to){

    }
*/
    private void addPreviewCircle(Tile from, Tile to)  {
        //Check if enemy checker is here then 
        Circle previewMove = new Circle(10);
        previewMove.setFill(Color.LIGHTGRAY);
        
        Checker piece = from.getChecker();

        previewMove.setOnMousePressed(event -> {
            System.out.println("MOVED PEICE!");
            //When a preview is clicked on, remove the original piece and then move it to the new tile
            from.removeChecker();
            checkerBoard.getChildren().remove(piece);

            to.setChecker(piece);
            checkerBoard.add(new StackPane(piece), to.col, to.row);
            

           removePreviewCircles();

        });

        
        checkerBoard.add(new StackPane(previewMove), to.col, to.row);

    }


    //Method to remove preview circles if applicable
    private void removePreviewCircles() {
        Iterator<Node> checkerBoardIterator = checkerBoard.getChildren().iterator();

while(checkerBoardIterator.hasNext()) {
    Node node = checkerBoardIterator.next();
    if(node instanceof StackPane){
        //System.out.println("CIRCLE DETECTED");
        StackPane pane = (StackPane) node;
        Iterator<Node> paneIterator = pane.getChildren().iterator();
        while(paneIterator.hasNext()) {
            Node innerNode = paneIterator.next();
            if(innerNode instanceof Circle){
                Circle preview = (Circle) innerNode;
                if(preview.getFill() == Color.LIGHTGRAY){
                    checkerBoardIterator.remove();
                // checkerBoard.getChildren().remove(pane);
                }
            }
        }
    }
}

    }

    public Parent getBoard(){
        return checkerBoard;
    }

    public Scene getScene(){

        newGame.setLayoutX(625);
        newGame.setLayoutY(200); 
        saveGame.setLayoutX(625);
        saveGame.setLayoutY(250);
        loadGame.setLayoutX(625);
        loadGame.setLayoutY(300);

        saveGame.setOnAction(event -> System.out.println("Hit!"));
        newGame.setOnAction(event -> System.out.println("Hit!!"));
        loadGame.setOnAction(event -> System.out.println("Hit!!!"));

        Group root = new Group();
        Scene scene = new Scene(root, 775, 560);
        root.getChildren().add(newGame);
        root.getChildren().add(checkerBoard);
        root.getChildren().add(saveGame);
        root.getChildren().add(loadGame);
        return scene;
    }

}
