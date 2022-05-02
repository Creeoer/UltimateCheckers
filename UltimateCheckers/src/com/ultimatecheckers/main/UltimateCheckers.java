package com.ultimatecheckers.main;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UltimateCheckers extends Application{o
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

		Button button1 = new Button("Button 1");
		Button button2 = new Button("Button 2");
		
        Board checkerBoard = new Board(7);

		
		Scene scene = new Scene(checkerBoard.getBoard(), 300, 200);
		
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("Scene");
		
		primaryStage.show();
        
    }
}
