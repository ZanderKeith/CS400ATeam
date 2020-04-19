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
import javafx.scene.layout.HBox;
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

	private void addNewFarmButtonAction() {
		System.out.println("Add new farm Button Pressed");
	}

	private void addNewMilkDataButtonAction() {
		System.out.println("Add new milk data Button Pressed");
	}

	private void editMilkDataButtonAction() {
		System.out.println("Edit milk data Button Pressed");
	}

	private void farmReportButtonAction() {
		System.out.println("Farm Report Button Pressed");
	}

	private void annualReportButtonAction() {
		System.out.println("Annual Report Button Pressed");
	}

	private void monthlyReportButtonAction() {
		System.out.println("Monthly Report Button Pressed");
	}

	private void dateRangeReportButtonAction() {
		System.out.println("Date Range Report Button Pressed");
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
		Label monthLabel = new Label("Month: ");
		Label yearLabel = new Label("Year: ");
		Label farmIDLabel = new Label("Farm ID: ");
		Label farmReportLabel = new Label("Farm Report");
		Label chartLabel = new Label("Farm no., Date + Report");
		Label totalWeightLabel = new Label("Total Weight Sold: + weight + lb");
		Label percentWeightLabel = new Label("Percent of total + something%");

		// Text Fields
		TextField farmIDField = new TextField("");
		TextField yearField = new TextField("");

		// Drop Downs
		ComboBox monthComboBox = new ComboBox();
		ObservableList<String> monthItems = FXCollections.observableArrayList("January",
				"February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December", "ALL");
		monthComboBox.setItems(monthItems);

		ComboBox yearComboBox = new ComboBox();
		ObservableList<String> yearItems = FXCollections.observableArrayList("2017", "2018",
				"2019", "2020", "ALL");
		yearComboBox.setItems(yearItems);

		ComboBox farmComboBox = new ComboBox();
		ObservableList<String> farmItems = FXCollections.observableArrayList("Boggis", "Bunce",
				"Bean");
		farmComboBox.setItems(farmItems);

		// Buttons
		Button homeButton = new Button("Home");
		homeButton.setOnAction(e -> homeButtonAction());

		Button importFileButton = new Button("Import File");
		importFileButton.setOnAction(e -> importFileButtonAction());

		Button exportAsCSVButton = new Button("Export as CSV");
		exportAsCSVButton.setOnAction(e -> exportAsCSVButtonAction());

		Button addNewFarmButton = new Button("Add new farm");
		addNewFarmButton.setOnAction(e -> addNewFarmButtonAction());

		Button addNewMilkDataButton = new Button("Add new milk data");
		addNewMilkDataButton.setOnAction(e -> addNewMilkDataButtonAction());

		Button editMilkDataButton = new Button("Edit milk data");
		editMilkDataButton.setOnAction(e -> editMilkDataButtonAction());

		Button farmReportButton = new Button("Farm Report");
		farmReportButton.setOnAction(e -> farmReportButtonAction());

		Button annualReportButton = new Button("Annual Report");
		annualReportButton.setOnAction(e -> annualReportButtonAction());

		Button monthlyReportButton = new Button("Monthly Report");
		monthlyReportButton.setOnAction(e -> monthlyReportButtonAction());

		Button dateRangeReportButton = new Button("Date Range Report");
		dateRangeReportButton.setOnAction(e -> dateRangeReportButtonAction());

		Button submitButton = new Button("Submit");
		submitButton.setOnAction(e -> submitButtonAction());

		// Create left panel of buttons (dependencies first)
		VBox fileOptionGroup = new VBox();
		fileOptionGroup.getChildren().addAll(fileLabel, importFileButton, exportAsCSVButton,
				addNewFarmButton, addNewMilkDataButton, editMilkDataButton);

		VBox reportOptionGroup = new VBox(reportLabel, farmReportButton, annualReportButton,
				monthlyReportButton, dateRangeReportButton);

		ImageView cowImage = new ImageView();
		cowImage.setImage(new Image("cow.jpg"));
		cowImage.setFitHeight(WINDOW_HEIGHT / 8);
		cowImage.setPreserveRatio(true);

		VBox leftOptionPanel = new VBox();
		leftOptionPanel.setSpacing(20); // TODO no magic numbers in spacing. define constants
										// elsewhere
		leftOptionPanel.setPadding(new Insets(15, 15, 15, 15)); // TODO verify insets are allowed
																// (javafx.geometry)
		leftOptionPanel.setStyle("-fx-background-color: #00FF00;");
		leftOptionPanel.getChildren().addAll(homeButton, fileOptionGroup, reportOptionGroup,
				cowImage);

		// Create report panel (dependencies first)

		// Create Chart group
		// this image is just a placeholder for the actual chart.
		ImageView placeholdImage = new ImageView();
		placeholdImage.setImage(new Image("basically.png"));
		placeholdImage.setFitHeight(WINDOW_HEIGHT / 2);
		placeholdImage.setPreserveRatio(true);

		VBox chartGroup = new VBox();
		chartGroup.setPadding(new Insets(15, 15, 15, 15));
		chartGroup.setStyle("-fx-border-color: black");
		chartGroup.getChildren().setAll(placeholdImage, chartLabel, totalWeightLabel,
				percentWeightLabel);

		// Create ID/Year/Submit group
		HBox farmIDGroup = new HBox();
		farmIDGroup.getChildren().addAll(farmIDLabel, farmComboBox);

		HBox yearGroup = new HBox();
		yearGroup.getChildren().addAll(yearLabel, yearComboBox);
		
		HBox monthGroup = new HBox();
		monthGroup.getChildren().addAll(monthLabel, monthComboBox);

		HBox IDYearSubmitGroup = new HBox();
		IDYearSubmitGroup.getChildren().addAll(farmIDGroup, yearGroup, monthGroup, submitButton);
		IDYearSubmitGroup.setSpacing(15.0);

		BorderPane reportPanel = new BorderPane();
		reportPanel.setBottom(IDYearSubmitGroup);
		reportPanel.setCenter(chartGroup);
		reportPanel.setPadding(new Insets(15, 15, 15, 15));

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();

		// Add panels to root pane
		root.setLeft(leftOptionPanel);
		root.setCenter(reportPanel);

		Scene mainScene = new Scene(root);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * TODO method header needed?
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
