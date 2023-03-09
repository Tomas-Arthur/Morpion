package application;

import java.util.TimerTask;

import ai.Test;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class Tache extends Task<Integer>{

	private Scene scene;
	private String mess;
	private static Tache tache;
	
	public static Tache creerTache(Scene scene) {
		if (tache!=null) {
			return tache;
		}
		return tache = new Tache(scene);
	}
	
	public static Tache recupTache() {
		return tache;
	}
	
	private Tache(Scene scene) {
		this.scene=scene;
	}
	
	public String getMess() {
		return mess;
	}
	public Scene getScene() {
		return scene;
	}
	
	public void setMess(String mess) {
		this.mess=mess;
	}
	public void setScene(Scene scene) {
		this.scene=scene;
	}

	
	public void rune()  {
		
		Label avancement = (Label) scene.lookup("#avancement");
		Test.main(null);
		/*Platform.runLater(new Runnable() {
			public void run() {
				Test.main(null);
				avancement.setText("ALED1");
			}
		});
		try{
			Thread.sleep(200);
		}
		catch(Exception e) {
			System.out.println(e);
		}*/
		
		//avancement.setText("ALED");
	}
	
	@Override
	protected Integer call() throws Exception {
		Label avancement = (Label) scene.lookup("#avancement");
		int iterations = 0;
		Platform.runLater(new Runnable() {
			public void run() {
				Test.main(null);
			}
		});
        for (iterations = 0; iterations < 10000000; iterations++) {
            if (isCancelled()) {
                break;
            }
            System.out.println("Iteration " + iterations);
        }
		return null;
	}
	
}
