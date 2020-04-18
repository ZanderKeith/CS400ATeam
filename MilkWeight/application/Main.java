/**
 * Main.java created by zkeith on personal laptop in MilkWeight
 *
 * Author: 		Zander Keith (zkeith@wisc.edu)
 * Date: 		Apr 14, 2020
 * 
 * Course: 		CS400
 * Semester: 	Spring 2020
 * Lecture: 	001
 * 
 * IDE: 		Eclipse IDE for Java Developers
 * Version: 	2020-03
 * 
 * OS:			Windows 10
 * 
 * List Collaborators: Name, email@wisc.edu, lecture number
 * 
 * Other Credits: describe other source (websites or people)
 * 
 * Known Bugs: describe known unresolved bugs here
 */
package application;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main - Launches program window, runs GUI, and holds Farm object data
 * 
 * @author zkeith
 *
 */
public class Main extends Application {
	// store any command-line arguments that were entered.
		// NOTE: this.getParameters().getRaw() will get these also
		private List<String> args;

		private static final int WINDOW_WIDTH = 300;
		private static final int WINDOW_HEIGHT = 200;
		private static final String APP_TITLE = "MilkWeight";

		@Override
		public void start(Stage primaryStage) throws Exception {
			// Labels
			Label topLabel = new Label(APP_TITLE);
			TextField copyLabel = new TextField("www.youtube.com/watch?v=dQw4w9WgXcQ");
			copyLabel.setEditable(false);
			
			
			// Main layout is Border Pane example (top,left,center,right,bottom)
			BorderPane root = new BorderPane();
			
			// Set contents in border pane
			root.setTop(topLabel);
			root.setCenter(copyLabel);
			
			
			Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
//hi
			// Add the stuff and set the primary stage
			primaryStage.setTitle(APP_TITLE);
			primaryStage.setScene(mainScene);
			primaryStage.show();
		}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

}
