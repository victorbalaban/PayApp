package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * Main Class for launching the User Interface
 */
public class Main extends Application {
	
	/**
	 * Current Stage
	 */
	public static Stage currentStage;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent login = FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));
	        Scene loginScene = new Scene(login,450,600);
			loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.setTitle("Easy Pay");
			primaryStage.getIcons().add(new Image("/images/logo.png"));
			primaryStage.setResizable(false);
			primaryStage.show();
			
			currentStage = primaryStage;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	/**
	 * Returns the current Stage
	 */
	public static Stage getStage(){
		return currentStage;
	}
	
	/**
	 * Sets the current Stage to the stage specified
	 */
	public static void setStage(Stage stage){
		currentStage = stage;
	}
}
