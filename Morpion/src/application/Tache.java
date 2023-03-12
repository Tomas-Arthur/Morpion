package application;

import java.util.HashMap;
import ai.Config;
import ai.ConfigFileLoader;
import ai.Coup;
import ai.MultiLayerPerceptron;
import ai.SigmoidalTransferFunction;
import ai.Test;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Tache extends Task<Void>{

	private Scene scene;
	private Stage stage;
	private String difficulte;

	private boolean pause=false;
	private boolean arret=false;

	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public Scene getScene() {
		return scene;
	}
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	public String getDifficulte() {
		return difficulte;
	}
	public void setDifficulte(String difficulte) {
		this.difficulte = difficulte;
	}
	public boolean isArret() {
		return arret;
	}
	public void setArret(boolean arret) {
		this.arret = arret;
	}

	public void setPause(boolean pause) {
		this.pause=pause;
	}
	public boolean getPause() {
		return pause;
	}

	@Override protected synchronized Void call() throws Exception {

		Label avancementDuLearn = (Label) scene.lookup("#avancementDuLearn"); 
		Button lancerDuLearn = (Button) scene.lookup("#lancerDuLearn");
		Button pause = (Button) scene.lookup("#pause");
		Button arreter = (Button) scene.lookup("#arreter");
		scene.lookup("#progressBarDuLearn");
		scene.lookup("#progressIndicatorDuLearn");

		HashMap<Integer, Coup> coups = Test.loadGames("./resources/dataset/Tic_tac_initial_results.csv");
		Test.saveGames(coups, "./resources/train_dev_test/", 0.7);
		//
		// LOAD CONFIG ...
		//
		ConfigFileLoader cfl = new ConfigFileLoader();
		cfl.loadConfigFile("./resources/config.txt");
		Config config = cfl.get(difficulte);
		System.out.println("Test.main() : "+config);
		//
		//TRAIN THE MODEL ...
		//
		double epochs = 10000 ;
		HashMap<Integer, Coup> mapTrain = Test.loadCoupsFromFile("./resources/train_dev_test/train.txt");
		//
		//LEARN
		//
		int size=9;
		//HashMap<Integer, Coup> mapTrain1 = mapTrain;
		String nomConfig=config.level+"-"+config.hiddenLayerSize+"-"+config.learningRate+"-"+config.numberOfhiddenLayers;
		int h=config.hiddenLayerSize;
		double lr=config.learningRate;
		int l=config.numberOfhiddenLayers;
		boolean verbose=true;
		//double epochs=epochs;

		try {
			if ( verbose ) {
				System.out.println();
				System.out.println("START TRAINING ...");
				System.out.println();
			}
			//
			//			int[] layers = new int[]{ size, 128, 128, size };
			int[] layers = new int[l+2];
			layers[0] = size ;
			for (int i = 0; i < l; i++) {
				layers[i+1] = h ;
			}
			layers[layers.length-1] = size ;
			//
			double error = 0.0 ;
			MultiLayerPerceptron net = new MultiLayerPerceptron(layers, lr, new SigmoidalTransferFunction());

			if ( verbose ) {
				System.out.println("---");
				System.out.println("Load data ...");
				System.out.println("---");
			}
			//TRAINING ...
			for(int i = 0; i < epochs; i++){
				//fin boucle
				if (arret) {
					pause.setDisable(false);
					arreter.setDisable(false);
					lancerDuLearn.setDisable(false);
					System.out.println("Apprentissage Arreter.");
					Platform.runLater(new Runnable() {
						public void run() {
							Controller.messageDuLearning("Apprentissage Arreter.");
						}
					});	
					Thread.sleep(1000);
					arret=false;
					Platform.runLater(new Runnable() {
						public void run() {
							stage.close();
						}
					});	
					return null;
				}
				if (this.pause==true) {
					wait();
					System.out.println("Pause de l'apprentissage enlever.");
				}
				Coup c = null ;
				while ( c == null )
					c = mapTrain.get((int)(Math.round(Math.random() * mapTrain.size())));

				error += net.backPropagate(c.in, c.out);

				//message
				if ( i % 1000 == 0 && verbose) {

					String s ="Error at step "+i+" is "+ (error/(double)i);
					System.out.println(s);
					Platform.runLater(new Runnable() {
						public void run() {
							Controller.messageDuLearning(s);
						}
					});	

				}
				//updateProgress(i, epochs);
				//progressBar
				float progression = (float) (i/epochs);
				Platform.runLater(new Runnable() {
					public void run() {
						Controller.progressBarEtIndicatorDuLearning(progression);
					}
				});	

			}
			if ( verbose ) {
				System.out.println("Learning completed!");
				if (net.save("./resources/models/"+nomConfig+".srl")==false) {
					System.out.println("Problème lors de la sauvegarde : "+nomConfig+".srl");
					Platform.runLater(new Runnable() {
						public void run() {
							avancementDuLearn.setText("Problème lors de la sauvegarde : "+nomConfig+".srl");
						}
					});
				}
			}

		} 
		catch (Exception e) {
			System.out.println("Test.learn()");
			e.printStackTrace();
			System.exit(-1);
		}


		Platform.runLater(new Runnable() {
			public void run() {
				avancementDuLearn.setText("Fin");
			}
		});
		Thread.sleep(1000);
		Platform.runLater(new Runnable() {
			public void run() {
				pause.setDisable(true);
				arreter.setDisable(true);
				Platform.runLater(new Runnable() {
					public void run() {
						System.out.println("MACHIN"+cfl.get(difficulte));
						if (config.level.equals("F")) {
							Controller.rechercheModelF();
						}
						if (config.level.equals("M")) {
							Controller.rechercheModelM();
						}
						if (config.level.equals("D")) {
							Controller.rechercheModelD();
						}
						
						stage.close();
					}
				});	
			}
		});

		return null;
	}

}
