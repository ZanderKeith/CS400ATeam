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
import javafx.geometry.Insets;
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
import javafx.scene.layout.VBox;
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

	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 640;
	private static final String APP_TITLE = "MilkWeight";
	
	
	private void homeButtonAction() {
		System.out.println("Home Button Pressed");
	}
	
	private void importFileButtonAction() {
		System.out.println("Import File Button Pressed");
	}
	
	private void exportAsCSVButtonAction() {
		System.out.println("Export as CSV File Button Pressed");
	}
	
	private void submitButtonAction() {
		System.out.println("Submit Button Pressed");
	}
	
	/**
	 * Sets up all GUI elements.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Labels
		Label fileLabel = new Label("File");
		Label reportLabel = new Label("Report");
		Label farmIDLabel = new Label("Farm ID");
		Label yearLabel = new Label("Year(Enter \"all\" for all available data)");
		Label farmReportLabel = new Label("Farm Report");
		TextField farmIDField = new TextField("");
		TextField yearField = new TextField("");
		
		// Buttons
		Button homeButton = new Button("Home");
		homeButton.setOnAction(e -> homeButtonAction());
		
		Button importFileButton = new Button("Import FIle");
		importFileButton.setOnAction(e -> importFileButtonAction());
		
		Button exportAsCSVButton = new Button("Export as CSV");
		exportAsCSVButton.setOnAction(e -> exportAsCSVButtonAction());
		
		Button submitButton = new Button("Submit");
		submitButton.setOnAction(e -> submitButtonAction());
		
		// Create left panel of buttons
		VBox leftOptionPanel = new VBox();
		leftOptionPanel.setSpacing(20); //TODO no magic numbers in spacing. define constants elsewhere
		leftOptionPanel.setPadding(new Insets(15, 15, 15, 15)); //TODO verify insets are allowed (javafx.geometry)
		
		VBox fileOptionGroup = new VBox();
		fileOptionGroup.getChildren().addAll(fileLabel, importFileButton, exportAsCSVButton);
		
		VBox reportOptionGroup = new VBox(farmReportLabel);
		
		leftOptionPanel.getChildren().addAll(homeButton, fileOptionGroup, reportOptionGroup);
		
		// 
		
		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();

		// Set contents in border pane
		root.setLeft(leftOptionPanel);

		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}


	/**
	 * TODO method header needed?
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
