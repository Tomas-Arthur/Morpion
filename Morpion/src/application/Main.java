package application;
import ai.Test;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Morpion");
			
			// Load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Sample.fxml"));
            BorderPane Sample = (BorderPane) loader.load();
			Scene scene = new Scene(Sample,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// Show the scene containing the root layout.
			primaryStage.setScene(scene);
			primaryStage.show();
			
			test(scene);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test(Scene scene) {
		Label avancement = (Label) scene.lookup("#avancement");
		Button lancer = (Button) scene.lookup("#lancer");
		lancer.setOnMouseClicked(event ->{
			avancement.setText("---");
			String args[] = null;
			Test.main(args);
			avancement.setText("Fin");
		});
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}
