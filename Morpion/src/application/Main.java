package application;
import ai.Test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application implements Runnable{

	static Scene scene;
	
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
	             /*for (iterations = 0; iterations < 10000000; iterations++) {
	            	 System.out.println("Iteration " + iterations);
	                 if (isCancelled()) {
	                     updateMessage("Cancelled");
	                     break;
	                 }
	                 //notify();
	                 updateMessage("Iteration " + iterations);
	                 
	                 updateProgress(iterations, 10000);
	             }*/
				return null;
	         }
	     };
	     /*
	     task.progressProperty().addListener((observable, oldValue, newValue) -> {
	    	    System.out.println("progressProperty changed from " + oldValue + " to " + newValue);
	    	    avancementDuLearn.setText(newValue.toString());
	     });
	     */
	     
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
