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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	//
	// FARM
	//

	static ArrayList<Farm> farms = new ArrayList<Farm>();

	//
	// GUI STUFF
	//

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 640;
	private static final String APP_TITLE = "MilkWeight";

	private BorderPane root = new BorderPane();
	private BorderPane reportPanel = new BorderPane();
	private BorderPane notImplementedPanel = new BorderPane();
	private BorderPane homePanel = new BorderPane();
	private VBox chartGroup = new VBox();
	private VBox inputs = new VBox();
	
	// Chart stuff
	private CategoryAxis dateAxis = new CategoryAxis();
	private NumberAxis percentAxis = new NumberAxis();
	private BarChart<String,Number> chart = new BarChart<String,Number>(dateAxis, percentAxis);
	

	private void homeButtonAction() {
		root.setCenter(homePanel);
		System.out.println("Home Button Pressed");
	}

	private void importFileButtonAction() {
		System.out.println("Import File Button Pressed");
	}

	private void exportAsCSVButtonAction() {
		root.setCenter(notImplementedPanel);
		System.out.println("Export as CSV File Button Pressed");
	}

	private void addNewFarmButtonAction() {
		root.setCenter(notImplementedPanel);
		System.out.println("Add new farm Button Pressed");
	}

	private void addNewMilkDataButtonAction() {
		root.setCenter(notImplementedPanel);
		System.out.println("Add new milk data Button Pressed");
	}

	private void editMilkDataButtonAction() {
		root.setCenter(notImplementedPanel);
		System.out.println("Edit milk data Button Pressed");
	}

	private void farmReportButtonAction() {
		System.out.println("Farm Report Button Pressed");
		root.setCenter(reportPanel);
		
		
		//GRAPHING
		chart.getData().clear(); // Clear chart
		Series[] seriesArray = new Series[farms.size()]; // make array for series
		int index = 0;
		for (Farm farm : farms) {
			seriesArray[index] = new XYChart.Series(); // Create new series
			// Set newly added series' name to that of farm
			seriesArray[index].setName(farm.getFarmID());
			// Add all months data to series
			for (int i = 1; i < 13; i++) {
				//TODO dynamically set year
				seriesArray[index].getData().add(new XYChart.Data(Integer.toString(i), farm.getTotalWeightMonth(2019, i)));
			}
			index++;
		}
		
		for (int i = 0; i < farms.size(); i++) {
			chart.getData().add(seriesArray[i]);
		}
		//GRAPHING
		
	}

	private void annualReportButtonAction() {
		System.out.println("Annual Report Button Pressed");
		root.setCenter(notImplementedPanel);
	}

	private void monthlyReportButtonAction() {
		System.out.println("Monthly Report Button Pressed");
		root.setCenter(notImplementedPanel);
	}

	private void dateRangeReportButtonAction() {
		System.out.println("Date Range Report Button Pressed");
		root.setCenter(notImplementedPanel);
	}

	private void submitButtonAction() {
		System.out.println("Submit Button Pressed");
	}

	/**
	 * EventHandler to create text field when a user want to input text data.
	 * 
	 */
	class InputHandler implements EventHandler<ActionEvent> {
		Button button;
		String text;
		Button submit;
		TextField textBox;

		InputHandler(Button button, String text, Button submit, TextField textBox) {
			this.button = button;
			this.text = text;
			this.submit = submit;
			this.textBox = textBox;
		}

		@Override
		public void handle(ActionEvent arg0) {
			textBox.setPromptText(text);
			inputs = new VBox(textBox, submit);
			root.setCenter(inputs);
		}
	}

	class InputSubmitHandler implements EventHandler<ActionEvent> {
		TextField textBox;
		Button submit;

		InputSubmitHandler(Button submit, TextField textBox) {
			this.textBox = textBox;
		}

		@Override
		public void handle(ActionEvent arg0) {
			System.out.println("User entered: \"" + textBox.getText() + "\"");
			// This is super cringe rn sorry team, just here to make it work and no further
			// Shouldn't take long to move though, just this one line is what's needed to get the input
			try {
				Main.farms = Report.parseFile(textBox.getText(), farms);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(farms.size());
			textBox.clear();
			
		}
	}

	class OutputRequestHandler implements EventHandler<ActionEvent> {
		OutputRequestHandler() {

		}

		@Override
		public void handle(ActionEvent arg0) {
			reportPanel.setCenter(chartGroup);

		}

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
		TextField inputText = new TextField();

		// Drop Downs
		ComboBox<String> monthComboBox = new ComboBox<String>();
		ObservableList<String> monthItems = FXCollections.observableArrayList("January",
				"February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December", "ALL");
		monthComboBox.setItems(monthItems);

		ComboBox<String> yearComboBox = new ComboBox<String>();
		ObservableList<String> yearItems = FXCollections.observableArrayList("2017", "2018",
				"2019", "2020", "ALL");
		yearComboBox.setItems(yearItems);

		ComboBox<String> farmComboBox = new ComboBox<String>();
		ObservableList<String> farmItems = FXCollections.observableArrayList("Boggis", "Bunce",
				"Bean");
		farmComboBox.setItems(farmItems);

		// Buttons
		Button homeButton = new Button("Home");
		homeButton.setOnAction(e -> homeButtonAction());

		// used in the event a user wants to input a file or farm
		Button inputSubmit = new Button("Submit");
		//InputSubmitHandler inputSubmitHandler = new InputSubmitHandler(inputSubmit, inputText);
		/*
		inputSubmit.setOnAction(inputSubmitHandler);
		*/
		
		inputSubmit.setOnAction((event) ->{
			System.out.println("User entered: \"" + inputText.getText() + "\"");
			// This is super cringe rn sorry team, just here to make it work and no further
			// Shouldn't take long to move though, just this one line is what's needed to get the input
			try {
				Main.farms = Report.parseFile(inputText.getText(), farms);
			}
			catch(Exception e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("File Error");
				alert.setHeaderText(null);
				alert.setContentText("We are sorry, but was unable to read the file. Please check the file path.");
				alert.showAndWait();
			}
			
			
			inputText.clear();
			if (farms.size()!=0) {				
				ObservableList<String> newFarms = FXCollections.observableArrayList();
				farms.forEach(e -> newFarms.add(e.getFarmID()));
				farmComboBox.setItems(newFarms);
			}			
		});
		

		Button importFileButton = new Button("Import File");
		InputHandler fileHandler = new InputHandler(importFileButton, "Enter file name. Example : C:\\Users\\<User>\\eclipse-workspace\\CS400ATeam\\MilkWeight\\csv\\small\\2019-1.csv",
				inputSubmit, inputText);
		importFileButton.setOnAction(e -> importFileButtonAction());
		importFileButton.setOnAction(fileHandler);

		Button exportAsCSVButton = new Button("Export as CSV");
		exportAsCSVButton.setOnAction(e -> exportAsCSVButtonAction());

		Button addNewFarmButton = new Button("Add new farm");
		InputHandler farmHandler = new InputHandler(addNewFarmButton, "Enter farm ID",
				inputSubmit, inputText);
		addNewFarmButton.setOnAction(e -> addNewFarmButtonAction());
		// addNewFarmButton.setOnAction(farmHandler);

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
		OutputRequestHandler outputRequester = new OutputRequestHandler();
		submitButton.setOnAction(outputRequester);

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

		chartGroup.setPadding(new Insets(15, 15, 15, 15));
		chartGroup.setStyle("-fx-border-color: black");
		chartGroup.getChildren().setAll(chart, chartLabel, totalWeightLabel,
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

		reportPanel.setBottom(IDYearSubmitGroup);
		reportPanel.setCenter(chartGroup);
		reportPanel.setPadding(new Insets(15, 15, 15, 15));

		// Home panel with instructions
		homePanel.setTop(new Label(
				"Welcome! \n How to use: \n Go to import CSV file and input path to file you want to read, then click submit \n Then go to Farm Report to see data. There you can select a farm ID and year to see data."));
		homePanel.setMinWidth(800);

		// Not implemented panel
		notImplementedPanel.setTop(new Label("NOT IMPLEMENTED"));
		notImplementedPanel.setCenter(placeholdImage);

		// Add panels to root pane
		root.setLeft(leftOptionPanel);
		root.setCenter(homePanel);

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
