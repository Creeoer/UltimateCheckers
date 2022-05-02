package com.ultimatecheckers.main;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UltimateCheckers extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

		Button button1 = new Button("Button 1");
		Button button2 = new Button("Button 2");
		
        GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		
		gridPane.add(button1, 0, 0);
		gridPane.add(button2, 1, 0);

		
		Scene scene = new Scene(gridPane, 300, 200);
		
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("Scene");
		
		primaryStage.show();
        
    }
}
