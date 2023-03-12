package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ai.MultiLayerPerceptron;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {

	private static Stage primaryStage;
	private static Scene primaryScene;

	private static Scene secondScene;

	private static boolean player1;
	private static MultiLayerPerceptron ia;
	private static double [] tabGame = new double[9];
	private static Button [] tabButtonGame = new Button[9];

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		Controller.primaryStage = primaryStage;
	}

	public static Scene getPrimaryScene() {
		return primaryScene;
	}

	public static void setPrimaryScene(Scene primaryScene) {
		Controller.primaryScene = primaryScene;
	}

	public static void fenetreMenu(Scene scene) {

		Button facile = (Button) scene.lookup("#facile");
		Button moyen = (Button) scene.lookup("#moyen");
		Button difficile = (Button) scene.lookup("#difficile");
		Button commencer = (Button) scene.lookup("#commencer");

		//si le bouton commencer est appuye
		commencer.setOnAction(event->{
			fenetreJeu(null);
		});



		//si le bouton facile est appuye
		facile.setOnMouseClicked(event ->{

			rechercheModelF();

		});

		//si le bouton moyen est appuye
		moyen.setOnMouseClicked(event ->{

			rechercheModelM();

		});

		//si le bouton difficile est appuye
		difficile.setOnMouseClicked(event ->{

			rechercheModelD();

		});

	}

	public static void rechercheModelD() {
		try {

			//
			//On cherche les paramètre dans config
			//
			File config = new File("./resources/config.txt");
			FileReader fr;
			fr = new FileReader(config);
			BufferedReader br = new BufferedReader(fr);
			String ligne, res = null;
			String [] tabRes = null;
			boolean trouve=false;
			while((ligne = br.readLine()) != null && trouve!=true){
				String [] tabLigne = ligne.split(":");
				if ( tabLigne[0].equals("D")) {
					trouve=true;
					res = ligne;
					tabRes = tabLigne;
				}
			}
			fr.close();

			//
			//On cherche si correspondance dans model
			//
			File dir  = new File("./resources/models/");
			File[] liste = dir.listFiles();
			trouve=false;
			for(File item : liste){
				if(item.isFile() && trouve!=true)
				{ 
					res = ligne = item.getName();
					ligne = ligne.substring(0, ligne.length() - 4);
					String [] tabLigne = ligne.split("-");
					if ( tabLigne[0].equals("D") ) {
						if ( tabLigne[1].equals(tabRes[1])==true && tabLigne[2].equals(tabRes[2])==true && tabLigne[3].equals(tabRes[3])==true) {
							trouve = true;
						}
					}
					//System.out.println("Nom du fichier: "+ item.getName()); 
				}
			}
			if ( trouve==true) {
				System.out.println("On lance le morpion avec le fichier "+res);
				fenetreJeu("./resources/models/"+res);
			}
			else {
				new Controller().lancerLearn("D");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void rechercheModelM() {
		try {

			//
			//On cherche les paramètre dans config
			//
			File config = new File("./resources/config.txt");
			FileReader fr;
			fr = new FileReader(config);
			BufferedReader br = new BufferedReader(fr);
			String ligne, res = null;
			String [] tabRes = null;
			boolean trouve=false;
			while((ligne = br.readLine()) != null && trouve!=true){
				String [] tabLigne = ligne.split(":");
				if ( tabLigne[0].equals("M")) {
					trouve=true;
					res = ligne;
					tabRes = tabLigne;
				}
			}
			fr.close();

			//
			//On cherche si correspondance dans model
			//
			File dir  = new File("./resources/models/");
			File[] liste = dir.listFiles();
			trouve=false;
			for(File item : liste){
				if(item.isFile() && trouve!=true)
				{ 
					res = ligne = item.getName();
					ligne = ligne.substring(0, ligne.length() - 4);
					String [] tabLigne = ligne.split("-");
					if ( tabLigne[0].equals("M") ) {
						if ( tabLigne[1].equals(tabRes[1])==true && tabLigne[2].equals(tabRes[2])==true && tabLigne[3].equals(tabRes[3])==true) {
							trouve = true;
						}
					}
					//System.out.println("Nom du fichier: "+ item.getName()); 
				}
			}
			if ( trouve==true) {
				System.out.println("On lance le morpion avec le fichier "+res);
				fenetreJeu("./resources/models/"+res);

			}
			else {
				new Controller().lancerLearn("M");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void rechercheModelF() {
		try {

			//
			//On cherche les paramètre dans config
			//
			File config = new File("./resources/config.txt");
			FileReader fr;
			fr = new FileReader(config);
			BufferedReader br = new BufferedReader(fr);
			String ligne, res = null;
			String [] tabRes = null;
			boolean trouve=false;
			while((ligne = br.readLine()) != null && trouve!=true){
				String [] tabLigne = ligne.split(":");
				if ( tabLigne[0].equals("F")==true) {
					trouve=true;
					res = ligne;
					tabRes = tabLigne;
				}
			}
			fr.close();

			//
			//On cherche si correspondance dans model
			//
			File dir  = new File("./resources/models/");
			File[] liste = dir.listFiles();
			trouve=false;
			for(File item : liste){
				if(item.isFile() && trouve!=true)
				{ 
					res = ligne = item.getName();
					ligne = ligne.substring(0, ligne.length() - 4);
					String [] tabLigne = ligne.split("-");
					if ( tabLigne[0].equals("F")==true ) {
						if ( tabLigne[1].equals(tabRes[1])==true && tabLigne[2].equals(tabRes[2])==true && tabLigne[3].equals(tabRes[3])==true) {
							trouve = true;
						}
					}
				}
			}
			if ( trouve==true) {
				System.out.println("On lance le morpion avec le fichier "+res);
				fenetreJeu("./resources/models/"+res);
			}
			else {
				new Controller().lancerLearn("F");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void lancerModels() {
		System.out.println("Fenetre des models lancée.");
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("delete.fxml"));

			BorderPane borderPane;
			borderPane = (BorderPane) loader.load();


			Scene secondScene = new Scene(borderPane, 800, 600);
			secondScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());


			// New window (Stage)
			Stage models = new Stage();
			models.setTitle("Models");
			models.setScene(secondScene);

			// Specifies the modality for new window.
			models.initModality(Modality.WINDOW_MODAL);

			// Specifies the owner Window (parent) for new window
			models.initOwner(primaryStage);

			// Set position of second window, related to primary window.
			models.setX(primaryStage.getX());
			models.setY(primaryStage.getY());

			Controller.secondScene=secondScene;
			Controller.fenetreDelete(models);

			models.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void lancerSettings() {

		System.out.println("Fenetre des parametres lancée.");
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("Setting.fxml"));

			BorderPane borderPane;
			borderPane = (BorderPane) loader.load();


			Scene secondScene = new Scene(borderPane, 800, 600);
			secondScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());


			// New window (Stage)
			Stage settings = new Stage();
			settings.setTitle("Settings");
			settings.setScene(secondScene);

			// Specifies the modality for new window.
			settings.initModality(Modality.WINDOW_MODAL);

			// Specifies the owner Window (parent) for new window
			settings.initOwner(primaryStage);

			// Set position of second window, related to primary window.
			settings.setX(primaryStage.getX());
			settings.setY(primaryStage.getY());

			Controller.secondScene=secondScene;
			Controller.fenetreSetting( settings);

			settings.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void lancerLearn(String difficulte) {

		System.out.println("Fenetre d'apprentissage lancée.");
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("Sample.fxml"));

			BorderPane borderPane;
			borderPane = (BorderPane) loader.load();


			Scene secondScene = new Scene(borderPane, 800, 600);
			secondScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());


			// New window (Stage)
			Stage learn = new Stage();
			learn.setTitle("Learn");
			learn.setScene(secondScene);

			// Specifies the modality for new window.
			learn.initModality(Modality.WINDOW_MODAL);

			// Specifies the owner Window (parent) for new window
			learn.initOwner(primaryStage);

			// Set position of second window, related to primary window.
			learn.setX(primaryStage.getX());
			learn.setY(primaryStage.getY());

			Controller.secondScene=secondScene;
			Controller.fenetreApprentissage( learn, difficulte);

			learn.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void fenetreDelete( Stage stage) {
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
				((VBox) secondScene.lookup("#vBox")).getChildren().addAll(hBox);
				//isSelected();
				//
				//Event
				//
				Button supprimer = (Button) secondScene.lookup("#supprimer");
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
					stage.close();
				});
			}
		}
	}

	public static void fenetreSetting( Stage stage) {
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
				((VBox) secondScene.lookup("#vBox")).getChildren().addAll(hBox);
				i++;
			}
			fr.close();

			//
			//Event
			//
			Button valider = (Button) secondScene.lookup("#valider");
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
				stage.close();
				System.out.println("Retour au menu, les modification on été pris en compte.");
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}

	public static void fenetreApprentissage( Stage stage, String difficulte) {


		Button lancerDuLearn = (Button) secondScene.lookup("#lancerDuLearn");
		Button pause = (Button) secondScene.lookup("#pause");
		Button arreter = (Button) secondScene.lookup("#arreter");
		ProgressBar progressBarDuLearn = (ProgressBar) secondScene.lookup("#progressBarDuLearn");
		ProgressIndicator progressIndicatorDuLearn = (ProgressIndicator) secondScene.lookup("#progressIndicatorDuLearn");
		progressBarDuLearn.setProgress(0.0F);
		progressIndicatorDuLearn.setProgress(0.0F);
		pause.setDisable(true);
		arreter.setDisable(true);



		Tache tache=new Tache();


		//si le bouton lancer est appuye
		lancerDuLearn.setOnMouseClicked(event ->{
			tache.setScene(secondScene);
			tache.setDifficulte(difficulte);
			tache.setStage(stage);
			Thread t=new Thread(tache);

			try {
				progressBarDuLearn.setProgress(0.0F);
				progressIndicatorDuLearn.setProgress(0.0F);
				t.start();
				lancerDuLearn.setDisable(true);
				pause.setDisable(false);
				arreter.setDisable(false);
				System.out.println("Apprentissage Commence.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		//
		//si le bouton pause est appuye
		//
		pause.setOnMouseClicked(event ->{
			if (tache.getPause()) {
				tache.setPause(false);
				arreter.setDisable(false);
				synchronized (tache)  {tache.notify();}
			}
			else{
				System.out.println("Apprentissage mis en Pause.");
				tache.setPause(true);
				arreter.setDisable(true);
			}
		});
		//
		//si le bouton pause est appuye
		//
		arreter.setOnMouseClicked(event ->{

			tache.setArret(true);	
		});
	}

	//mise à jour du message dans a fenetre learning
	public static void messageDuLearning (String s) {
		Label avancementDuLearn = (Label) secondScene.lookup("#avancementDuLearn");
		avancementDuLearn.setText(s);
	}
	//mise à jour des progressBars dans a fenetre learning
	public static void progressBarEtIndicatorDuLearning (float progression) {
		ProgressBar progressBarDuLearn = (ProgressBar) secondScene.lookup("#progressBarDuLearn");
		ProgressIndicator progressIndicatorDuLearn = (ProgressIndicator) secondScene.lookup("#progressIndicatorDuLearn");
		progressBarDuLearn.setProgress(progression);
		progressIndicatorDuLearn.setProgress(progression);
	}


	public static void fenetreJeu(String model)
	{
		ia=null;System.out.println("ALED");
		if (model!=null) {
			System.out.println("ALED");
			ia = MultiLayerPerceptron.load(model);
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("board.fxml"));
		//Label secondLabel = new Label("I'm a Label on new Window");

		BorderPane secondaryLayout;

		try {
			secondaryLayout = loader.load();

			//secondaryLayout.getChildren().add(secondLabel);


			//secondaryLayout.getChildren().add(config);
			Scene secondScene1 = new Scene(secondaryLayout, 800, 600);

			secondScene=secondScene1;
			// New window (Stage)
			Stage newWindow = new Stage();
			newWindow.setTitle("Morpion");
			newWindow.setScene(secondScene);
			Button b0 = (Button) secondScene.lookup("#B0");tabButtonGame[0]=b0;
			Button b1 = (Button) secondScene.lookup("#B1");tabButtonGame[1]=b1;
			Button b2 = (Button) secondScene.lookup("#B2");tabButtonGame[2]=b2;
			Button b3 = (Button) secondScene.lookup("#B3");tabButtonGame[3]=b3;
			Button b4 = (Button) secondScene.lookup("#B4");tabButtonGame[4]=b4;
			Button b5 = (Button) secondScene.lookup("#B5");tabButtonGame[5]=b5;
			Button b6 = (Button) secondScene.lookup("#B6");tabButtonGame[6]=b6;
			Button b7 = (Button) secondScene.lookup("#B7");tabButtonGame[7]=b7;
			Button b8 = (Button) secondScene.lookup("#B8");tabButtonGame[8]=b8;

			Button quitter = (Button) secondScene.lookup("#quitter");
			Label informationPartie2 = (Label) secondScene.lookup("#information2");

			// Specifies the modality for new window.
			newWindow.initModality(Modality.WINDOW_MODAL);

			// Specifies the owner Window (parent) for new window
			newWindow.initOwner(primaryStage);

			// Set position of second window, related to primary window.
			newWindow.setX(primaryStage.getX() );
			newWindow.setY(primaryStage.getY() );

			for(int i =0;i<9;i++)
			{
				tabGame[i] = -1;
			}

			b0.setOnAction(e->{ played(e,b0);});
			b1.setOnAction(e->{	played(e,b1);});
			b2.setOnAction(e->{	played(e,b2);});
			b3.setOnAction(e->{	played(e,b3);});
			b4.setOnAction(e->{	played(e,b4);});
			b5.setOnAction(e->{	played(e,b5);});
			b6.setOnAction(e->{	played(e,b6);});
			b7.setOnAction(e->{	played(e,b7);});
			b8.setOnAction(e->{	played(e,b8);});

			quitter.setOnAction(e->{newWindow.close();});

			newWindow.show();

			// on choisi qui commence
			int start = (int) ((Math.random() * (3 - 1)) + 1);
			if(start == 1)
			{

				player1 = true;
				if (ia==null) {informationPartie2.setText(" C'est au joueur 1 de jouer");}
				else {informationPartie2.setText(" C'est au joueur de jouer");}
				informationPartie2.setStyle("-fx-text-fill: blue;");
			}
			if(start == 2)
			{

				player1 = false;
				if (ia==null) {informationPartie2.setText(" C'est au joueur 2 de jouer");}
				else {informationPartie2.setText(" C'est à l'IA de jouer");
				informationPartie2.setStyle("-fx-text-fill: red;");

				//On désactive les boutons le temps que l'ia joue.
				if (ia!=null) {
					for (int i1=0; i1<9; i1++) {
						tabButtonGame[i1].setDisable(true);
					}
				}

				//l'IA joue
				Thread thread = new Thread() {
					public void run() {
						try {
							//On fait croire que l'IA reflechie
							Thread.sleep(1000);

							double [] rep = ia.forwardPropagation(tabGame);
							boolean fin=false; double max=-1; int index =-1;
							for (int i=0; fin==false; i++) {

								//On récupère le choix principal de l'ia
								if (rep[i]>=max){
									max = rep[i];
									index = i;
								}

								//Quand on a fait le tour du tableau
								if (i==8) {

									//problème
									if (max==-1) {
										fin=true;
										System.out.println("Probleme, le jeu ne c'est pas arreté alors que toutes les cases sont remplits. ");
									}

									//On verifie si le coup et jouable
									if (tabGame[index]==-1) {final int ind = index;fin=true;
									Platform.runLater(new Runnable() {public void run() {played(null,tabButtonGame[ind]);}});
									}
									//Si pas jouable on enleve ce choix des possibilitées et on recherche
									else {rep[index]=-1;i=0;max=-1;}
								}
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};thread.start();
				}
			}

			game();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	static void game()
	{

		if(!win())
		{
			if(player1)
			{
				if (ia==null) {System.out.println("au tour du joueur 1");}
				else {System.out.println("au tour du joueur ");}

			}
			else
			{
				if (ia==null) {System.out.println("au tour du joueur 2");}

			}
		}

	}

	static boolean win()
	{
		Label informationPartie = (Label) secondScene.lookup("#information");
		Label informationPartie2 = (Label) secondScene.lookup("#information2");
		//Quand la partie est terminée on désactive tout les boutons
		if(testWin()>0) 
		{
			for (int i1=0; i1<9; i1++) {
				tabButtonGame[i1].setDisable(true);
			}
		}
		if(testWin()== 1)
		{
			informationPartie.setText("Partie Terminée : ");
			informationPartie2.setStyle("-fx-text-fill: blue;");

			if (ia==null) {
				informationPartie2.setText(" Le joueur 1 a gagné !");
				System.out.println(" Player 1 win");
			}
			else {
				informationPartie2.setText(" Le joueur a gagné !");
				System.out.println(" Player win");
			}
			return true;
		}
		else if (testWin()== 2)
		{
			informationPartie.setText("Partie Terminée : ");
			informationPartie2.setStyle("-fx-text-fill: red;");

			if (ia==null) {
				informationPartie2.setText(" Le joueur 2 a gagné !");
				System.out.println(" Player 2 win");
			}
			else {
				informationPartie2.setText(" L'IA a gagné !");
				System.out.println(" AI win");
			}
			return true;
		}
		else if (testWin()== 3)
		{
			informationPartie.setText("Partie Terminée : ");
			informationPartie2.setStyle("-fx-text-fill: grey;");
			informationPartie2.setText(" Egalité !");
			System.out.println("Tie");
			return true;
		}
		else
			return false;
	}

	static int testWin()
	{
		// premiere ligne 
		if((tabGame[0] >-1 && tabGame[1] >-1 && tabGame[2] >-1)&&(tabGame[0] == tabGame[1]  && tabGame[2] == tabGame[1] ))
		{
			if(tabGame[0] == 1) // 1 dans le tableau == rond
			{
				//System.out.print("Player 2 win");
				return 2 ;
			}

		}
		// deuxieme ligne 
		if((tabGame[3] >-1 && tabGame[4] >-1 && tabGame[5] >-1)&&(tabGame[3] == tabGame[4]  && tabGame[5] == tabGame[3] ))
		{
			if(tabGame[3] == 1) // 1 dans le tableau == rond
				return 2 ;
		}
		//troisieme ligne
		if((tabGame[6] >-1 && tabGame[7] >-1 && tabGame[8] >-1)&&(tabGame[6] == tabGame[7]  && tabGame[8] == tabGame[6] ))
		{
			if(tabGame[6] == 1) // 1 dans le tableau == rond
				return 2 ;
		}
		////////////////////////////////
		// premiere colone 
		if((tabGame[0] >-1 && tabGame[3] >-1 && tabGame[6] >-1)&&(tabGame[0] == tabGame[3]  && tabGame[0] == tabGame[6] ))
		{
			if(tabGame[0] == 1) // 1 dans le tableau == rond
			{
				//System.out.print("Player 2 win");
				return 2 ;
			}

		}
		// deuxieme colone 
		if((tabGame[1] >-1 && tabGame[4] >-1 && tabGame[7] >-1)&&(tabGame[1] == tabGame[4]  && tabGame[1] == tabGame[7] ))
		{
			if(tabGame[1] == 1) // 1 dans le tableau == rond
				return 2 ;
		}
		//troisieme colone
		if((tabGame[2] >-1 && tabGame[5] >-1 && tabGame[8] >-1)&&(tabGame[2] == tabGame[5]  && tabGame[8] == tabGame[2] ))
		{
			if(tabGame[2] == 1) // 1 dans le tableau == rond
				return 2 ;
		}
		//////////////////////////////////
		//diagonale gauche -> droite
		if((tabGame[0] >-1 && tabGame[4] >-1 && tabGame[8] >-1)&&(tabGame[0] == tabGame[4]  && tabGame[0] == tabGame[8] ))
		{
			if(tabGame[0] == 1) // 1 dans le tableau == rond
				return 2 ;
		}
		//diagonale droite -> gauche
		if((tabGame[2] >-1 && tabGame[4] >-1 && tabGame[6] >-1)&&(tabGame[2] == tabGame[4]  && tabGame[2] == tabGame[6] ))
		{
			if(tabGame[2] == 1) // 1 dans le tableau == rond
				return 2 ;
		}

		// verif pour les croix

		// premiere ligne 
		if((tabGame[0] >-1 && tabGame[1] >-1 && tabGame[2] >-1)&&(tabGame[0] == tabGame[1]  && tabGame[2] == tabGame[1] ))
		{
			if(tabGame[0] == 0) // 0 dans le tableau == croix
				return 1 ;
		}
		// deuxieme ligne 
		if((tabGame[3] >-1 && tabGame[4] >-1 && tabGame[5] >-1)&&(tabGame[3] == tabGame[4]  && tabGame[5] == tabGame[3] ))
		{
			if(tabGame[3] == 0) // 0 dans le tableau == croix
				return 1 ;
		}
		//troisieme ligne
		if((tabGame[6] >-1 && tabGame[7] >-1 && tabGame[8] >-1)&&(tabGame[6] == tabGame[7]  && tabGame[8] == tabGame[6] ))
		{
			if(tabGame[6] == 0) // 0 dans le tableau == croix
				return 1 ;
		}
		///////////////////////////////
		// premiere colone 
		if((tabGame[0] >-1 && tabGame[3] >-1 && tabGame[6] >-1)&&(tabGame[0] == tabGame[3]  && tabGame[0] == tabGame[6] ))
		{
			if(tabGame[0] == 0) // 1 dans le tableau == rond
			{
				//System.out.print("Player 2 win");
				return 2 ;
			}

		}
		// deuxieme colone 
		if((tabGame[1] >-1 && tabGame[4] >-1 && tabGame[7] >-1)&&(tabGame[1] == tabGame[4]  && tabGame[1] == tabGame[7] ))
		{
			if(tabGame[1] == 0) // 1 dans le tableau == rond
				return 2 ;
		}
		//troisieme colone
		if((tabGame[2] >-1 && tabGame[5] >-1 && tabGame[8] >-1)&&(tabGame[2] == tabGame[5]  && tabGame[8] == tabGame[2] ))
		{
			if(tabGame[2] == 0) // 1 dans le tableau == rond
				return 2 ;
		}
		//////////////////////////////
		//diagonale gauche -> droite
		if((tabGame[0] >-1 && tabGame[4] >-1 && tabGame[8] >-1)&&(tabGame[0] == tabGame[4]  && tabGame[0] == tabGame[8] ))
		{
			if(tabGame[0] == 0) // 0 dans le tableau == croix
				return 1 ;
		}
		//diagonale droite -> gauche
		if((tabGame[2] >-1 && tabGame[4] >-1 && tabGame[6] >-1)&&(tabGame[2] == tabGame[4]  && tabGame[2] == tabGame[6] ))
		{
			if(tabGame[2] == 0) // 0 dans le tableau == croix
				return 1 ;
		}

		//////////////////////////////
		// verifie si égalité
		if((tabGame[0] >-1 && tabGame[1] >-1 && tabGame[2] >-1 && tabGame[3] >-1 && tabGame[4] >-1 && tabGame[5] >-1 && tabGame[6] >-1 && tabGame[7] >-1 && tabGame[8] >-1))
		{
			return 3 ;
		}
		return -1;

	}

	public static void played(Event it, Button b)
	{
		if(!win())
		{
			Label informationPartie2 = (Label) secondScene.lookup("#information2");
			if(player1 && b.getText() == "")
			{

				//System.out.print("button nb : "+b.getId().replace("B", "")+'\n');
				String i = b.getId().replace( "B", "");
				tabGame[Integer.parseInt(i)] = 0;
				b.setText("X");
				b.setStyle("-fx-text-fill: blue;");

				//On désactive les boutons le temps que l'ia joue.
				if (ia!=null) {
					for (int i1=0; i1<9; i1++) {
						tabButtonGame[i1].setDisable(true);
					}
				}

				informationPartie2.setStyle("-fx-text-fill: red;");

				if (ia==null) {informationPartie2.setText(" C'est au joueur 2 de jouer");}
				else {
					informationPartie2.setText(" C'est à l'IA de jouer");
					System.out.println("au tour de l'IA ");
					//
					//C'est ici que le choix de l'IA va se faire
					//
					Thread thread = new Thread() {
						public void run() {
							try {
								//On fait croire que l'IA reflechie
								Thread.sleep(1000);

								double [] rep = ia.forwardPropagation(tabGame);
								boolean fin=false; double max=-1; int index =-1;
								for (int i=0; fin==false; i++) {

									//On récupère le choix principal de l'ia
									if (rep[i]>=max){
										max = rep[i];
										index = i;
									}

									//Quand on a fait le tour du tableau
									if (i==8) {

										//problème
										if (max==-1) {
											fin=true;
											System.out.println("Probleme, le jeu ne c'est pas arreté alors que toutes les cases sont remplits. ");
										}

										//On verifie si le coup et jouable
										if (tabGame[index]==-1) {final int ind = index;fin=true;
										Platform.runLater(new Runnable() {public void run() {played(null,tabButtonGame[ind]);}});
										}
										//Si pas jouable on enleve ce choix des possibilitées et on recherche
										else {rep[index]=-1;i=0;max=-1;}
									}
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};thread.start();
				}

				player1 = !player1;
				game();

			}
			else if(!player1 && b.getText() == "")
			{

				//System.out.print("button nb : "+b.getId().replace("B", "")+'\n');
				String i = b.getId().replace( "B", "");
				tabGame[Integer.parseInt(i)] = 1;
				b.setText("O");
				b.setStyle("-fx-text-fill: red;");
				informationPartie2.setStyle("-fx-text-fill: blue;");

				if (ia==null) {informationPartie2.setText(" C'est au joueur 1 de jouer");}
				else {
					informationPartie2.setText(" C'est au joueur du jouer");
					//On réactive les boutons pour le tour du joueur
					for (int i1=0; i1<9; i1++) {
						tabButtonGame[i1].setDisable(false);
					}
				}

				player1 = !player1;
				game();
			}
		}
		//
		//afficheTab();

	}

	static void afficheTab()
	{
		System.out.println("new affichage ");
		for(int i =0 ; i<9;i++)
		{
			System.out.println("cell : "+i +"value : "+tabGame[i]);
		}
	}

}
