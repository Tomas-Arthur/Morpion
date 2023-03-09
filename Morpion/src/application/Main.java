package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
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

		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		launch(args);

	}

	/*
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
	}*/


}
