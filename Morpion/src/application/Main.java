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
		
		
	     Label avancement = (Label) scene.lookup("#avancement");
	     
	     
		
		Button lancer = (Button) scene.lookup("#lancer");

		lancer.setOnMouseClicked(event ->{

			Task<Void> task = new Task<Void>() {
	         @Override protected Void call() throws Exception {
	             int iterations=0;
	             Test.main(null);
	             Platform.runLater(new Runnable() {
						public void run() {
							avancement.setText("Fin");
						}
					});
	             Thread.sleep(1000);
	             Platform.runLater(new Runnable() {
					public void run() {
						avancement.setText("...");
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
	     task.progressProperty().addListener((observable, oldValue, newValue) -> {
	    	    System.out.println("progressProperty changed from " + oldValue + " to " + newValue);
	    	    avancement.setText(newValue.toString());
	     });
	     
			Thread t = new Thread(task);
			t.setDaemon(true);
			t.start();
			//task.run();
			
			
			//String args[] = null;
			//Test.main(args);
			//avancement.setText("Fin");
			//t.start();
			//t.run();
		});
		
	}
	
	public static void messageDuLearning (String s) {
		Label avancement = (Label) scene.lookup("#avancement");
		avancement.setText(s);
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
