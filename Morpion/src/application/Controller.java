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
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller {
	
	private static Scene scene;

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		Controller.scene = scene;
	}
	
	public static void fenetreDelete() {
		//
		// LOAD CONFIG ...
		//
		int nbrLine=0;
			File dir  = new File("./resources/models/");
			File[] liste = dir.listFiles();
		    for(File item : liste){
		    	if(item.isFile())
		        { 
		    		nbrLine++;
		    		System.out.println("Nom du fichier: "+ item.getName()); 
		        }
		    }
		    //
		    //on recupere les configs
		    //
		    String [] conf = new String[nbrLine];
		    RadioButton [] tab = new RadioButton[nbrLine];
			int i=0;
			for(File item : liste)
			{
				if(item.isFile() && i<nbrLine)
		        {
					
					conf[i] = item.getName();
					tab[i] = new RadioButton(i+".  "+item.getName());
					//
					//hbox qui contiendra chaque config
					//
					HBox hBox= new HBox();
					hBox.setAlignment(Pos.CENTER);
					hBox.getChildren().addAll(tab[i]);
					//
					//vbox qui contiendra toutes les configs
					//
					i++;
					((VBox) scene.lookup("#vBox")).getChildren().addAll(hBox);
					//isSelected();
					//
				    //Event
				    //
				    Button supprimer = (Button) scene.lookup("#supprimer");
				    final int nb = nbrLine;
				    
				    //si le bouton lancer est appuye
				    supprimer.setOnMouseClicked(event ->{
						    for (int j1=0; j1<nb; j1++) {
						    	if( tab[j1].isSelected()==true) {
						    		File fichierSupp = new File("./resources/models/"+conf[j1]); 
						            
						            if(fichierSupp.delete()) 
						            { 
						                System.out.println("Fichier "+conf[j1]+" supprimé avec succès"); 
						            } 
						            else
						            { 
						                System.out.println("Impossible de supprimer le fichier"); 
						            }
						    	}
						    }
						Platform.exit();
					});
		    	}
			}
	}
	
	public static void fenetreSetting(Scene scene) {
		//
		// LOAD CONFIG ...
		//
		try {
			File config = new File("./resources/config.txt");
			//
			//On compte le nombre de ligne
			//
			FileReader fr = new FileReader(config);
			BufferedReader br = new BufferedReader(fr);
			int nbrLine = 0;            
		    while((br.readLine()) != null){nbrLine++;}
		    fr.close();
		    //
		    //on recupere les configs
		    //
		    fr = new FileReader(config);
			br = new BufferedReader(fr);
			String ligne;
			TextField [][] tab = new TextField[nbrLine][4];
			int i=0;
			while((ligne = br.readLine()) != null)
		    	{
				
					//System.out.println(ligne);
					String[] tabLigne = ligne.split(":");
					
					tab[i][0] = new TextField(tabLigne[0]);
					tab[i][1] = new TextField(tabLigne[1]);
					tab[i][2] =  new TextField(tabLigne[2]);
					tab[i][3] =  new TextField(tabLigne[3]);
					//
					//hbox qui contiendra chaque config
					//
					HBox hBox= new HBox();
					hBox.setAlignment(Pos.CENTER);
					hBox.getChildren().addAll(new Label(tabLigne[0]),new Label(" : "),tab[i][1],new Label(" : "),tab[i][2],new Label(" : "),tab[i][3]);
					//
					//vbox qui contiendra toutes les configs
					//
					((VBox) scene.lookup("#vBox")).getChildren().addAll(hBox);
					i++;
		    	}
		    fr.close();
		    
		    //
		    //Event
		    //
		    Button valider = (Button) scene.lookup("#valider");
		    final int nb = nbrLine;
		    
		    //si le bouton lancer est appuye
		    valider.setOnMouseClicked(event ->{
				FileWriter fw;
				try {
					fw = new FileWriter(config);
				    BufferedWriter bw = new BufferedWriter(fw);
				    for (int j1=0; j1<nb; j1++) {
				    	bw.write(tab[j1][0].getText()+":"+tab[j1][1].getText()+":"+tab[j1][2].getText()+":"+tab[j1][3].getText()+"\n");
				    }
				    bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Platform.exit();
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}

	public static void fenetreApprentissage(Scene scene) {
		
		
		Label avancementDuLearn = (Label) scene.lookup("#avancementDuLearn"); 
		Button lancerDuLearn = (Button) scene.lookup("#lancerDuLearn");
		ProgressBar progressBarDuLearn = (ProgressBar) scene.lookup("#progressBarDuLearn");
		ProgressIndicator progressIndicatorDuLearn = (ProgressIndicator) scene.lookup("#progressIndicatorDuLearn");
		progressBarDuLearn.setProgress(0.0F);
		progressIndicatorDuLearn.setProgress(0.0F);

		//si le bouton lancer est appuye
		lancerDuLearn.setOnMouseClicked(event ->{

			Task<Void> task = new Task<Void>() {
	         @Override protected Void call() throws Exception {
	        	HashMap<Integer, Coup> coups = Test.loadGames("./resources/dataset/Tic_tac_initial_results.csv");
	        	Test.saveGames(coups, "./resources/train_dev_test/", 0.7);
	 			//
	 			// LOAD CONFIG ...
	 			//
	 			ConfigFileLoader cfl = new ConfigFileLoader();
	 			cfl.loadConfigFile("./resources/config.txt");
	 			Config config = cfl.get("F");
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
	 								messageDuLearning(s);
	 							}
	 						});	

	 					}
	 					
	 					//progressBar
	 					float progression = (float) (i/epochs);
	 					Platform.runLater(new Runnable() {
	 						public void run() {
	 							progressBarEtIndicatorDuLearning(progression);
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
	
	//mise à jour du message dans a fenetre learning
	public static void messageDuLearning (String s) {
		Label avancementDuLearn = (Label) scene.lookup("#avancementDuLearn");
		avancementDuLearn.setText(s);
	}
	//mise à jour des progressBars dans a fenetre learning
	public static void progressBarEtIndicatorDuLearning (float progression) {
		ProgressBar progressBarDuLearn = (ProgressBar) scene.lookup("#progressBarDuLearn");
		ProgressIndicator progressIndicatorDuLearn = (ProgressIndicator) scene.lookup("#progressIndicatorDuLearn");
		progressBarDuLearn.setProgress(progression);
		progressIndicatorDuLearn.setProgress(progression);
	}

}
