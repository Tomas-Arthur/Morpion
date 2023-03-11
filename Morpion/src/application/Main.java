package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


public class Main extends Application{

	public static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Morpion");
			Main.primaryStage = primaryStage;
			// Load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(Main.class.getResource("Menu.fxml"));
			BorderPane borderPane = (BorderPane) loader.load();
			Scene menu= new Scene(borderPane,800,600);
			menu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// Show the scene containing the root layout.
			primaryStage.setScene(menu);
			primaryStage.show();

			Controller.setPrimaryStage(Main.primaryStage);
			Controller.setPrimaryScene(menu);
			
			Controller.fenetreMenu(menu);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}



	public static void main(String[] args) {
		launch(args);
	}




}
