package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ai.Test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
            loader.setLocation(Main.class.getResource("Sample.fxml"));
            BorderPane Sample = (BorderPane) loader.load();
			Scene scene = new Scene(Sample,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// Show the scene containing the root layout.
			primaryStage.setScene(scene);
			primaryStage.show();
			
			this.scene=scene;
			test(scene);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test(Scene scene) {
		
		
		Label avancementDuLearn = (Label) scene.lookup("#avancementDuLearn"); 
		Button lancerDuLearn = (Button) scene.lookup("#lancerDuLearn");
		ProgressBar progressBarDuLearn = (ProgressBar) scene.lookup("#progressBarDuLearn");
		ProgressIndicator progressIndicatorDuLearn = (ProgressIndicator) scene.lookup("#progressIndicatorDuLearn");
		progressBarDuLearn.setProgress(0.0F);
		progressIndicatorDuLearn.setProgress(0.0F);

		//si le bouton est appuye
		lancerDuLearn.setOnMouseClicked(event ->{

			Task<Void> task = new Task<Void>() {
	         @Override protected Void call() throws Exception {
	             //int iterations=0;
	             Test.main(null);
	             Platform.runLater(new Runnable() {
						public void run() {
							avancementDuLearn.setText("Fin");
						}
					});
	             Thread.sleep(1000);
	             Platform.runLater(new Runnable() {
					public void run() {
						avancementDuLearn.setText("...");
						progressBarDuLearn.setProgress(0.0F);
						progressIndicatorDuLearn.setProgress(0.0F);
						lancerDuLearn.setDisable(false);
					}
				});
	            
				return null;
	         }
	     };
	     
	     
	     
	     
			Thread t = new Thread(task);
			t.setDaemon(true);
			t.start();
			lancerDuLearn.setDisable(true);
		});
		
		
		
	}
	
	//mise à jour des progressBars
	public static void messageDuLearning (String s) {
		Label avancementDuLearn = (Label) scene.lookup("#avancementDuLearn");
		avancementDuLearn.setText(s);
	}
	public static void progressBarEtIndicatorDuLearning (float progression) {
		ProgressBar progressBarDuLearn = (ProgressBar) scene.lookup("#progressBarDuLearn");
		ProgressIndicator progressIndicatorDuLearn = (ProgressIndicator) scene.lookup("#progressIndicatorDuLearn");
		progressBarDuLearn.setProgress(progression);
		progressIndicatorDuLearn.setProgress(progression);
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
