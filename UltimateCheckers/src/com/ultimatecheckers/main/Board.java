package com.ultimatecheckers.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.ultimatecheckers.checkers.Checker;
import com.ultimatecheckers.checkers.King;
import com.ultimatecheckers.checkers.Pawn;
import com.ultimatecheckers.powerup.Bomb;
import com.ultimatecheckers.powerup.ExtraTurn;
import com.ultimatecheckers.powerup.Powerup;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Board {

    private int SIZE; //SIZE OF BOARD 
    private Tile[][] board; //store all the tiles 
    private boolean previewMode; //Whether or not the player is previewing moves

    private GridPane checkerBoard; //checkerboard in gridpane form
    private boolean extraTurn; //Whether or not the extraTurn powerup has been used
    private Group root;

    private Button newGame;
    private Button loadGame;
    private Button saveGame;


    public Board(int size) {
        root = new Group();
        newGame = new Button("New Game");
        loadGame = new Button("Load Game");
        saveGame = new Button("Save Game");

        previewMode = false;
        extraTurn = false;
        SIZE = size;
        board = new Tile[SIZE][SIZE];

        GridPane checkerBoard = new GridPane();

        int counter = 0;
        // i is column, k is row
        for (int i = 0; i < SIZE; i++) {
            for (int k = 0; k < SIZE; k++) {
                Tile tile = new Tile(k, i);

                if (counter == 0) {
                    tile.setFill(Color.BLACK);
                    counter = 1;
                } else {
                    tile.setFill(Color.WHITE);
                    counter = 0;
                }

                board[i][k] = tile;
                checkerBoard.add(new StackPane(tile), i, k);
                // checkerBoard.add(new StackPane(checkerPiece), i, k);

                // Adds red checker pieces to top of board, it goes col row
                if (k < SIZE / 3 && counter == 0) {
                    Checker checkerPiece = new Pawn(Color.RED, i, k);
                    tile.setChecker(checkerPiece);
                    checkerBoard.add(new StackPane(checkerPiece), i, k);
                    // if k is greater than 7, we know that all the pieces below black
                } else if (k > 7 && counter == 0) {
                    Checker checkerPiece = new Pawn(Color.BLACK, i, k);
                    tile.setChecker(checkerPiece);
                    checkerBoard.add(new StackPane(checkerPiece), i, k);

                }
                 //add powerups
                 if(k < 6 && k > SIZE / 3){
                     //Random powerup on random part of board
                     Random rand = new Random();
                     if(rand.nextInt(4) == 2 && tile.getFill() != Color.BLACK){
                         Random powerupRand = new Random();
                         switch(powerupRand.nextInt(0, 2)) {
                             case 0:
                             Bomb bomb = new Bomb(i, k, board, SIZE, checkerBoard);
                             tile.setPowerup(bomb);
                             checkerBoard.add(new StackPane(bomb), i, k);
                             break;

                             case 1:
                             ExtraTurn eTurn = new ExtraTurn();
                             System.out.println("adding eturn");
                             tile.setPowerup(eTurn);
                             checkerBoard.add(new StackPane(eTurn), i, k);
                             break;
                         }
                     }
                 }
            }


        }

        this.checkerBoard = checkerBoard;

        checkerBoard.setOnMousePressed(event -> {

            checkforWinConditions();
            int col = (int) ((event.getX() - 2) / 50);
            int row = (int) ((event.getY() - 2) / 50);

            // If the user clicks and is alerady in preview mdoe, we have to cleanup the
            // circles
            if (previewMode) {
                System.out.println("IN PREVIEW MODE!");

                removePreviewCircles();
                previewMode = false;
            }

            System.out.println("Col: " + col);
            System.out.println("Row: " + row);
            Tile tile = board[col][row];

            if (tile.hasChecker()) {
                // Make sure piece belongs to player
                Checker checker = tile.getChecker();

                if (checker.getColor() != Color.BLACK)
                    return;

                previewMode = true;

                // Create preview spots: SIZE
                List<Tile> possibleTiles = new ArrayList<>();

     if(checker instanceof King){

        if (col - 1 >= 0 && row + 1 < SIZE) {
            Tile leftDiagnol = board[col - 1][row + 1];

            // Make sure our own piece is not in that spot!
            if (leftDiagnol.hasChecker() && leftDiagnol.getChecker().getColor() != Color.BLACK) {

                if (leftDiagnol.hasChecker() && (col - 2 >= 0 && row + 2 < SIZE)
                        && !board[col - 2][row + 2].hasChecker()) {
                    Tile attackTile = board[col - 2][row + 2];
                    possibleTiles.add(attackTile);
                }
            } else if (!leftDiagnol.hasChecker()) {
                possibleTiles.add(leftDiagnol);

            }
        }

        if (col + 1 < SIZE && row + 1 < SIZE) {
            Tile rightDiagnol = board[col + 1][row + 1];

            if (rightDiagnol.hasChecker() && rightDiagnol.getChecker().getColor() != Color.BLACK) {
                // We know this is an enemy piece, so check if the diagnol of THAT piece has a
                // piece or if its on the board
                // If the diagnol exists, AND it has no checker, then we can proceed with the
                // possible tile add
                if (rightDiagnol.hasChecker() && (col + 2 < SIZE && row + 2 < SIZE)
                        && !board[col + 2][row + 2].hasChecker()) {
                    Tile attackTile = board[col + 2][row + 2];
                    possibleTiles.add(attackTile);
                }
            } else if (!rightDiagnol.hasChecker()) {
                possibleTiles.add(rightDiagnol);
            }
        }        
     }
    
                // Check for left diagonol spot then rigth
                // If this works then it's valid
                if (col - 1 >= 0 && row - 1 >= 0) {
                    Tile leftDiagnol = board[col - 1][row - 1];

                    // Make sure our own piece is not in that spot!
                    if (leftDiagnol.hasChecker() && !(leftDiagnol.getChecker().getColor() == Color.BLACK)) {

                        // We know this is an enemy piece, so check if the diagnol of THAT piece has a
                        // piece or if its on the board
                        // If the diagnol exists, AND it has no checker, then we can proceed with the
                        // possible tile add
                        if (leftDiagnol.hasChecker() && (col - 2 >= 0 && row - 2 >= 0)
                                && !board[col - 2][row - 2].hasChecker()) {
                            Tile attackTile = board[col - 2][row - 2];
                            possibleTiles.add(attackTile);
                        }
                    } else if (!leftDiagnol.hasChecker()) {
                        possibleTiles.add(leftDiagnol);
                    }
                }
                // Now see if right diagnol tile is in game
                if (col + 1 < SIZE && row - 1 >= 0) {
                    Tile rightDiagnol = board[col + 1][row - 1];

                    if (rightDiagnol.hasChecker() && !(rightDiagnol.getChecker().getColor() == Color.BLACK)) {

                        // We know this is an enemy piece, so check if the diagnol of THAT piece has a
                        // piece or if its on the board
                        // If the diagnol exists, AND it has no checker, then we can proceed with the
                        // possible tile add
                        if (rightDiagnol.hasChecker() && (col + 2 < SIZE && row - 2 >= 0)
                                && !board[col + 2][row - 2].hasChecker()) {
                            Tile attackTile = board[col + 2][row - 2];
                            possibleTiles.add(attackTile);
                        }
                    } else if (!rightDiagnol.hasChecker()) {
                        possibleTiles.add(rightDiagnol);
                    }
                }

                if (!possibleTiles.isEmpty()) {

                    possibleTiles.forEach(t -> {

                        if (Math.abs(tile.row - t.row) > 1 || Math.abs(tile.col - t.col) > 1) {

                            addPreviewCircle(tile, t, true);
                        } else {
                            addPreviewCircle(tile, t, false);
                        }
                        System.out.println("There are possible tiles!");
                    });

                }

                /*
                 * Draw two temporary circles to represent where the player can go of size 9.
                 * 
                 * Steps:
                 * 1) First make sure the player clicks a piece that belongs to them. For now,
                 * they will be black
                 * 2) If they clicked on a black checker piece, we have to generate preview
                 * circles. To do this , we have to generate get both the left and right
                 * diagonal spot
                 * Once we determined where those are, we have to make sure they're actually
                 * valid spots in the game. We also have to make sure there is no black piece on
                 * it!
                 * 3) Once those both have been validated, we have to add mouse pressing
                 * listeners on both of those grey circles. If it's clicked, we move the piece
                 * 4)Moving the piece is done by taking the checker object and removing it from
                 * the tile + board , then re-drawing it on the board + adding it to a new tile.
                 */
                System.out.println("There is a piece here!");
    
            }

        });

    }

    private void checkforWinConditions() {

        Text winText = new Text();
        winText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        winText.setFill(Color.GRAY);
        winText.setX(570);
        winText.setY(550);
      

        List<Checker> redCheckers = new ArrayList<>();
        List<Checker> blackCheckers = new ArrayList<>();

        for(int i = 0; i < SIZE; i++){
            for(int k = 0; k < SIZE; k++){
                Tile tile = board[i][k];
                if(tile.hasChecker()){
                    Checker piece = tile.getChecker();
                    if(piece.getColor() == Color.RED) { 
                        redCheckers.add(piece);
                    } else {
                        blackCheckers.add(piece);
                    }
                }
            }
        }


        //Player won
        if(redCheckers.isEmpty()){
            winText.setText("Player has won!");
        } else if(blackCheckers.isEmpty()){
            //AI won
            winText.setText("Computer has won!");
        }

        root.getChildren().add(winText);

    }

    /*
     * private void moveChecker(Checker checkertoMove, Tile from, Tile to){
     * 
     * }
     */
    private void addPreviewCircle(Tile from, Tile to, boolean attackMove) {
        // Check if enemy checker is here then
        Circle previewMove = new Circle(10);
        previewMove.setFill(Color.LIGHTGRAY);

        Checker piece = from.getChecker();

        previewMove.setOnMousePressed(event -> {
            System.out.println("MOVED PEICE!");
          
            // When a preview is clicked on, remove the original piece and then move it to
            // the new tile
            from.removeChecker();
            checkerBoard.getChildren().remove(piece);

            // If this is an attack move, we know that we must remove the red checker piece
            // in between the from and to tile
            // So you must determine if it is a left or right diagnol by seeing which tile
            // has the red checker piece
            // Then get rid of the enemy piece
            /*
             * diagnol right:
             * add 1 to col, minus 1 from row
             * diagnol left:
             * minus 1 col, minus 1 row
             */
            try {
            if (attackMove) {
                // Right diagnol
                if (board[from.col + 2][from.row - 2] != null && board[from.col + 2][from.row-2]== to) {
                    Tile enemyTile = board[from.col + 1][from.row - 1];
                    checkerBoard.getChildren().remove(new StackPane(enemyTile.getChecker()));
                    enemyTile.removeChecker();
                    System.out.println("Successfully killed enemy checker piece");
                } else if (board[from.col - 2][from.row - 2] != null && board[from.col -2 ][from.row -2]  == to){
                    // Left diagnol
                    Tile enemyTile = board[from.col - 1][from.row - 1];
                    checkerBoard.getChildren().remove(new StackPane(enemyTile.getChecker()));
                    enemyTile.removeChecker();
                    System.out.println("Successfully killed enemy checker piece");

                }else if(board[from.col + 2][from.row + 2] != null && board[from.col + 2][from.row + 2] == to) {
                    Tile enemyTile = board[from.col + 1][from.row + 1];
                    checkerBoard.getChildren().remove(new StackPane(enemyTile.getChecker()));
                    enemyTile.removeChecker();
                    System.out.println("Successfully killed enemy checker piece");
                }else if(board[from.col - 2][from.row + 2] != null && board[from.col- 2][from.row + 2] == to) {
                    Tile enemyTile = board[from.col - 1][from.row + 1];
                    checkerBoard.getChildren().remove(new StackPane(enemyTile.getChecker()));
                    enemyTile.removeChecker();
                    System.out.println("Successfully killed enemy checker piece");
                }

            }
        } catch (Exception e){

        }

            
            to.setChecker(piece);
            checkerBoard.add(new StackPane(piece), to.col, to.row);

            removePreviewCircles();

            if(to.row == 0 || to.row == SIZE -1 && !(to.getChecker() instanceof King)){
                King king = new King(Color.BLACK, to.col, to.row);
                checkerBoard.getChildren().remove(new StackPane(to.getChecker()));
                to.removeChecker();
                to.setChecker(king);
                checkerBoard.add(new StackPane(king), to.col, to.row);
            }

            // powerup detected, use on piece
            if (to.hasPowerup()) {
                Powerup powerup = to.getPowerup();
                powerup.use(to);

                if(powerup instanceof ExtraTurn)
                    extraTurn = true;

                //figure out how to remove powerup
                // checkerBoard.getChildren().remove(new StackPane(powerup.getElement()));
    
                checkerBoard.getChildren().remove(new StackPane(to.getPowerup().getElement()));
                to.setPowerup(null);
            }
/*
            to.setChecker(piece);
            checkerBoard.add(new StackPane(piece), to.col, to.row);

            removePreviewCircles();
*/
            // initate ai turn
            if(!extraTurn) {
            for(int i = 0; i < 1; i ++){
                if(!runAITurn())
                    runAITurn();
            }
        }

        //If the extra turn is true then set it false
        if(extraTurn)
            extraTurn = false;
                /*
            do {
                runAITurn();
            } while (!runAITurn());
*/
        });

        checkerBoard.add(new StackPane(previewMove), to.col, to.row);

    }

    // Method to remove preview circles if applicable
    private void removePreviewCircles() {
        Iterator<Node> checkerBoardIterator = checkerBoard.getChildren().iterator();

        while (checkerBoardIterator.hasNext()) {
            Node node = checkerBoardIterator.next();
            if (node instanceof StackPane) {
                // System.out.println("CIRCLE DETECTED");
                StackPane pane = (StackPane) node;
                Iterator<Node> paneIterator = pane.getChildren().iterator();
                while (paneIterator.hasNext()) {
                    Node innerNode = paneIterator.next();
                    if (innerNode instanceof Circle) {
                        Circle preview = (Circle) innerNode;
                        if (preview.getFill() == Color.LIGHTGRAY) {
                            checkerBoardIterator.remove();
                            // checkerBoard.getChildren().remove(pane);
                        }
                    }
                }
            }
        }

    }

    public boolean runAITurn() {
        List<Tile> computerPieces = new ArrayList<>();

        // Populate computerPieces
        // i is column, k is row
        for (int i = 0; i < SIZE; i++) {
            for (int k = 0; k < SIZE; k++) {
                Tile tile = board[i][k];

                if (tile.hasChecker() && tile.getChecker().getColor() == Color.RED) {
                    computerPieces.add(tile);
                }
            }
        }

        // Get random piece from computer
        Random rand = new Random();
        Tile computerTile = computerPieces.get(rand.nextInt(computerPieces.size()));
        int col = computerTile.col;
        int row = computerTile.row;

        // Get valid moves for that piece
        List<Tile> possibleTiles = new ArrayList<>();

        if (col - 1 >= 0 && row + 1 < SIZE) {
            Tile leftDiagnol = board[col - 1][row + 1];

            // Make sure our own piece is not in that spot!
            if (leftDiagnol.hasChecker() && leftDiagnol.getChecker().getColor() != Color.RED) {

                if (leftDiagnol.hasChecker() && (col - 2 >= 0 && row + 2 < SIZE)
                        && !board[col - 2][row + 2].hasChecker()) {
                    Tile attackTile = board[col - 2][row + 2];
                    possibleTiles.add(attackTile);
                }
            } else if (!leftDiagnol.hasChecker()) {
                possibleTiles.add(leftDiagnol);

            }
        }

        if (col + 1 < SIZE && row + 1 < SIZE) {
            Tile rightDiagnol = board[col + 1][row + 1];

            if (rightDiagnol.hasChecker() && rightDiagnol.getChecker().getColor() != Color.RED) {

                // We know this is an enemy piece, so check if the diagnol of THAT piece has a
                // piece or if its on the board
                // If the diagnol exists, AND it has no checker, then we can proceed with the
                // possible tile add
                if (rightDiagnol.hasChecker() && (col + 2 < SIZE && row + 2 < SIZE)
                        && !board[col + 2][row + 2].hasChecker()) {
                    Tile attackTile = board[col + 2][row + 2];
                    possibleTiles.add(attackTile);
                }
            } else if (!rightDiagnol.hasChecker()) {
                possibleTiles.add(rightDiagnol);
            }
        }

        // Okay, there is an actual valid move for this piece
        if (!possibleTiles.isEmpty()) {
            Tile selectedTile = possibleTiles.get(rand.nextInt(possibleTiles.size()));
            // If this is true, we know that a player piece is in our spot we are moving
            // into

            // if this is true we are doing an attack move
            if (Math.abs(row - selectedTile.row) > 1 || Math.abs(col - selectedTile.col) > 1) {

                try {
                    // Right diagnol
                    if (board[col + 2][row - 2] != null && board[col + 2][row - 2] == selectedTile) {
                        Tile enemyTile = board[col + 1][row - 1];
                        checkerBoard.getChildren().remove(new StackPane(enemyTile.getChecker()));
                        enemyTile.removeChecker();
                        System.out.println("Successfully killed enemy checker piece");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // left diagnol
                    Tile enemyTile = board[col - 1][row + 1];
                    checkerBoard.getChildren().remove(new StackPane(enemyTile.getChecker()));
                    enemyTile.removeChecker();
                    System.out.println("Successfully killed enemy checker piece");
                }
            }

            selectedTile.setChecker(computerTile.getChecker());
            checkerBoard.add(new StackPane(computerTile.getChecker()), selectedTile.col, selectedTile.row);

            computerTile.removeChecker();
            checkerBoard.getChildren().remove(computerTile.getChecker());
            return true;
        } else {
            return false;
        }
    
    }

    public Parent getBoard() {
        return checkerBoard;
    }

    public Scene getScene() {

        newGame.setLayoutX(625);
        newGame.setLayoutY(200);
        saveGame.setLayoutX(625);
        saveGame.setLayoutY(250);
        loadGame.setLayoutX(625);
        loadGame.setLayoutY(300);

        Scene scene = new Scene(root, 775, 560);
       root.getChildren().add(newGame);
        root.getChildren().add(checkerBoard);
        root.getChildren().add(saveGame);
        root.getChildren().add(loadGame);
        return scene;
    }

}
