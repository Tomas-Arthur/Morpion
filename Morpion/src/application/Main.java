package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import ai.Config;
import ai.ConfigFileLoader;
import ai.Coup;
import ai.MultiLayerPerceptron;
import ai.SigmoidalTransferFunction;
import ai.Test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Main extends Application implements Runnable{

	static Scene scene;
	static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Morpion");
			this.primaryStage = primaryStage;
			// Load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();
			
			//
			//loader à decommenter selon la fenetre voulu (pas deux a la fois)			//
			
			//loader.setLocation(Main.class.getResource("Sample.fxml"));
			//loader.setLocation(Main.class.getResource("Setting.fxml"));
			//loader.setLocation(Main.class.getResource("Delete.fxml"));
			loader.setLocation(Main.class.getResource("Menu.fxml"));
			BorderPane Sample = (BorderPane) loader.load();
			Scene scene = new Scene(Sample,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// Show the scene containing the root layout.
			primaryStage.setScene(scene);
			primaryStage.show();

			this.scene=scene;
			Controller.setScene(scene);
			
			//
			//fonction a décommenter selon le loader prix
			//
			
			//Controller.fenetreApprentissage(scene);
			//Controller.fenetreSetting(scene);
			//Controller.fenetreDelete();
						
		} catch(Exception e) {
			e.printStackTrace();
		}
	}



	public static void main(String[] args) {
		launch(args);

	}

	public void setConfig()
	{

		Label secondLabel = new Label("I'm a Label on new Window");

		StackPane secondaryLayout = new StackPane();
		secondaryLayout.getChildren().add(secondLabel);
		VBox config = new VBox();
		TextArea confAffichage = new TextArea();
		secondaryLayout.getChildren().addAll(config,confAffichage);
		Scene secondScene = new Scene(secondaryLayout, 230, 100);

		// File path is passed as parameter
		File file = new File(
				"./resources/config.txt");

		// Note:  Double backquote is to avoid compiler
		// interpret words
		// like \test as \t (ie. as a escape sequence)

		// Creating an object of BufferedReader class
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Declaring a string variable
		String st;
		String conf = "";
		// Condition holds true till
		// there is character in a string
		try {
			while ((st = br.readLine()) != null)
			{
				// Print the string
				//System.out.println(st);
				conf+=st+'\n';
				confAffichage.setText(conf);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// New window (Stage)
		Stage newWindow = new Stage();
		newWindow.setTitle("Second Stage");
		newWindow.setScene(secondScene);

		// Specifies the modality for new window.
		newWindow.initModality(Modality.WINDOW_MODAL);

		// Specifies the owner Window (parent) for new window
		newWindow.initOwner(primaryStage);

		// Set position of second window, related to primary window.
		newWindow.setX(primaryStage.getX() + 200);
		newWindow.setY(primaryStage.getY() + 100);

		newWindow.show();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
