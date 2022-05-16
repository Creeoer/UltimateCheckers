package com.ultimatecheckers.main;


import javafx.application.Application;
import javafx.stage.Stage;

public class UltimateCheckers extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Board checkerBoard = new Board(11);

//		Scene scene = new Scene(checkerBoard.getBoard(), 560, 560);
		
		primaryStage.setScene(checkerBoard.getScene());
		
		primaryStage.setTitle("Scene");
		
		primaryStage.show();
        
    }
}
