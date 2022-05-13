package com.ultimatecheckers.main;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class UltimateCheckers extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

		Button button1 = new Button("Button 1");
		Button button2 = new Button("Button 2");
		
        Board checkerBoard = new Board(11);

		
		Scene scene = new Scene(checkerBoard.getBoard(), 560, 560);
		
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("Scene");
		
		primaryStage.show();
        
    }
}
